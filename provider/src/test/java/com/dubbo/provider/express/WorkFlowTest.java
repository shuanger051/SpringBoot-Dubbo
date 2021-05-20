package com.dubbo.provider.express;

import com.dubbo.api.HelloService;
import com.dubbo.api.PointsService;
import com.dubbo.api.entity.Points;
import com.dubbo.provider.BaseTest;
import com.dubbo.provider.service.HelloServiceImpl;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.Operator;
import org.junit.Test;

import javax.annotation.Resource;

public class WorkFlowTest extends BaseTest {

    @Resource
    private HelloService helloService;

    @Resource
    private PointsService pointsService;

    @Test
    public void testWorkFlow() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        IExpressContext<String,Object> context = new DefaultContext<>();
        context.put("helloService",helloService);
        context.put("userName","zhangsan");

        runner.addClassMethod("login", com.dubbo.api.HelloService.class, new Operator() {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                ((HelloServiceImpl) list[0]).login(list[1].toString());
                System.out.println("拦截到login方法,为当前用户添加签到积分");
                Points points = new Points();
                points.setId(1);
                points.setPoints(2);
                pointsService.addPoints(points);
                return true;
            }
        });

        Object result = runner.execute("helloService.login(userName);",context,null,true,false);
        System.out.println(result);
    }

}
