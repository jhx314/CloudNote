package com.jeson.cloudenote.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("注册");
        final EditText etName = (EditText) findViewById(R.id.etRegisterUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etRegisterPassword);
        final EditText etRepassword = (EditText) findViewById(R.id.etRePassword);

        final Button btnRegiser = (Button) findViewById(R.id.btnRegister);
        final Button btnExit = (Button) findViewById(R.id.btnExit);

        btnRegiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                String repassword = etRepassword.getText().toString();

                Toast.makeText(RegisterActivity.this,"Name:"+name+",Password"+password+",RePassword"+repassword,Toast.LENGTH_SHORT).show();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
