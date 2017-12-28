package com.lx.chat.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lx.chat.adapters.SearchListAdapter;
import com.lx.chat.bean.User;
import com.lx.chat.bean.UserListResponse;
import com.lx.chat.mychatclient.Config;
import com.lx.chat.mychatclient.DBA;
import com.lx.chat.mychatclient.HandleMess;
import com.lx.chat.mychatclient.MsgBean;
import com.lx.chat.mychatclient.R;
import com.lx.chat.mychatclient.UserBean;
import com.lx.chat.mychatclient.UserListAdapter;
import com.lx.chat.util.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;




    private String searchApi = "http://" + Config.ServerAddr + ":8080/index.php?module=user&action=search" ;
    private Bundle _b;
    List<UserBean> userlist;
    ListView lv;
    SearchListAdapter ulAdapter;

    private SwipeRefreshLayout listRefresh;

    private View thisView;

    private Button doSearchBtn;
    private EditText keywordsEt ;

    public UserSearchFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSearchFragment newInstance(String param1, String param2) {
        UserSearchFragment fragment = new UserSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user_search, container, false);


        View view;
        view = inflater.inflate(R.layout.fragment_user_search, container, false);
        thisView = view ;


        userlist = new ArrayList<UserBean>();

        ulAdapter = new SearchListAdapter(view.getContext(), userlist, R.layout.item_search) ;



        lv = (ListView)view.findViewById(R.id.lv) ;

        lv.setAdapter(ulAdapter);


        /*


        listRefresh = (SwipeRefreshLayout)view.findViewById(R.id.userListRf);

        listRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Thread(sockHttpConnection)).start();
            }
        });


        (new Thread(sockHttpConnection)).start();


        */

        Config.rdThread.setDoit(true);
        Config.rdThread.setHandler(this.myHandler);


        keywordsEt = (EditText) view.findViewById(R.id.keywords) ;
        doSearchBtn = (Button) view.findViewById(R.id.do_search) ;
        doSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new Thread(sockHttpConnection)).start();
            }
        });


        return view;
    }




    Runnable sockHttpConnection = new Runnable() {

        @Override
        public void run() {

            String keywords = keywordsEt.getEditableText().toString() ;

            String queryString = "authcode="+Config.my.getAuthcode()+"&keywords="+keywords;

            String content = HttpRequest.doPostAndGetData(searchApi, queryString) ;

            //通过句柄（就是一个管子）更新列表数据
            Message mess=new Message();
            mess.what = HandleMess.MESS_UPUSERLIST;
            Bundle _bb = new Bundle();
            _bb.putString("content", content);
            mess.setData(_bb);
            UserSearchFragment.this.myHandler.sendMessage(mess);

        }

    };



    // 主线程操作句柄
    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        @SuppressLint("NewApi")
        public void handleMessage(Message msg) {
            _b = msg.getData(); // bundle这东西就是个包，数据打包用它传递过来
            switch (msg.what) {
                case HandleMess.MESS_UPUSERLIST:

                    try{
                        // 解析json
                        String content = _b.getString("content");

                        UserListResponse userListResp = JSON.parseObject(content, UserListResponse.class) ;

                        if (userListResp==null){
                            Toast.makeText(thisView.getContext(), "data bad", Toast.LENGTH_SHORT).show();
                        }else{

                            if (userListResp.getCode() != 0){
                                Toast.makeText(thisView.getContext(), userListResp.getMsg(), Toast.LENGTH_SHORT).show();
                            }else {

                                ArrayList<User> users = userListResp.getData().getUserlist() ;

                                for (User user : users) {


                                    UserBean _b = new UserBean();


                                    _b.setUid(user.getId());
                                    _b.setAccount(user.getName());
                                    _b.setAvatar(user.getAvatar());
                                    _b.setNickname(user.getName());

                                    userlist.add(_b);

                                }

                                ulAdapter.notifyDataSetChanged();


                            }
                        }

                    }catch (NullPointerException e){

                        Log.i("lixin", e.getMessage()) ;
                    }finally {
                        //listRefresh.setRefreshing(false); // 停止刷新
                    }



                    break;

                case HandleMess.MESS_RECVMSG :
                    String sender = _b.getString("uid");

                    Log.i("lixin", "save db") ;

                    DBA dba = new DBA(thisView.getContext()) ;
                    MsgBean _msg = new MsgBean();
                    _msg.uid = Integer.parseInt(sender);
                    _msg.content = _b.getString("content");
                    _msg.fid = Config.my.getUid() ;
                    _msg.addtime = 0 ;
                    _msg.type = 1 ;

                    dba.insertMsg(_msg);

                    break;
            }
            super.handleMessage(msg);
        }
    };




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
