package com.mika.mentha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.mika.mentha.db.Users;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private  EditText namea;         //用户名输入框
    private  EditText passwd;        //密码输入框
    private  Switch show;            //开关
    private  Button login;          //登陆按钮
    private  TextView newView;       //新用户按钮


    String regular = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //设置页面标题
        this.setTitle("用户登陆");

        //限制密码输入框只能输入字母以及数字
        this.passwd=(EditText)super.findViewById(R.id.passwdedit);
        passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.show=(Switch)super.findViewById(R.id.switch1);
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //CheckBox选中，显示明文
                  passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    //CheckBox取消选中，显示暗文
                  passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
//                //光标移至最末端
//                passwd.setSelection(passwd.getText().toString().length());
            }
        });

        //点击的登陆转到显示日记页面
        this.login = (Button)super.findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = namea.getText().toString();
                String pass = passwd.getText().toString();
                if (!name.equals("") && !pass.equals("")) {


                    List<Users> users = DataSupport.findAll(Users.class);
                    for(Users in : users){
                        if (name.equals(in.getUsername())){             //判断用户名是否存在
                            if (pass.equals(in.getPasswd())){           //判断密码是否正确
                                List<Users> useraa = DataSupport.findAll(Users.class);
                                Users usersa = null;
                                Boolean userature = false;
                                for (Users users1:users){       //找到用户名存放到对象中
                                    if (users1.getUsername().equals(name)){
                                        userature=true;
                                        usersa=users1;
                                        break;
                                    }
                                }
                                if (userature){
                                    Intent loginIntent = new Intent(LoginActivity.this,ShowActivity.class);
                                    loginIntent.putExtra("Username",name);      //把name传到Show活动中
                                    loginIntent.putExtra("Number",usersa.getId());  //把userid传到Show活动中
                                    startActivity(loginIntent);
                                    Util.showToast(LoginActivity.this,"登陆成功");
                                }
                            }
                            else{
                                Util.showToast(LoginActivity.this,"密码错误");
                            }
                        }
                        else{
                            Util.showToast(LoginActivity.this,"用户名不存在");
                            }
                    }
                }
                else{
                    Util.showToast(LoginActivity.this,"请输入用户名和密码");
                    }

            }
       });



        //点击的新用户登陆跳转注册页面
        this.namea = (EditText)super.findViewById(R.id.nameEdit);
        this.newView = (TextView)super.findViewById(R.id.newView);
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}


