package com.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.api.HelloService;
import com.dubbo.api.entity.Points;
import com.dubbo.provider.dao.PointsMapper;
import com.dubbo.provider.rule.LoginExpressRule;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 由于dubbo的@service标签，没有像spring的@Service那样声明@Component，所以这里需要单独添加@Component声明
 */
@Service
@Component
public class HelloServiceImpl implements HelloService {

    @Resource
    private PointsMapper pointsMapper;

    @Override
    public String sayHello(){
        return "Dubbo: hello world";
    }

    @Override
    public String login(String userName) {
        String msg = "";
        try{
            System.out.println("用户: " + userName + " ,登录成功");
            int userLevel = 1;
            /**
             * 1.有的客户登录成功送10积分，有的客户登录成功送20积分
             * 2.有的客户根据用户等级进行给积分，1级用户登录给5分，2级用户给10分。
             */
            //规则信息，后续可以存放在数据库中，需要有规则名称作为唯一标识
            //String express = "userLevel>=1+5;";
            String express = "userLevel==1+5;userLevel==2+10;userLevel==3+20;";
            int needAddPoints = LoginExpressRule.loginAddPoints(userLevel,express);

            Points points = new Points();
            points.setId(1);
            points.setPoints(needAddPoints);

            //根据规则添加积分
            pointsMapper.addPoints(points);
            msg =  "登录成功，已为该用户添加" + needAddPoints + "积分。";
        }catch (Exception e){
            //TODO 这里可以抛出自定义异常信息
        }
        return msg;
    }

}
