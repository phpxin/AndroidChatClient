package com.lx.chat.mychatclient;

/**
 * Created by lx on 16/11/2.
 */
public class NetUtil {

    public final static String loginApi = "http://" + Config.ServerAddr + ":8080/index.php?module=user&action=login" ;
    public final static String userListApi = "http://" + Config.ServerAddr + ":8080/index.php?module=user&action=getList" ;


}
