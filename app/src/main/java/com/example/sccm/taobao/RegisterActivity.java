package com.example.sccm.taobao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sccm.javabean.User;
import com.example.sccm.utility.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {
    DBUtils db=null;
    Connection conn;
    PreparedStatement pstm;
    private Button button_register;
    private TextView text_yeszhuce;
    private EditText et_register_id;
    private EditText et_register_password;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button_register=findViewById(R.id.button_register);
        text_yeszhuce=findViewById(R.id.text_yeszhuce);
        et_register_id=findViewById(R.id.et_register_id);
        et_register_password=findViewById(R.id.et_register_password);

        text_yeszhuce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Database_operaton database_operaton=new Database_operaton();
                Thread t = new Thread(database_operaton);
                t.start();
            }
        });
    }

    class Database_operaton implements Runnable{

        @Override
        public void run() {
            add_user();
            if(i==1){
                User user=new User(et_register_id.getText().toString(),et_register_password.getText().toString());
                register_goodscar();
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        //为注册的用户添加购物车
        public void register_goodscar(){
            String name=et_register_id.getText().toString();
            db = new DBUtils();
            conn = DBUtils.getConnection("squirrel_store");//获取数据库连接
            String sql="create table '"+name+ "'(goods_Id int(50) auto_increment primary key,goods_Name varchar(100),goods_Price float)";
            try {
                pstm = conn.prepareStatement(sql);
                pstm.executeUpdate();
                pstm.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //注册用户
        public int add_user(){
            if(et_register_id.getText().toString().equals("")||et_register_password.getText().toString().equals("")){
                Toast.makeText( RegisterActivity.this, "账号密码都不能为空！", Toast.LENGTH_SHORT).show();
            }
            else{
                String name=et_register_id.getText().toString();
                String pass=et_register_password.getText().toString();
                db = new DBUtils();
                conn = DBUtils.getConnection("squirrel_store");//获取数据库连接
                String sql="insert into store_users(user_Name,user_Password) VALUES (?,?) ";
                try {
                    pstm = conn.prepareStatement(sql);
                    pstm.setString(1,name);
                    pstm.setString(2,pass);
                    int j=pstm.executeUpdate();
                    if(j!=0){
                        i=1;
                    }else{
                        i=0;
                    }
                    pstm.close();
                    conn.close();

                } catch (SQLException e) {
                    Toast.makeText( RegisterActivity.this, "账号已存在！", Toast.LENGTH_SHORT).show();
                }
            }
            return i;
        }
    }
}

