package com.yim.asocream.category.controller;

import com.yim.asocream.category.model.CategoryKindVo;
import com.yim.asocream.category.model.CategoryVo;
import com.yim.asocream.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public long insCategory(@RequestBody CategoryVo categoryVo){

        return categoryService.insCategory(categoryVo.getName());

    }

    @GetMapping("/category")
    public List<CategoryVo> selCategoryAll(){

        return categoryService.selCategoryAll();
    }

    @GetMapping("/categoryAndKind")
    public List<CategoryKindVo> selCategoryAndKindAll(){

        return categoryService.selCategoryAndKindAll();
    }
    

    @DeleteMapping("/category")
    public long delCategory(long id){

        return categoryService.delCategory(id);
    }
}
