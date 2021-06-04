package com.example.sqlitetestcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText username, password, repassword;
    Button signup, signin;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        username = (EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        repassword =(EditText) findViewById(R.id.repassword);
        signup =(Button) findViewById(R.id.btnSignup);
        signin =(Button) findViewById(R.id.btnSignin);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("")||repass.equals(""))
                    Toast.makeText(MainActivity2.this, "Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if (checkuser==false){
                            Boolean insert = DB.insertData(user, pass);
                            if (insert=true){
                                Toast.makeText(MainActivity2.this,"Đăng Ký Thành Công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity2.this,"Đăng Ký Không Thành Công",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity2.this,"Người Dùng Đã Tồn Tại! Làm Ơn Đăng Nhập",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity2.this,"Mật Khẩu Không Đúng",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}