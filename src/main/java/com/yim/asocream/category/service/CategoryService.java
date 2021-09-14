package com.yim.asocream.category.service;

import com.yim.asocream.category.model.CategoryEntity;
import com.yim.asocream.category.model.CategoryKindVo;
import com.yim.asocream.category.model.CategoryVo;
import com.yim.asocream.category.repository.CategoryRepository;
import com.yim.asocream.exception.OrderNotFoundException;
import com.yim.asocream.kind.repository.KindRepository;
import com.yim.asocream.kind.service.KindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final KindService kindService;

    public long insCategory(String name) {

        CategoryEntity categoryEntity = new CategoryEntity(name);
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId();
    }

    public List<CategoryVo> selCategoryAll() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryVo> categoryVos = categoryEntities.stream()
                .map(categoryEntity -> new CategoryVo(categoryEntity)).collect(Collectors.toList());
        return categoryVos;
    }

    public long delCategory(long id) {
        Optional<CategoryEntity> categoryEntity_ = categoryRepository.findById(id);
        if(!categoryEntity_.isPresent()){
            throw new OrderNotFoundException("없는 카테고리 입니다. 오류 추가해야함");
        }
        categoryRepository.delete(categoryEntity_.get());
        return categoryEntity_.get().getId();

    }


    public List<CategoryKindVo> selCategoryAndKindAll() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryKindVo> categoryKindVoList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities){
            categoryKindVoList.add(new CategoryKindVo(categoryEntity,categoryEntity.getKindEntities()));
        }
        return categoryKindVoList;
    }
}
