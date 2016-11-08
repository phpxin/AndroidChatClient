package com.lx.chat.bean;

import java.util.ArrayList;

/**
 * Created by lx on 16/11/8.
 */
public class UserListResponse {

    private int code ;
    private String msg ;
    private UserListResponseData data ;


    public UserListResponseData getData() {
        return data;
    }
    public void setData(UserListResponseData data) {
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg = msg ;
    }



    public class UserListResponseData{
        private ArrayList<User> userlist ;

        public ArrayList<User> getUserlist() {
            return userlist;
        }

        public void setUserlist(ArrayList<User> userlist) {
            this.userlist = userlist;
        }


    }

}
