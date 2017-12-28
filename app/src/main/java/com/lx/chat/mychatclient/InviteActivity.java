package com.lx.chat.mychatclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.bean.CommonResponse;
import com.lx.chat.bean.GetInfoResponse;
import com.lx.chat.bean.InviteResponse;
import com.lx.chat.bean.User;
import com.lx.chat.util.HttpRequest;

public class InviteActivity extends Activity implements View.OnClickListener {


    private ImageView avatar ;
    private Button doInvite ;
    private EditText introEditText ;

    private ImageView retbtn ;
    private TextView titleTv ;

    private int fid ; // 目标用户id

    private String userInfoApi = "http://" + Config.ServerAddr + ":8080/index.php?module=user&action=getInfo" ;
    private String doInviteApi = "http://" + Config.ServerAddr + ":8080/index.php?module=relation&action=invite" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        //datas
        Bundle bun=this.getIntent().getExtras(); //通过进来的意图对象获取数据

        try{
            fid = bun.getInt("fid");
        }catch(NumberFormatException _e){
            Toast.makeText(getApplicationContext(), "fid is error", Toast.LENGTH_SHORT).show();

            InviteActivity.this.finish();
        }


        this.avatar = (ImageView) findViewById(R.id.avatar) ;
        this.doInvite = (Button) findViewById(R.id.doInvite) ;
        this.introEditText = (EditText) findViewById(R.id.intro) ;

        titleTv = (TextView) findViewById(R.id.titleText) ;
        titleTv.setText("获取信息中...");

        retbtn = (ImageView) findViewById(R.id.retbtn) ;
        retbtn.setOnClickListener(this);


        //加载用户信息
        (new Thread(doUserInfoRequest)).start();
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.retbtn:
                InviteActivity.this.finish();
                break;
            case R.id.doInvite:
                //邀请
                (new Thread(doInviteRequest)).start();
                break;
        }


    }

    private void LoadImage(ImageView img, String path)
    {
        //异步加载图片资源
        AsyncTaskImageLoad async=new AsyncTaskImageLoad(img);
        //执行异步加载，并把图片的路径传送过去
        async.execute(path);
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData(); // bundle这东西就是个包，数据打包用它传递过来

            switch (msg.what) {

                case HandleMess.MESS_HTTP_GET_INFO_OK:
                    String jsonStr = bundle.getString("getInfoResponse") ;
                    GetInfoResponse getInfoResponse = JSON.parseObject(jsonStr, GetInfoResponse.class) ;

                    String toastMsg = "注册成功";

                    if (getInfoResponse.getCode() != 0){
                        toastMsg = getInfoResponse.getMsg() ;
                        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                        InviteActivity.this.finish();
                    }else{
                        //Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                        //RegisterActivity.this.finish();
                    }

                    User user = getInfoResponse.getData().getInfo() ;
                    titleTv.setText("邀请"+user.getName());

                    LoadImage(avatar, user.getAvatar());

                    //load 成功设置按钮事件
                    doInvite.setOnClickListener(InviteActivity.this);


                    break;

                case HandleMess.MESS_HTTP_INVITE_DONE:
                    InviteResponse inviteResponse = (InviteResponse) bundle.getSerializable("inviteResponse") ;
                    if (inviteResponse.getCode() != 0){
                        Toast.makeText(getApplicationContext(), inviteResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "邀请成功", Toast.LENGTH_SHORT).show();
                        InviteActivity.this.finish();
                    }
                    break;

            }


        }
    };

    Runnable doInviteRequest = new Runnable() {
        @Override
        public void run() {
            String intro = introEditText.getEditableText().toString() ;
            String queryStr = "authcode="+Config.my.getAuthcode()+"&fid="+fid+"&intro="+intro ;
            InviteResponse inviteResponse = HttpRequest.doPostAndGetJson(doInviteApi, queryStr, InviteResponse.class) ;

            //msg
            Message mess=new Message();
            mess.what = HandleMess.MESS_HTTP_INVITE_DONE;
            Bundle bundle = new Bundle();
            //bundle.putString("getInfoResponse", getInfoResponse);
            bundle.putSerializable("inviteResponse", inviteResponse);
            mess.setData(bundle);
            InviteActivity.this.myHandler.sendMessage(mess);
        }
    } ;

    Runnable doUserInfoRequest = new Runnable() {
        @Override
        public void run() {

            //String _account = accountET.getEditableText().toString();
            //String _pwd = pwdET.getEditableText().toString() ;
            //String _pwdConfirm = confirmPwdET.getEditableText().toString() ;

            String queryStr = "authcode="+Config.my.getAuthcode()+"&uid="+fid ;


            String getInfoResponse = HttpRequest.doPostAndGetData(userInfoApi, queryStr) ;



            //msg
            Message mess=new Message();
            mess.what = HandleMess.MESS_HTTP_GET_INFO_OK;
            Bundle bundle = new Bundle();
            bundle.putString("getInfoResponse", getInfoResponse);
            mess.setData(bundle);
            InviteActivity.this.myHandler.sendMessage(mess);
        }
    } ;




}
