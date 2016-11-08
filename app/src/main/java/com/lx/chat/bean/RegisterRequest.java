package com.lx.chat.bean;

/**
 * Created by lx on 16/11/8.
 */
public class RegisterRequest {

    private String account;
    private String pwd;
    private String pwdConfirm;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdConfirm() {
        return pwdConfirm;
    }

    public void setPwdConfirm(String pwdConfirm) {
        this.pwdConfirm = pwdConfirm;
    }





}
