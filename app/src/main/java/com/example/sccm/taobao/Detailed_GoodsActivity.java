package com.example.sccm.taobao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.sccm.javabean.Goods;
import com.example.sccm.utility.DBUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Detailed_GoodsActivity extends AppCompatActivity {
    Intent intent;
    Goods goods;
    int goods_id;
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    DBUtils db;
    find_goodsinfo findGoodsinfo;
    TextView name;
    TextView price;
    ImageView imageView;
    List<Map<String,Object>> data_list=new ArrayList<>();
    //判断值
    private static final int COMPLETED = 0;
    //主线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==COMPLETED){
                imageView.setImageResource(getResourceId(data_list.get(0).get("goodsPic").toString()));
                name.setText(data_list.get(0).get("goodsName").toString());
                price.setText(data_list.get(0).get("goodsPrice").toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__goods);
        //控件Id获取
        imageView=(ImageView) findViewById(R.id.imageView_detailed_img);
        name=(TextView) findViewById(R.id.textView_detailed_name);
        price=(TextView)findViewById(R.id.textView_detailed_price);
        //实例化一个线程
        findGoodsinfo=new find_goodsinfo();
        //线程启动
        findGoodsinfo.start();
    }
    //子线程 实现数据查询
    public class find_goodsinfo extends Thread {
        //定义
        String find_name, find_pic;
        float find_price;
        public void run() {
           intent=getIntent();
//           通过getSerializableExtra()方法获取并设置对应的数据
            //此处获得的是控件的ID
           goods=(Goods) intent.getSerializableExtra("goods");
           //控件ID+1即为数据库图片ID
           goods_id=goods.getPid()+1;
           //获取连接
            db = new DBUtils();
            conn = DBUtils.getConnection("squirrel_store");
            String sql = "select * from goods where goodsId = '" + goods_id + "'";
            try {
                pstm = conn.prepareStatement(sql);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    find_pic = rs.getString("goodsPic");
                    find_name = rs.getString("goodsName");
                    find_price = rs.getFloat("goodsPrice");
                    Map<String,Object> map=new HashMap<>();
                    map.put("goodsPic", find_pic);
                    map.put("goodsPrice", find_price);
                    map.put("goodsName", find_name);
                    data_list.add(map);
                }
                //向主线程传递消息
                Message msg=new Message();
                msg.what=COMPLETED;
                handler.sendMessage(msg);
                //释放连接
                rs.close();
                pstm.close();
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        //此方法为获得本地图片ID
        public int getResourceId(String name) {
            try {
                // 根据图片资源的文件名获得Field对象
                Field field = R.drawable.class.getField(name);
                // 取得并返回资源ID
                return Integer.parseInt(field.get(null).toString());
            } catch (Exception e) {
            }
            return 0;
        }
    }
    public int getResourceId(String name) {
        try {
            // 根据图片资源的文件名获得Field对象
            Field field = R.drawable.class.getField(name);
            // 取得并返回资源ID
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
        }
        return 0;
    }
}
