package com.yim.asocream.item.model.entity;

import com.yim.asocream.item.model.request.UpdItemRequest;
import com.yim.asocream.kind.model.KindEntity;
import com.yim.asocream.model.common.BaseModel;
import com.yim.asocream.order.model.entity.OrderEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본생성자 생성 (접근지정자= PROTECTED)
public class ItemEntity extends BaseModel {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "item_id")//이름 설정
    private long id;

    private String url;
    private String title;
    @Lob
    private String Explanation;
    private int price;
    private long views;

    public void setKindEntity(KindEntity kindEntity) {
        this.kindEntity = kindEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "kind_id")
    private KindEntity kindEntity;

    public ItemEntity(String title, String explanation, int price) {
        this.title = title;
        Explanation = explanation;
        this.price = price;
    }

    public void changeUpdate(UpdItemRequest updItemRequest){

        this.title = updItemRequest.getTitle();
        this.Explanation = updItemRequest.getExplanation();
        this.price = updItemRequest.getPrice();

    }
    public void addViews(int views){
        this.views = this.views + views;
    }
}
