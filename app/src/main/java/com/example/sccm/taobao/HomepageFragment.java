package com.example.sccm.taobao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomepageFragment extends Fragment {


    //创建HomepageFragment类，继承Fragment，在类中的onCreatView方法中绑定对应的Xml布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);

    }
}
