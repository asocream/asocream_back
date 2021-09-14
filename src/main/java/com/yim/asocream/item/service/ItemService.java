package com.yim.asocream.item.service;

import com.yim.asocream.category.model.CategoryEntity;
import com.yim.asocream.category.repository.CategoryRepository;
import com.yim.asocream.category.service.CategoryService;
import com.yim.asocream.exception.ItemNotFoundException;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.item.model.request.InsItemRequest;
import com.yim.asocream.item.model.request.UpdItemRequest;
import com.yim.asocream.item.model.response.ItemResponse;
import com.yim.asocream.item.repository.ItemRepository;
import com.yim.asocream.kind.model.KindEntity;
import com.yim.asocream.kind.repository.KindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final KindRepository kindRepository;

    public long insItem(InsItemRequest insItemRequest) {

        Optional<KindEntity> kindEntity_ = kindRepository.findById(insItemRequest.getKindId());
        if(!kindEntity_.isPresent()){
            throw new ItemNotFoundException("없는 종류입니다.");
        }

        ItemEntity itemEntity = insItemRequest.changeItemEntity();
        itemEntity.setKindEntity(kindEntity_.get());
        itemRepository.save(itemEntity);
        return itemEntity.getId();
    }


    public ItemResponse selItemOne(long id) {

        Optional<ItemEntity> itemEntity_ = itemRepository.findById(id);
        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        ItemEntity itemEntity = itemEntity_.get();
        return new ItemResponse(itemEntity);
    }

    public List<ItemResponse> selItemList() {

        List<ItemEntity> itemEntities = itemRepository.findAll();
        return itemEntities.stream().map(itemEntity ->
                new ItemResponse(itemEntity)).collect(Collectors.toList());
    }


    public long updItem(UpdItemRequest updItemRequest) {

        Optional<ItemEntity> itemEntity_ = itemRepository.findById(updItemRequest.getId());
        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        ItemEntity itemEntity = itemEntity_.get();
        itemEntity.changeUpdate(updItemRequest);//영속성이 해결해줄거임
        return itemEntity.getId();
    }

    public long delItem(long id) {
        Optional<ItemEntity> itemEntity_ =  itemRepository.findById(id);
        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        ItemEntity itemEntity = itemEntity_.get();
        itemRepository.delete(itemEntity);
        return itemEntity.getId();
    }

    public List<ItemResponse> selItemByCategory(long id) {

        Optional<KindEntity> kindEntity_ = kindRepository.findById(id);
        if(!kindEntity_.isPresent()){
            throw new ItemNotFoundException("없는 종류입니다..");
        }

        List<ItemEntity> itemEntities = itemRepository.findOptionalByKindEntity(kindEntity_.get());
        return itemEntities.stream()
                .map(itemEntity -> new ItemResponse(itemEntity)).collect(Collectors.toList());
    }
}
