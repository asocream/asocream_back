package com.yim.asocream.category.model;

import com.yim.asocream.kind.model.KindEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class CategoryEntity {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "category_id")//이름 설정
    private long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<KindEntity> kindEntities;


    public CategoryEntity(String name){
        this.name = name;
    }
}
