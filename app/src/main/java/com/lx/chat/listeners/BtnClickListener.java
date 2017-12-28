package com.lx.chat.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lx.chat.mychatclient.InviteActivity;
import com.lx.chat.mychatclient.R;

/**
 * Created by lx on 16/11/10.
 */

public class BtnClickListener implements View.OnClickListener {


    private Context context;
    private int fid ;

    private int inviteId ;

    public BtnClickListener(Context _context){

        this.context = _context ;
    }

    public void setFid(int fid){
        this.fid  = fid ;
    }

    public void setInviteId(int inviteId) { this.inviteId = inviteId ; }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.inviteBtn :
                //邀请用户
                Intent showDemoPage=new Intent(); //使用意图对象切换窗口
                showDemoPage.setClass(context, InviteActivity.class); //参数1是一个context对象，参数2是窗体类
                showDemoPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle mainBundle=new Bundle(); //设置bundle对象，传递数据
                //mainBundle.putString("fid", fid+"");
                mainBundle.putInt("fid", fid);
                showDemoPage.putExtras(mainBundle);

                context.startActivity(showDemoPage); //切换窗体
                break;
            case R.id.dealInviteBtn:
                //处理用户邀请
                Toast.makeText(this.context, "click deal btn invite id is "+inviteId, Toast.LENGTH_SHORT).show();
                break;
        }


    }
}

