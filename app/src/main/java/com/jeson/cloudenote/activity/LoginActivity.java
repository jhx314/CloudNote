package com.jeson.cloudenote.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.user.IUserAction;
import com.cloudnote.core.user.UserActionImpl;
import com.jeson.cloudenote.util.NetValue;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private IUserAction userAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("登录");

        final EditText etUserName = (EditText) findViewById(R.id.et_login_username);
        final EditText etPassword = (EditText) findViewById(R.id.et_login_password);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView tvRegister = (TextView) findViewById(R.id.tvRegister);

        //RequestQueue执行请求的请求队列
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        userAction = new UserActionImpl(requestQueue);
        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAction.Login(etUserName.getText().toString(), etPassword.getText().toString(), new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Object data) {
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
