package com.lx.chat.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lx.chat.fragments.UsersFragment;
import com.lx.chat.mychatclient.R;


/**
 * Created by lx on 16/10/13.
 */
public class MenuAdapter extends BaseAdapter {

    public static int BTN_TAG = 1 ;

    private ArrayList<HashMap<String, Object>> menus; //要绑定的数据
    private int resouce; //绑定条目的界面
    private Context context;
    private LayoutInflater inflater ;
    private Activity fa ;

    //构造方法获取数据和界面
    public MenuAdapter(Context _context , Activity _fa, ArrayList<HashMap<String, Object>> menus, int resouce){
        this.menus = menus ;
        this.resouce=resouce;
        this.context = _context ;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.fa = _fa ;
    }


    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            //resource 指定使用哪个资源文件(item.xml)生成view对象
            //设置填充对象
            convertView=inflater.inflate(this.resouce, null);//null 代表没有根元素
        }

        //使用findViewById方法 获取item.xml每个条目的 textView对象
        ImageView lv_name=(ImageView)convertView.findViewById(R.id.gobtn);
        Map<String, Object> p = this.menus.get(position);

        //填充数据
        int resId = (int)p.get("res_id");
        lv_name.setImageResource(resId);

        //跳转事件中需要判断点击的是哪个条目,使用一个tag来标识,当然这个tag的参数是一个对象,也可以保存更多内容,比如一个类对象
        lv_name.setTag(position);

        //监听点击事件,跳转到对应Fragment
        lv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = fa.getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                Fragment bf = null;

                int tag = (int)v.getTag() ;


                switch (tag){
                    case 0:
                        bf = new UsersFragment();
                        break;
//                    case 1:
//                        bf = new LoginContent();
//                        break;
//                    case 2:
//                        bf = new UserInfo();
//                        break;
                    default:
                        bf = new UsersFragment(); //R.id.btn1
                        break;
                }

                transaction.replace(R.id.content, bf);
                transaction.commit();
            }
        });



        return convertView;

    }
}
