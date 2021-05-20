package com.dubbo.provider.qlexpress;

import com.dubbo.api.PointsService;
import com.dubbo.api.entity.Points;
import com.dubbo.provider.service.HelloServiceImpl;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class Runner implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(Runner.class);

    @Resource
    private PointsService pointsService;

    private static final ExpressRunner RUNNER;

    private static boolean isInitialRunner = false;

    private ApplicationContext applicationContext;

    //这里使用了Map集合做本地缓存，本地缓存在分布式部署时，不能做实时更新。因为本地缓存的数据会因为物理环境和时间存在不一致的情况。
    private static final Map<String, Object> EMPTY_CLIENT_CONTEXT = new HashMap<>();

    static {
        RUNNER = new ExpressRunner();
    }

    /**
     * @param statement 需要执行的语句
     * @param context 上下文
     */
    @SuppressWarnings("unchecked")
    public Object execute(String statement, Map<String, Object> context) throws Exception {
        initRunner();
        IExpressContext expressContext = new Context(context != null ? context : EMPTY_CLIENT_CONTEXT, applicationContext);
        statement = initStatement(statement);
        try {
            return RUNNER.execute(statement, expressContext, null, true, false);
        } catch (Exception e) {
            logger.error("QLExpress执行出错", e);
        }
        return null;
    }

    /**
     * 在此处把一些中文符号替换成英文符号
     */
    private String initStatement(String statement) {
        return statement.replace("（", "(").replace("）", ")").replace("；", ";").replace("，", ",").replace("“", "\"").replace("”", "\"");
    }

    private void initRunner() {
        if (isInitialRunner) {
            return;
        }
        //延迟初始化
        synchronized (RUNNER) {
            if (isInitialRunner) {
                return;
            }
            try {
                //这里可以改为从数据库读取数据
                RUNNER.addFunctionOfServiceMethod("签到", applicationContext.getBean("pointsService"), "signIn", new Class[]{Integer.class}, null);

                RUNNER.addClassMethod("login", com.dubbo.api.HelloService.class, new Operator() {
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

            } catch (Exception e) {
                throw new RuntimeException("初始化失败表达式", e);
            }
        }
        isInitialRunner = true;
    }

    @Override
    public void setApplicationContext(ApplicationContext aContext) throws BeansException {
        applicationContext = aContext;
    }

}
