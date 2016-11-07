package com.lx.chat.mychatclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    ImageView retbtn ;
    TextView titleTv ;

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
    }
}
