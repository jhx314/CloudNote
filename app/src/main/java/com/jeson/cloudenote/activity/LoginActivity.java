package com.jeson.cloudenote.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.user.IUserAction;
import com.cloudnote.core.user.UserActionImpl;
import com.roger.catloadinglibrary.CatLoadingView;


public class LoginActivity extends AppCompatActivity {

    private IUserAction userAction;
    private final static int LOGIN_SUCCESS = 0;
    private final static int LOGIN_FAILURE = 1;

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
        final CatLoadingView loadingView = new CatLoadingView();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LOGIN_SUCCESS:
                        loadingView.dismiss();
                        Bundle bundle = msg.getData();
                        String userId = bundle.getString("userId");
                        SharedPreferences.Editor editor = getSharedPreferences("user", Activity.MODE_PRIVATE).edit();
                        editor.putString("userId", userId);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case LOGIN_FAILURE:
                        loadingView.dismiss();
                        Bundle bundle1 = msg.getData();
                        String error = bundle1.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.show(getSupportFragmentManager(), "loadingView");
                new Thread() {
                    @Override
                    public void run() {
                        userAction.Login(etUserName.getText().toString(), etPassword.getText().toString(), new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Object data) {
                                Message message = new Message();
                                message.what = LOGIN_SUCCESS;
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", data.toString());
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onFailure(String message) {
                                Message msg = new Message();
                                msg.what = LOGIN_FAILURE;
                                Bundle bundle = new Bundle();
                                bundle.putString("error", message);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }.start();

            }
        });
    }
}
