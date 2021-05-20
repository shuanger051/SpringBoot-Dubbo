package com.dubbo.api.entity;

public class BeanExample {

    public Object getTemplate(Object... params) throws Exception{
        String result = "";
        for(Object obj:params){
            result = result+obj+",";
        }
        return result;
    }

}
