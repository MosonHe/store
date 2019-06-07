package com.example.sccm.taobao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class GoodsFragment extends Fragment implements View.OnClickListener {

    View view =null;
    private FrameLayout fl;

    //商品页ID声明
    private TextView text_synthesize;
    private TextView text_sale;
    private TextView text_newproduct;
    private TextView text_price;

    //商品页面Fragment声明
    private SynthesizeFragment synthesize;
    private SaleFragment sale;
    private NewProductFragment newProduct;
    private PriceFragment price;

    private FragmentManager fragmentManager;
    //创建Fragment_new类，继承Fragment，在类中的onCreatView方法中绑定对应的Xml布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_goods, container, false);
       return view;//返回当前视图！！！
    }

    //fragment对用户可见的时候 获得生命周期
    @Override
    public void onStart() {
        super.onStart();
        initComponent();
        setDefaultFragment();
    }
    //初始化goods_Fragment
    private void initComponent() {
        text_synthesize=(TextView)view.findViewById(R.id.textView_sysnthesize);
        text_price=(TextView)view.findViewById(R.id.textView_price);
        text_newproduct=(TextView) view.findViewById(R.id.textView_nweproduct);
        text_sale=(TextView)view.findViewById(R.id.textView_sale);

        text_synthesize.setOnClickListener(this);
        text_sale.setOnClickListener(this);
        text_newproduct.setOnClickListener(this);
        text_price.setOnClickListener(this);
    }
    //设置默认二层Fragment
    private void setDefaultFragment(){
        fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        synthesize=new SynthesizeFragment();
        transaction.replace(R.id.framelayout_goods,synthesize);
        transaction.commit();
    }
    //text_view监听
    @Override
    public void onClick(View view) {
        fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.textView_sysnthesize:
                if(synthesize==null){
                    synthesize=new SynthesizeFragment();
                }
                transaction.replace(R.id.framelayout_goods,synthesize);
                break;
            case R.id.textView_sale:
                if (sale==null){
                    sale=new SaleFragment();
                }
                transaction.replace(R.id.framelayout_goods,sale);
                break;
            case R.id.textView_nweproduct:
                if(newProduct==null){
                    newProduct=new NewProductFragment();
                }
                transaction.replace(R.id.framelayout_goods,newProduct);
                break;
            case R.id.textView_price:
                if(price==null){
                    price=new PriceFragment();
                }
                transaction.replace(R.id.framelayout_goods,price);
                break;
        }
        transaction.commit();
    }

}
