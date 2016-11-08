package com.lx.chat.bean;

/**
 * Created by lx on 16/11/8.
 * 公用http响应数据,适用于添加,删除,修改等操作
 */
public class CommonResponse {

    private int code ;
    private String msg ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
