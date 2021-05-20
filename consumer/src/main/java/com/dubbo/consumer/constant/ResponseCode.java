package com.dubbo.consumer.constant;

/**
 * 定义错误异常代码
 */
 public enum ResponseCode {

    SUCCESS(0, "操作成功"),
    ERROR(1, "操作失败"),
    WEB_400(400,"错误请求"),
    WEB_401(401,"访问未得到授权"),
    WEB_403(403,"拒绝访问"),
    WEB_404(404,"资源未找到"),
    WEB_500(500,"服务器内部错误");

   private Integer code;
   private String msg;

   ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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

   @Override
   public String toString() {
      return "ResponseCode{" +
              "code=" + code +
              ", msg='" + msg + '\'' +
              '}';
   }

}
