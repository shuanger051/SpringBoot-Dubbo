package com.dubbo.provider.qlexpress;

import com.ql.util.express.IExpressContext;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Context extends HashMap<String, Object> implements IExpressContext<String, Object> {

    private ApplicationContext context;

    public Context(ApplicationContext context) {
        this.context = context;
    }

    public Context(Map<String, Object> properties, ApplicationContext context) {
        super(properties);
        this.context = context;
    }

    @Override
    public Object get(Object name) {
        Object result;
        result = super.get(name);
        try {
            if (result == null && this.context != null && this.context.containsBean((String) name)) {
                //如果在Spring容器中包含bean，则返回String的Bean
                result = this.context.getBean((String) name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Object put(String name, Object object) {
        throw new RuntimeException("未实现");
    }

}
