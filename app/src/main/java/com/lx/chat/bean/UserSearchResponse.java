package com.lx.chat.bean;

import java.util.ArrayList;

/**
 * Created by lx on 16/11/9.
 */
public class UserSearchResponse extends CommonResponse {
    UserSearchResponseData data ;

    public UserSearchResponseData getData() {
        return data;
    }

    public void setData(UserSearchResponseData data) {
        this.data = data;
    }

    public class UserSearchResponseData{
        ArrayList<User> userlist ;

        public ArrayList<User> getUserlist() {
            return userlist;
        }

        public void setUserlist(ArrayList<User> userlist) {
            this.userlist = userlist;
        }
    }
}
