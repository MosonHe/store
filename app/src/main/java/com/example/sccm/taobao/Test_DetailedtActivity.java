package com.example.sccm.taobao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sccm.javabean.Goods;

import java.lang.reflect.Field;

public class Test_DetailedtActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    ImageView imageView;

    Intent intent;
    Goods goods;

    String find_pic,find_name;
    float find_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__detailedt);
        //控件Id获取
        imageView=(ImageView) findViewById(R.id.test_imageView_detailed_img);
        name=(TextView) findViewById(R.id.test_textView_detailed_name);
        price=(TextView)findViewById(R.id.test_textView_detailed_price);

        show_goods();
    }
    public void show_goods(){
        intent=getIntent();
        goods=(Goods)intent.getSerializableExtra("goods");

        find_name=goods.getName();
        find_price=goods.getPrice();
        find_pic=goods.getPic();

        name.setText(find_name);
        price.setText(String.valueOf(find_price));
        imageView.setImageResource(Integer.valueOf(find_pic));
    }
}
