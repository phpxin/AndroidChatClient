package com.lx.chat.bean;

import java.util.ArrayList;

/**
 * Created by lx on 16/11/14.
 */
public class InviteListResponse extends CommonResponse {

    private InviteListResponseData data ;


    public InviteListResponseData getData() {
        return data;
    }

    public void setData(InviteListResponseData data) {
        this.data = data;
    }



    public class InviteListResponseData {
        private ArrayList<InviteItem> list ;


        public ArrayList<InviteItem> getList() {
            return list;
        }

        public void setList(ArrayList<InviteItem> list) {
            this.list = list;
        }

    }


}
