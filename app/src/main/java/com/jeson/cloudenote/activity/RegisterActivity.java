package com.jeson.cloudenote.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.user.IUserAction;
import com.cloudnote.core.user.UserActionImpl;
import com.roger.catloadinglibrary.CatLoadingView;

public class RegisterActivity extends AppCompatActivity {

    private IUserAction userAction;
    private CatLoadingView loadingView;
    private final static int REGIST_SUCCESS = 0;
    private final static int REGIST_FAILURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("注册");
        final EditText etName = (EditText) findViewById(R.id.etRegisterUserName);
        final EditText etNickName = (EditText) findViewById(R.id.et_register_nickname);
        final EditText etPassword = (EditText) findViewById(R.id.etRegisterPassword);
        final EditText etRepassword = (EditText) findViewById(R.id.etRePassword);

        final Button btnRegiser = (Button) findViewById(R.id.btnRegister);
        final Button btnExit = (Button) findViewById(R.id.btnExit);
        loadingView = new CatLoadingView();

        //RequestQueue执行请求的请求队列
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        userAction = new UserActionImpl(requestQueue);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REGIST_SUCCESS:
                        loadingView.dismiss();
                        String message = msg.getData().getString("msg");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case REGIST_FAILURE:
                        loadingView.dismiss();
                        String message1 = msg.getData().getString("msg");
                        Toast.makeText(getApplicationContext(), message1, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        btnRegiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.show(getSupportFragmentManager(), "");
                new Thread() {
                    @Override
                    public void run() {
                        String name = etName.getText().toString();
                        String password = etPassword.getText().toString();
                        String repassword = etRepassword.getText().toString();
                        String nickname = etNickName.getText().toString();
                        userAction.Regist(name, password, repassword, nickname, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Object data) {
                                Message msg = new Message();
                                msg.what = REGIST_SUCCESS;
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", data.toString());
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onFailure(String message) {
                                Message msg = new Message();
                                msg.what = REGIST_FAILURE;
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", message);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }.start();
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
