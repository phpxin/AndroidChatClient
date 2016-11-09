package com.lx.chat.bean;

import java.util.ArrayList;

/**
 * Created by lx on 16/11/8.
 */
public class UserListResponse extends CommonResponse {


    private UserListResponseData data ;


    public UserListResponseData getData() {
        return data;
    }
    public void setData(UserListResponseData data) {
        this.data = data;
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
