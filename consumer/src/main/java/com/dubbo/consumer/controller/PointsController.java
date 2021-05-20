package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.PointsService;
import com.dubbo.api.entity.Points;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/point")
public class PointsController {

    @Reference
    private PointsService pointsService;

    @PostMapping("/addPoints")
    public String addPoints(@Valid Points points){
        pointsService.addPoints(points);
        return "添加用户积分成功!";
    }

    @PostMapping("/reducePoints")
    public String reducePoints(@Valid Points points){
        pointsService.reducePoints(points);
        return "扣减用户积分成功!";
    }

}
