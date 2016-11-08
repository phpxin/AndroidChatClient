package com.lx.chat.mychatclient;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.bean.RegisterRequest;
import com.lx.chat.bean.User;
import com.lx.chat.bean.UserListResponse;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        RegisterRequest rr = new RegisterRequest();
        rr.setAccount("lixinxin");
        rr.setPwd("123");
        rr.setPwdConfirm("123");
        String jstr = JSON.toJSONString(rr);

        //Toast.makeText(getApplicationContext(), jstr, Toast.LENGTH_LONG).show();
        Log.i("lx", "test: "+jstr);
    }

    public void test2(){
        String jstr = "{\"account\":\"lixinxin\",\"pwd\":\"123\",\"pwdConfirm\":\"123\"}" ;

        RegisterRequest rr = JSON.parseObject(jstr, RegisterRequest.class) ;

        Log.i("lx", "account: "+rr.getAccount()) ;
        Log.i("lx", "pwd: "+rr.getPwd()) ;
        Log.i("lx", "pwd confirm: "+rr.getPwdConfirm()) ;
    }

    public void test3(){
        String jstr = "{\"code\":0,\"data\":{\"userlist\":[{\"id\":\"1\",\"name\":\"lixin\",\"avatar\":\"1111\"}]}}" ;
        UserListResponse ulr = JSON.parseObject(jstr, UserListResponse.class) ;

        if (0!=ulr.getCode()){
            Log.i("lx", "msg: "+ulr.getMsg()) ;
        }else{
            Log.i("lx", "msg: success") ;

            ArrayList<User> users =  ulr.getData().getUserlist() ;

            for (User _u : users) {
                Log.i("lx", "id: "+_u.getId()) ;
                Log.i("lx", "name: "+_u.getName()) ;
                Log.i("lx", "avatar: "+_u.getAvatar()) ;
            }
        }
    }

    public void test4(){
        String jstr = "{\"code\":10003,\"msg\":\"授权已过期\"}" ;
        UserListResponse ulr = JSON.parseObject(jstr, UserListResponse.class) ;

        Log.i("lx", "code: "+ulr.getCode()) ;

        if (0!=ulr.getCode()){
            Log.i("lx", "msg: "+ulr.getMsg()) ;
        }else{
            Log.i("lx", "msg: success") ;
        }
    }
}