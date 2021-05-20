package com.dubbo.provider.express;

import com.dubbo.api.PointsService;
import com.dubbo.provider.BaseTest;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

import javax.annotation.Resource;

public class PointsServiceTest extends BaseTest {

    @Resource
    private PointsService pointsService;

    /**
     * 测试绑定函数
     * @throws Exception
     */
    @Test
    public void testSignIn() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        context.put("pointsService",pointsService);
        context.put("points",2);
        Object result = runner.execute("pointsService.signIn(points)",context,null,true,false);
        System.out.println(result);
    }


}
