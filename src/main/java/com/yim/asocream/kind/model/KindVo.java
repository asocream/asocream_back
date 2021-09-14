package com.yim.asocream.kind.model;

import com.yim.asocream.category.model.CategoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KindVo {

    private long kindId;

    private String name;

    public KindEntity change(CategoryEntity categoryEntity) {
        KindEntity kindEntity = new KindEntity(this.name,categoryEntity);
        return kindEntity;
    }
    public KindVo(KindEntity kindEntity){
        this.kindId =  kindEntity.getId();
        this.name = kindEntity.getName();
    }
}
