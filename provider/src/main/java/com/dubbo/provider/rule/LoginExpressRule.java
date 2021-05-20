package com.dubbo.provider.rule;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.exception.QLBizException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginExpressRule {

    /**
     * 根据用户等级运算需要添加多少积分
     * @param express
     * @param paramMap
     * @return
     * @throws QLBizException
     */
    public static String execute(String express,Map<String, Object> paramMap) throws QLBizException {
        try {
            ExpressRunner runner = new ExpressRunner();
            DefaultContext<String, Object> context = new DefaultContext<String, Object>();
            paramMap.forEach((k,v)-> context.put(k, Integer.parseInt(String.valueOf(v))));
            Object r = null;
            try {
                r = runner.execute(express, context, null, true, false);
            }catch (Exception e) {
                throw new RuntimeException("表达式解析错误！");
            }
            return String.valueOf(r);
        }catch (Exception e){
            //TODO 这里可以抛出自定义异常
            throw new QLBizException(e.getMessage());
        }
    }

    /**
     * 登录成功赠送积分规则
     * @param userLevel
     * @param express
     * @return
     * @throws Exception
     */
    public static int loginAddPoints(int userLevel,String express) throws Exception{
        String[] expressArray = express.split(";");
        int res = 0;
        for (String condition : expressArray) {
            if (!StringUtils.isEmpty(condition)) {
                String[] e = condition.split("\\+");
                Map<String,Object> param = new HashMap<>();
                param.put("userLevel", userLevel);
                String result = execute(e[0], param);
                if (result == "true") {
                    res = Integer.parseInt(e[1]);
                    break;
                }
            }
        }
        return res;
    }

}
