package com.lx.chat.mychatclient;

public class HandleMess {

	//socket 用的
	public final static int MESS_RECVMSG = 1;
	public final static int MESS_LOGINOK = 2;
	public final static int MESS_UPUSERLIST = 3;
	public final static int MESS_UPUSERINFO = 4;

	public final static int MESS_DRAWHEADER = 5;  // 绘制用户头像


	//http 用的
	public final static int MESS_HTTP_LOGINOK = 10001; // 登录成功
	public final static int MESS_HTTP_GET_INFO_OK = 10002; // 获取用户信息成功
	public final static int MESS_HTTP_REGISTER_DONE = 10003; // 完成注册请求
	public final static int MESS_HTTP_INVITE_DONE = 10004; // 完成好友申请
	public final static int MESS_HTTP_INVITE_LIST_DONE = 10005; // 完成好友申请列表请求
}
