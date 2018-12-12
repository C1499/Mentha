package com.mika.mentha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.mika.mentha.db.Text;
import com.mika.mentha.db.Users;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Switch sshow;       //开关
    private EditText onepasswd; //第一个密码输入框
    private EditText twopasswd; //第二个密码输入框
    private EditText naeregister;  //用户名输入框
    private Button register;    //注册按钮
    private Boolean pname = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sshow=(Switch)findViewById(R.id.switch2);
        naeregister = (EditText)findViewById(R.id.registernameEdit);
        onepasswd = (EditText)findViewById(R.id.registerEdit1);
        twopasswd = (EditText)findViewById(R.id.registeredit2);
        onepasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        twopasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        Connector.getDatabase();

        sshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //CheckBox选中，显示明文
                    onepasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    twopasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //CheckBox取消选中，显示暗文
                    onepasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    twopasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
//                //光标移至最末端
//                passwd.setSelection(passwd.getText().toString().length());
            }
        });

        this.register = findViewById(R.id.registerbutton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = naeregister.getText().toString();
                String passwd = onepasswd.getText().toString();
                String passwd2 = twopasswd.getText().toString();
                if (!names.equals("") && !passwd.equals("")) {
                    if (!passwd2.equals("")) {
                        Users user = new Users();
                        List<Users> username = DataSupport.select("username").find(Users.class);
                        for (int i = 0; i < username.size(); i++) {
                            Log.d("==", "onClick: "+ username.get(i).getUsername());
                            if (names.equals(username.get(i).getUsername())) {
                                Util.showToast(RegisterActivity.this, "用户名已存在");
                                pname = false;
                            }
                        }
                        if (pname) {
                            if (passwd2.equals(passwd)) {
                                user.setUsername(names);
                                user.setPasswd(passwd2);
                                user.save();
                                if (user.save()) {
                                    Util.showToast(RegisterActivity.this, "注册成功");
                                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);
                                } else {
                                    Util.showToast(RegisterActivity.this, "注册失败");
                                }
                            } else {
                                Util.showToast(RegisterActivity.this, "两次密码输入不一致");
                                naeregister.setText("");
                                onepasswd.setText("");
                                twopasswd.setText("");
                            }

                        }
                    }
                        }

                else{
                    Util.showToast(RegisterActivity.this, "请输入用户名和密码");
                    naeregister.setText("");
                    onepasswd.setText("");
                    twopasswd.setText("");
                        }
                }
        });
    }
}
