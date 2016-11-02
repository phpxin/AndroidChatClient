package com.lx.chat.mychatclient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends Activity {
    public EditText ipaddr;
    public EditText username;
    public EditText password;
    public Button loginbtn;

    public String remoteIp = "";
    public int remotePort = 10001;

    public String authcode = "";
    
    private Bundle _b;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setTitle("登陆");
		
		Config.rdThread = new ReadData();
		Config.rdThread.setHandler(this.myHandler);
		Config.rdThread.setDoit(true);
		
        ipaddr = (EditText)findViewById(R.id.ipaddr);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        loginbtn = (Button)findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //(new Thread(sockThread)).start();
                Config.ServerAddr = ipaddr.getText().toString() ;
                (new Thread(sockHttpConnection)).start();
            }
        });
	}

    Runnable sockHttpConnection = new Runnable() {

        @Override
        public void run() {

            URL url = null;

            int uid = Config.my.getUid() ;

            try {
                // 创建url对象
                SysLog.log("url is "+NetUtil.loginApi);
                url = new URL(NetUtil.loginApi);

            } catch (MalformedURLException e1) {
                SysLog.log(e1.getMessage());

            }

            OutputStream ots = null;
            BufferedReader ins = null;

            // 通过http将用户信息提交
            HttpURLConnection hUrlConn = null;
            try {
                // 通过url对象获取并初始化http连接对象
                hUrlConn = (HttpURLConnection) url.openConnection();

                // 设置开启post方法
                hUrlConn.setRequestMethod("POST");

                // 打开http连接
                hUrlConn.connect();

                //req = new BufferedWriter(new OutputStreamWriter(hUrlConn.getOutputStream())) ;
                //req.write("uid="+uid);
                //发送post请求，post以键值对夹&符保存在正文中发送
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                ots=hUrlConn.getOutputStream();
                String queryString = "account="+_username+"&pwd="+_password;
                ots.write(queryString.getBytes());


                // 接收返回值
                ins = new BufferedReader(new InputStreamReader(
                        hUrlConn.getInputStream()));

                String content = "";
                String line = "";

                while ((line = ins.readLine()) != null) {
                    content += line;
                }

                //通过句柄（就是一个管子）更新列表数据
                Message mess=new Message();
                mess.what = HandleMess.MESS_HTTP_LOGINOK;
                Bundle _bb = new Bundle();
                _bb.putString("content", content);
                mess.setData(_bb);
                MainActivity.this.myHandler.sendMessage(mess);

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (ProtocolException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                    if (ots != null) {
                        ots.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }

    };
	
	Runnable sockThread=new Runnable(){

        @Override
        public void run() {
            // 在线程开启socket 不阻塞UI
            // 构建socket client对象
            Log.i("lixin","1");

            int _rdata_l = 0;
            short _protocol = 0;
            int _flag = 0;
            
            remoteIp = ipaddr.getText().toString() ;
            
            Config.ServerAddr = remoteIp;

            try {
                Config.client = new Socket(InetAddress.getByName(remoteIp), remotePort);
                
                
                Config.clientSend = new BufferedOutputStream(Config.client.getOutputStream());

                //登陆
                /*
                String _username = username.getText().toString();
                String _password = password.getText().toString();

                char username[] = new char[200];	//由于c语言字符串需要定长，这里必须指定长度
                for (int i = 0; i < _username.length(); i++) {
                    username[i] = _username.charAt(i);
                }

                char password[] = new char[200];
                for (int i = 0; i < _password.length(); i++) {
                    password[i] = _password.charAt(i);
                }
                */

                char _authcode[] = new char[1024] ;
                for(int i=0; i<authcode.length(); i++){
                    _authcode[i] = authcode.charAt(i) ;
                }

                //int package_len = 4 + 2 + 200 + 200 ;
                int package_len = 4 + 2 + 1024 ;

                ByteArrayOutputStream clientPackageStream = new ByteArrayOutputStream(package_len);
                DataOutputStream _clientPackageStream = new DataOutputStream(clientPackageStream);

                _clientPackageStream.writeInt(package_len);
                _clientPackageStream.writeShort(Protocol.login);
                //_clientPackageStream.write((new String(username, 0, username.length)).getBytes());
                //_clientPackageStream.write((new String(password, 0, username.length)).getBytes());
                _clientPackageStream.write((new String(_authcode, 0, _authcode.length)).getBytes());

                byte[] clientPackageBuf = clientPackageStream.toByteArray();	//字节数据

                clientPackageStream.close();

                Config.clientSend.write(clientPackageBuf, 0, clientPackageBuf.length);
                Config.clientSend.flush();

                (new Thread(Config.rdThread)).start(); // 开启读取线程
                
            } catch (IOException e) {
                Log.i("lixin",e.getMessage());
            } catch(Exception e){
            	 Log.i("lixin",e.getMessage());
            }
        }

    };
    
    
	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			_b = msg.getData(); // bundle这东西就是个包，数据打包用它传递过来

			switch (msg.what) {

			case HandleMess.MESS_LOGINOK :
				
				int flag = _b.getInt("flag") ;
				
				if(flag == 1){
	                //使用意图对象切换窗口
	                Intent showDemoPage=new Intent();
	                //showDemoPage.setClass(getApplicationContext(), UlistActivity.class);
                    //showDemoPage.setClass(getApplicationContext(), UsersActivity.class);
                    showDemoPage.setClass(getApplicationContext(), HomeActivity.class);
	                //切换窗体
	                startActivity(showDemoPage);
				}else{
					Toast.makeText(getApplicationContext(), "登陆失败：用户名/密码错误", Toast.LENGTH_SHORT).show();
				}

				break;

            case HandleMess.MESS_HTTP_LOGINOK:
                String content = _b.getString("content") ;
                SysLog.log("content is "+content);

                try{
                    JSONTokener jsonParser = new JSONTokener(content);

                    JSONObject jsonRoot = (JSONObject) jsonParser.nextValue();

                    int code = jsonRoot.getInt("code") ;

                    if (code!=0){
                        Toast.makeText(getApplicationContext(), "request error code = "+code, Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject data = (JSONObject) jsonRoot.getJSONObject("data");
                        authcode = data.getString("authcode");

                        //请求聊天socket服务器
                        (new Thread(sockThread)).start();
                    }
                }catch (JSONException je){
                    SysLog.log("http login err content is "+content);
                    Toast.makeText(getApplicationContext(), "请求失败,请稍后重试", Toast.LENGTH_SHORT).show();
                }
                break;



			}
			super.handleMessage(msg);
		}
	};
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
