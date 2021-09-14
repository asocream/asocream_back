package com.yim.asocream.category.model;

import com.yim.asocream.kind.model.KindEntity;
import com.yim.asocream.kind.model.KindVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class CategoryKindVo {

    private CategoryVo categoryVo;
    private List<KindVo> kindVos;

    public CategoryKindVo(CategoryEntity categoryEntity, List<KindEntity> kindEntities) {
        this.categoryVo = new CategoryVo(categoryEntity);
        this.kindVos = kindEntities.stream()
                .map(kindEntity -> new KindVo(kindEntity)).collect(Collectors.toList());
    }
}
