package com.yim.asocream.kind.model;

import com.yim.asocream.category.model.CategoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class KindEntity {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "kind_id")//이름 설정
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public KindEntity(String name,CategoryEntity category){
        this.category = category;
        this.name = name;
    }



}
