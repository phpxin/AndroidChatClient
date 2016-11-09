package com.lx.chat.util;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.bean.GetInfoResponse;
import com.lx.chat.bean.User;
import com.lx.chat.mychatclient.Config;
import com.lx.chat.mychatclient.HandleMess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lx on 16/11/9.
 */
public class HttpRequest {

    String url ;
    String inputs ;

    public HttpRequest(String url, String inputs) {
        this.url = url ;
        this.inputs = inputs ;
    }

    public static <T> T doPostAndGetJson(String url, String inputs, Class<T> clazz ){
        HttpRequest hr = new HttpRequest(url, inputs) ;
        String json = hr.doPostAndGetJson() ;

        return JSON.parseObject(json, clazz) ;

    }

    public static String doPostAndGetData(String url, String inputs){

        HttpRequest hr = new HttpRequest(url, inputs) ;
        return hr.doPostAndGetJson() ;

    }

    public String doPostAndGetJson(){

        URL url = null;
        String responseJson = null ;
        try {
            // 创建url对象
            url = new URL(this.url);
        } catch (MalformedURLException e1) {
            Log.i("lixin", e1.getMessage());

        }

        // 通过http将用户信息提交
        HttpURLConnection hUrlConn;
        OutputStream ots = null;
        try {
            // 通过url对象获取并初始化http连接对象
            hUrlConn = (HttpURLConnection) url.openConnection();

            // 设置开启post方法
            hUrlConn.setRequestMethod("POST");

            // 打开http连接
            hUrlConn.connect();

            ots=hUrlConn.getOutputStream();
            String queryString = this.inputs;
            ots.write(queryString.getBytes());

            // 接收返回值
            BufferedReader ins = new BufferedReader(new InputStreamReader(
                    hUrlConn.getInputStream()));

            String content = "";
            String line = "";

            while ((line = ins.readLine()) != null) {
                content += line;
            }

            Log.i("lixin111",content);

            //responseJson = JSON.parseObject(content, clazz) ;
            responseJson = content ;

        } catch (MalformedURLException e) {

            Log.i("lixin", e.getMessage());

        } catch (ProtocolException e) {
            Log.i("lixin", e.getMessage());
        } catch (IOException e) {
            Log.i("lixin", e.getMessage());
        }

        return responseJson;
    }



}
