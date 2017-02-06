package com.zhuoxin.news.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zhuoxin.news.R;
import com.zhuoxin.news.entity.RegistInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.et_uid)
    EditText et_uid;
    @InjectView(R.id.et_pwd)
    EditText et_pwd;
    @InjectView(R.id.et_email)
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_regist, R.id.btn_login})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_regist:
                String uid = et_uid.getText().toString();
                String pwd = et_uid.getText().toString();
                String email = et_uid.getText().toString();
                if (uid == null || pwd == null || email == null) {
                    Toast.makeText(this, "信息不能为空，请填写后重试", Toast.LENGTH_SHORT).show();
                } else {
                    regist(uid, pwd, email);
                }
                break;
        }
    }

    /**
     * 用户注册的逻辑，根据用户名、用户密码、用户邮箱注册
     *
     * @param uid
     * @param pwd
     * @param email
     */
    private void regist(String uid, String pwd, String email) {
        String url = "http://118.244.212.82:9092/newsClient/user_register?ver=1&uid=" + uid + "&pwd=" + pwd + "&email=" + email;
        //用Volley进行网络数据的处理
        //1、建立请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //2、建立一个真正的请求，并设置参数和回调
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RegistInfo registInfo = gson.fromJson(response, RegistInfo.class);
                //判断服务器返沪状态是否为0
                if (registInfo.getStatus() == 0) {
                    Toast.makeText(LoginActivity.this, registInfo.getData().getExplain(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
            }
        });
        //3、把这个请求加入队列中，系统会自动执行
        requestQueue.add(stringRequest);
    }
}
