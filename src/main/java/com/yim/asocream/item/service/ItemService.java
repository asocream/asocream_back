package com.yim.asocream.item.service;

import com.yim.asocream.exception.ItemNotFoundException;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.item.model.request.InsItemRequest;
import com.yim.asocream.item.model.request.UpdItemRequest;
import com.yim.asocream.item.model.response.ItemResponse;
import com.yim.asocream.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public long insItem(InsItemRequest insItemRequest) {
        ItemEntity itemEntity = insItemRequest.changeItemEntity();
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

}
