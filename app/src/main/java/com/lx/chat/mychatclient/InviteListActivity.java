package com.lx.chat.mychatclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.adapters.InviteListAdapter;
import com.lx.chat.bean.GetInfoResponse;
import com.lx.chat.bean.InviteItem;
import com.lx.chat.bean.InviteListResponse;
import com.lx.chat.bean.InviteResponse;
import com.lx.chat.bean.User;
import com.lx.chat.util.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class InviteListActivity extends Activity {


    private ImageView retbtn ;
    private TextView titleTv ;

    List<InviteItem> inviteListData;
    ListView inviteList;
    InviteListAdapter ulAdapter;

    private SwipeRefreshLayout listRefresh;

    private String inviteListApi = "http://" + Config.ServerAddr + ":8080/index.php?module=relation&action=inviteList" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_list);

        retbtn = (ImageView) findViewById(R.id.retbtn) ;
        retbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InviteListActivity.this.finish();
            }
        });

        titleTv = (TextView) findViewById(R.id.titleText) ;
        titleTv.setText("邀请列表");


        inviteListData = new ArrayList<InviteItem>() ;
        ulAdapter = new InviteListAdapter(getApplicationContext(), inviteListData, R.layout.item_invite) ;

        inviteList = (ListView) findViewById(R.id.inviteList) ;
        inviteList.setAdapter(ulAdapter);


        (new Thread(inviteListRequest)).start();
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData(); // bundle这东西就是个包，数据打包用它传递过来

            switch (msg.what) {

                case HandleMess.MESS_HTTP_INVITE_LIST_DONE:
                    InviteListResponse inviteListResponse = (InviteListResponse) bundle.getSerializable("inviteListResponse") ;
                    if (inviteListResponse.getCode() != 0){
                        Toast.makeText(getApplicationContext(), inviteListResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(getApplicationContext(), "邀请成功", Toast.LENGTH_SHORT).show();
                        //InviteActivity.this.finish();

                        ///inviteListData = inviteListResponse.getData().getList() ;
                        for (InviteItem _item : inviteListResponse.getData().getList()) {

                        }


                        ulAdapter.notifyDataSetChanged();

                    }
                    break;

            }


        }
    };


    Runnable inviteListRequest = new Runnable() {
        @Override
        public void run() {

            String queryStr = "authcode="+Config.my.getAuthcode() ;

            //String inviteListResponse = HttpRequest.doPostAndGetData(inviteListApi, queryStr) ;
            InviteListResponse inviteListResponse = HttpRequest.doPostAndGetJson(inviteListApi, queryStr, InviteListResponse.class) ;

            //msg
            Message mess=new Message();
            mess.what = HandleMess.MESS_HTTP_INVITE_LIST_DONE;
            Bundle bundle = new Bundle();
            bundle.putSerializable("inviteListResponse", inviteListResponse);
            mess.setData(bundle);
            InviteListActivity.this.myHandler.sendMessage(mess);
        }
    } ;
}
