package com.yim.asocream.category.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryVo {

    String name;

    public CategoryVo(CategoryEntity categoryEntity){
        this.name = categoryEntity.getName();
    }
}
