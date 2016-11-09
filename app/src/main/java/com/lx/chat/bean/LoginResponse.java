package com.lx.chat.bean;

/**
 * Created by lx on 16/11/9.
 * 登录接口返回对应数据
 */
public class LoginResponse extends CommonResponse {

    private LoginInfo data ;

    public LoginInfo getData() {
        return data;
    }

    public void setData(LoginInfo data) {
        this.data = data;
    }



    public class LoginInfo{
        private String authcode ;

        public String getAuthcode() {
            return authcode;
        }

        public void setAuthcode(String authcode) {
            this.authcode = authcode;
        }


    }
}
