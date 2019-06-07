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
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    DBUtils db=null;
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    User user;
    Bundle bundle;

    private Button button_login;
    private TextView text_nozhuce;
    private EditText et_login_id;
    private EditText et_login_password;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login=findViewById(R.id.button_login);
        text_nozhuce=findViewById(R.id.text_nozhuce);
        et_login_id=findViewById(R.id.et_login_id);
        et_login_password=findViewById(R.id.et_login_password);

        text_nozhuce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Datafind datafind=new Datafind();
                Thread thread=new Thread(datafind);
                thread.start();
            }
        });
    }

    class Datafind implements Runnable{
        @Override
        public void run() {
            find_user();
            if(i==1){
                bundle.putSerializable("user",user);
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(LoginActivity.this,MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText( LoginActivity.this, "账号密码错误或账号不存在！", Toast.LENGTH_SHORT).show();
            }

        }

        public void find_user(){
            int find_id;
            String find_name;
            db = new DBUtils();
            conn = DBUtils.getConnection("squirrel_store");//获取数据库连接
            String name=et_login_id.getText().toString();
            String pass=et_login_password.getText().toString();
            String sql="select * from store_users where user_Name=? and user_Password=?";
            try {
                pstm=conn.prepareStatement(sql);
                pstm.setString(1,name);
                pstm.setString(2,pass);
                rs=pstm.executeQuery();
                if (rs.next())
                {
                    find_id=rs.getInt("user_Id");
                    find_name=rs.getString("user_Name");
                    user.setUser_Id(find_id);
                    user.setUser_Name(find_name);
                    i=i+1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
