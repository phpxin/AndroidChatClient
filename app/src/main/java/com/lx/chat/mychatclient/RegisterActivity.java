package com.lx.chat.mychatclient;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.lx.chat.bean.RegisterRequest;
import com.lx.chat.util.HttpRequest;

public class RegisterActivity extends Activity {

    String registerUrl = "http://" + Config.ServerAddr + ":8080/index.php?module=user&action=register" ;
    ImageView retbtn ;
    TextView titleTv ;

    EditText accountET, pwdET, confirmPwdET;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retbtn = (ImageView) findViewById(R.id.retbtn) ;
        titleTv = (TextView) findViewById(R.id.titleText) ;

        titleTv.setText("注册");

        retbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

        accountET = (EditText) findViewById(R.id.account) ;
        pwdET = (EditText) findViewById(R.id.pwd) ;
        confirmPwdET = (EditText) findViewById(R.id.confirm_pwd) ;
        submit = (Button) findViewById(R.id.register) ;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(doRequest)).start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData(); // bundle这东西就是个包，数据打包用它传递过来

            switch (msg.what) {

                case HandleMess.MESS_HTTP_REGISTER_DONE:
                    String jsonStr = bundle.getString("commonResponse") ;
                    CommonResponse commonResponse = JSON.parseObject(jsonStr, CommonResponse.class) ;

                    String toastMsg = "注册成功";

                    if (commonResponse.getCode() != 0){
                        toastMsg = commonResponse.getMsg() ;
                        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                        RegisterActivity.this.finish();
                    }

                    break;

            }


        }
    };

    Runnable doRequest = new Runnable() {
        @Override
        public void run() {

            String _account = accountET.getEditableText().toString();
            String _pwd = pwdET.getEditableText().toString() ;
            String _pwdConfirm = confirmPwdET.getEditableText().toString() ;

            String queryStr = "account="+_account+"&pwd="+_pwd+"&repwd="+_pwdConfirm ;


            String commonResponse = HttpRequest.doPostAndGetData(registerUrl, queryStr) ;


            //msg
            Message mess=new Message();
            mess.what = HandleMess.MESS_HTTP_REGISTER_DONE;
            Bundle bundle = new Bundle();
            bundle.putString("commonResponse", commonResponse);
            mess.setData(bundle);
            RegisterActivity.this.myHandler.sendMessage(mess);
        }
    } ;
}
