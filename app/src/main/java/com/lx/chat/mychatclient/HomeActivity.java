package com.lx.chat.mychatclient;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

import com.lx.chat.adapters.MenuAdapter;
import com.lx.chat.fragments.UsersFragment;

public class HomeActivity extends Activity {


    GridView menu = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        menu = (GridView) findViewById(R.id.menu) ;

        ArrayList<HashMap<String, Object>> menus = new ArrayList<>() ;

        HashMap<String, Object> mitem = new HashMap<>() ;
        mitem.put("res_id", R.drawable.a1) ;
        menus.add(mitem);
        HashMap<String, Object> mitem2 = new HashMap<>() ;
        mitem2.put("res_id", R.drawable.a2) ;
        menus.add(mitem2);
        HashMap<String, Object> mitem3 = new HashMap<>() ;
        mitem3.put("res_id", R.drawable.a3) ;
        menus.add(mitem3);

        MenuAdapter mAdapter = new MenuAdapter(this, this, menus, R.layout.menu_item) ;
        menu.setAdapter(mAdapter);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment bf = new UsersFragment();
        transaction.replace(R.id.content, bf);
        transaction.commit();
    }
}
