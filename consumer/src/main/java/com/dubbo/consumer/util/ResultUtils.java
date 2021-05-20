package com.dubbo.consumer.util;

import com.dubbo.consumer.constant.ResponseCode;

import java.io.Serializable;

/**
 * 定义统一后端返回格式，一般用在controller和异常返回中
 */
public class ResultUtils implements Serializable {

    private static final long serialVersionUID = 7498483649536881777L;

    /**
     * 异常代码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public ResultUtils(){
    }

    public ResultUtils(Integer status, String msg, Object data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResultUtils success(Integer code, String msg) {
        return new ResultUtils(code, msg, null);
    }

    public static <T> ResultUtils success(T data) {
        ResultUtils result = new ResultUtils();
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMsg(ResponseCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static ResultUtils error(ResponseCode code) {
        return new ResultUtils(code.getCode(), code.getMsg(), null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
