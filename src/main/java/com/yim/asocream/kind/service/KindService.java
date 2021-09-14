package com.yim.asocream.kind.service;

import com.yim.asocream.category.model.CategoryEntity;
import com.yim.asocream.category.repository.CategoryRepository;
import com.yim.asocream.exception.OrderNotFoundException;
import com.yim.asocream.kind.model.KindEntity;
import com.yim.asocream.kind.model.KindVo;
import com.yim.asocream.kind.repository.KindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KindService {

    private final KindRepository kindRepository;
    private final CategoryRepository categoryRepository;

    public long insKind(KindVo kindVo) {

        Optional<CategoryEntity> categoryEntity_ = categoryRepository.findById(kindVo.getKindId());
        if(!categoryEntity_.isPresent()){
            throw new OrderNotFoundException("없는 카테고리입니다.");
        }
        KindEntity kindEntity = kindVo.change(categoryEntity_.get());
        kindRepository.save(kindEntity);
        return kindEntity.getId();

    }

    public List<KindVo> selKindAll() {
        List<KindEntity> kindEntities = kindRepository.findAll();
        List<KindVo> kindVos = kindEntities.stream()
                .map(kindEntity -> new KindVo(kindEntity)).collect(Collectors.toList());
        return kindVos;
    }




    public long delKind(long id) {
        Optional<KindEntity> kindEntity_ = kindRepository.findById(id);
        if(!kindEntity_.isPresent()){
            throw new OrderNotFoundException("종류가 없습니다.");
        }
        kindRepository.delete(kindEntity_.get());
        return kindEntity_.get().getId();
    }
}
