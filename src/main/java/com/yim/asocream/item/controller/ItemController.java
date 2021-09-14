package com.yim.asocream.item.controller;

import com.yim.asocream.item.model.request.InsItemRequest;
import com.yim.asocream.item.model.request.UpdItemRequest;
import com.yim.asocream.item.model.response.ItemResponse;
import com.yim.asocream.item.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public long insItem(@RequestBody InsItemRequest insItemRequest){

        return itemService.insItem(insItemRequest);
    }

    @GetMapping("/item")
    public ItemResponse selItemOne(long id){

        return itemService.selItemOne(id);
    }

    @GetMapping("/itemByCategory")
    public List<ItemResponse> selItemByCategory(long id){
        return itemService.selItemByCategory(id);
    }

    @GetMapping("/items")//이거 일단 만듬 사용할시 페이징 처리 까지 끝내기
    public List<ItemResponse> selItemList(){

        return itemService.selItemList();
    }

    @PatchMapping
    public long updItem(@RequestBody UpdItemRequest updItemRequest){

        return itemService.updItem(updItemRequest);
    }

    @DeleteMapping
    public long delItem(long id){
        return itemService.delItem(id);
    }
}
