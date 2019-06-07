package com.example.sccm.taobao;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

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

public class SaleFragment extends Fragment {

    View view=null;
    DBUtils db=null;
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    GridView gv;
    SimpleAdapter adapter;

    //判断值
    private static final int COMPLETED = 0;
    //主线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==COMPLETED){
                gv.setAdapter(adapter);//为GridView加入适配器
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_sale, container, false);
        init();
        //实例化一个子线程
        db_dao db_dao = new db_dao();
        //子线程启动
        db_dao.start();
        return view;
    }

    //初始化
    public void init(){
        gv=view.findViewById(R.id.grid_sale);
    }

    //新建子线程db_dao
    public class db_dao extends Thread implements AdapterView.OnItemClickListener {
        int pid,psale;
        String name, pic;
        float price;
        //商品Javabean
        Goods goods2;
        //
        Bundle bundle;
        //List集合中的对象是一个Map对象,而这个Map对象的键是String类型,值是Object类型    List以Map接口对象为列表对象。
        //Map以String为键，以Object为值。  List里只能放Map类型的对象，而这个Map类型的对象又只能放以String类型为键，以Object类型为值的键值对。
        List<Map<String, Object>> data_list = new ArrayList<>();

        //运行
        public void run() {
            db = new DBUtils();
            conn = DBUtils.getConnection("squirrel_store");//获取数据库连接
            String sql = "select * from goods order by goodsSale DESC";//定义SQL查询语句
            try {
                pstm = conn.prepareStatement(sql);//预编译sql语句,执行速度快
                rs = pstm.executeQuery();//rs接收结果集
                while (rs.next()) {
                    //从数据库中获得数据的结果集rs，传值给自定义变量pic，name，price
                    pid = rs.getInt("goodsId");
                    pic = rs.getString("goodsPic");
                    name = rs.getString("goodsName");
                    price = rs.getFloat("goodsPrice");
                    psale=rs.getInt("goodsSale");
                    Map<String, Object> map = new HashMap<>();
                    //Map<String,Object>前者为String类型的键，后者为object类型的值
                    map.put("goodsId", pid);
                    map.put("goodsPic", getResourceId(pic));
                    map.put("goodsName", name);//subsequent字符串截取函数
                    map.put("goodsPrice", price);
                    map.put("goodsSale",psale);
                    //List<Map>集合中存放的对象是一个Map类型的对象
                    data_list.add(map);
                }
                ////创建适配器对象，并且通过参数设置类item_layout的布局样式和数据源
                /*参数1：传入一个上下文作为参数*/
                /*参数2：传入相对应的数据源，这个数据源不仅仅是数据而且还是和界面相耦合的混合体。*/
                /*参数3：设置具体某个items的布局，需要是新的布局，而不是ListView控件的布局*/
                /*参数4：传入上面定义的键值对的键名称,会自动根据传入的键找到对应的值*/
                /*参数5：传入items布局文件中需要指定传入的控件，这里直接上id即可*/
                adapter = new SimpleAdapter(getActivity(), data_list, R.layout.item_layout, new String[]{"goodsPic", "goodsName", "goodsPrice"}, new int[]{R.id.imageView_goods, R.id.textView_goods_name, R.id.textView_goods_price});
                //线程message
                Message msg = new Message();
                msg.what = COMPLETED;
                handler.sendMessage(msg);
                //设置GridView监听
                gv.setOnItemClickListener(this);
                //实例化商品
                bundle = new Bundle();
                conn.close();
                pstm.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //通过图片名称获得图片ID
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

        //设置GridView监听，并通过bundle将对象传值给对应页面
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //putSerializable方法存入键值对
            goods2=new Goods(i,data_list.get(i).get("goodsPic").toString(),data_list.get(i).get("goodsName").toString()
                    ,Float.valueOf(data_list.get(i).get("goodsPrice").toString()),psale);
            bundle.putSerializable("goods",goods2);
            Intent intent=new Intent(getActivity(),Test_DetailedtActivity.class);
            //putExtras存放bundle键值对
            intent.putExtras(bundle);
            //页面跳转
            startActivity(intent);
        }
    }

}
