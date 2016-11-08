package com.lx.chat.mychatclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.bean.RegisterRequest;

public class RegisterActivity extends Activity {

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
                //JSON.
                RegisterRequest rr = new RegisterRequest();
                rr.setAccount("lixinxin");
                rr.setPwd("123");
                rr.setPwdConfirm("123");
                String jstr = JSON.toJSONString(rr);

                Toast.makeText(getApplicationContext(), jstr, Toast.LENGTH_LONG).show();

            }
        });
    }
}
