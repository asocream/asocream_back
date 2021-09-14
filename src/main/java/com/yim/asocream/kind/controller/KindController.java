package com.yim.asocream.kind.controller;

import com.yim.asocream.kind.model.KindVo;
import com.yim.asocream.kind.service.KindService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KindController {

    private  final KindService kindService;

    @PostMapping("/kind")
    public long insKind(@RequestBody KindVo kindVo){

        return kindService.insKind(kindVo);
    }
    @GetMapping("/kind")
    public List<KindVo> selKindAll(){

        return kindService.selKindAll();
    }

    @DeleteMapping("/kind")
    public long delKind(long id){
        return kindService.delKind(id);
    }
}
