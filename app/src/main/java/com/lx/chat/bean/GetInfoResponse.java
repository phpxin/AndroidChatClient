package com.lx.chat.bean;

import java.util.ArrayList;

/**
 * Created by lx on 16/11/9.
 */
public class GetInfoResponse extends CommonResponse {


    private GetInfoResponseData data ;


    public GetInfoResponseData getData() {
        return data;
    }
    public void setData(GetInfoResponseData data) {
        this.data = data;
    }



    public class GetInfoResponseData{
        private User info ;

        public User getInfo() {
            return info;
        }

        public void setInfo(User info) {
            this.info = info;
        }
    }

}
