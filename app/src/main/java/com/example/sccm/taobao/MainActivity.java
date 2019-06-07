package com.example.sccm.taobao;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.sccm.javabean.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ib;//imagebutton_search

    //主页viewpage声明
    private ViewPager vp;
    //主页ID声明
    private Button Tab_pagehome;
    private Button Tab_goods;
    private Button Tab_activity;
    private Button tab_show;
    //主页面Fragment声明
    private HomepageFragment pagehome;
    private GoodsFragment goods;
    private ActivityFragment activity;
    private ShowFragment show;

    //主页面mFragmentList链表声明
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();//声明一个Fragment链表
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        User user= (User) intent.getSerializableExtra("user");
        int id=user.getUser_Id();
        String find_name=user.getUser_Name();


        initViews_findID();
        search_listener();

        //主页ViewPage配置
        mFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),mFragmentList);
        vp.setOffscreenPageLimit(4);//设置ViewPage的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//设置ViewPage的初始页面选中第一帧
        Tab_pagehome.setTextColor(Color.parseColor("#66CDAA"));
        ViewPage_listener();
    }

    //初始化控件和声明事件
    public void initViews_findID(){
        //主页id查找
        ib=(ImageButton)super.findViewById(R.id.imageButton_search);
        Tab_pagehome=(Button)super.findViewById(R.id.button_pagehome);
        Tab_goods=(Button)super.findViewById(R.id.button_goods);
        Tab_activity=(Button)super.findViewById(R.id.button_activity);
        tab_show=(Button)super.findViewById(R.id.button_show);
        vp=(ViewPager)super.findViewById(R.id.viewpage_index);
        //声明事件，设置主页、商品、活动监听
        Tab_pagehome.setOnClickListener(this);
        Tab_goods.setOnClickListener(this);
        Tab_activity.setOnClickListener(this);
        tab_show.setOnClickListener(this);
        //创建主页Fragment实例
        pagehome=new HomepageFragment();
        goods=new GoodsFragment();
        activity=new ActivityFragment();
        show=new ShowFragment();
        //给FragmentList添加数据
        mFragmentList.add(pagehome);
        mFragmentList.add(goods);
        mFragmentList.add(activity);
        mFragmentList.add(show);
}

    //搜索页面监听
    public void search_listener(){
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,search.class));
            }
        });
    }

    //主页ViewPage的监听
    public void ViewPage_listener(){
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //该方法在页面选中时被调用
            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }
            //此方法是在状态改变时调用，其中arg0这个参数有三种状态（0,1,2）
            //arg0==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //点击主页、商品、活动动态修改ViewPager内容
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_pagehome:
                vp.setCurrentItem(0,true);
                break;
            case R.id.button_goods:
                vp.setCurrentItem(1,true);
                break;
            case R.id.button_activity:
                vp.setCurrentItem(2,true);
                break;
            case R.id.button_show:
                vp.setCurrentItem(3,true);
                break;
        }
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    //由ViewPage的滑动改变顶部导航Button_text的颜色
    private void changeTextColor(int position) {
        if (position == 0) {
            Tab_pagehome.setTextColor(Color.parseColor("#66CDAA"));
            Tab_goods.setTextColor(Color.parseColor("#000000"));
            Tab_activity.setTextColor(Color.parseColor("#000000"));
            tab_show.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            Tab_pagehome.setTextColor(Color.parseColor("#000000"));
            Tab_goods.setTextColor(Color.parseColor("#66CDAA"));
            Tab_activity.setTextColor(Color.parseColor("#000000"));
            tab_show.setTextColor(Color.parseColor("#000000"));
        } else if (position == 2) {
            Tab_pagehome.setTextColor(Color.parseColor("#000000"));
            Tab_goods.setTextColor(Color.parseColor("#000000"));
            Tab_activity.setTextColor(Color.parseColor("#66CDAA"));
            tab_show.setTextColor(Color.parseColor("#000000"));
        } else if (position == 3) {
            Tab_pagehome.setTextColor(Color.parseColor("#000000"));
            Tab_goods.setTextColor(Color.parseColor("#000000"));
            Tab_activity.setTextColor(Color.parseColor("#000000"));
            tab_show.setTextColor(Color.parseColor("#66CDAA"));
        }
    }
}
