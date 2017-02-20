package com.jomeslu.toprouter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Map;

import jomeslu.com.router.IRouteInterceptor;
import jomeslu.com.router.IRouteTableMapping;
import jomeslu.com.router.IRouterResultCallback;
import jomeslu.com.router.LogUtils;
import jomeslu.com.router.Router;

public class MainActivity extends AppCompatActivity {


    private TextView tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Router.initRouteTable(new IRouteTableMapping() {
            @Override
            public void operaRouterTable(Map<String, Class<? extends Activity>> map) {
                map.put("jomeslu://www", OneActivity.class);
                map.put("jomeslu://loginactivity", LoginActivity.class);
            }
        });

        getIntentParam("url");

         tips = (TextView) findViewById(R.id.tips);
        final EditText edit = (EditText) findViewById(R.id.edit);


        setTips(tips);

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = TextUtils.isEmpty(edit.getText().toString())?"test":edit.getText().toString();
                Router.build(url).setRouterResultCallback(new IRouterResultCallback() {
                    @Override
                    public void succeed(Uri uri) {
                        Toast.makeText(MainActivity.this, "success uri:" + uri.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(Uri uri, String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }).start(MainActivity.this);
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Router.build("jomeslu://www?{i:id}=168&{s:jomeslu}=jomeslu").setRouterResultCallback(new IRouterResultCallback() {
                    @Override
                    public void succeed(Uri uri) {
                        Toast.makeText(MainActivity.this, "success uri:" + uri.toString(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void failure(Uri uri, String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                    }
                }).start(MainActivity.this);

            }
        });

        findViewById(R.id.browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.build("http://androidblog.cn/index.php/Source").start(MainActivity.this);
            }
        });

        findViewById(R.id.inter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.build("jomeslu://www?{i:id}=168&{s:jomeslu}=jomeslu").setIRouteInterceptor(new IRouteInterceptor() {
                    @Override
                    public boolean interceptor() {
                        Router.build("jomeslu://loginactivity?{i:id}=168&{s:jomeslu}=jomeslu").start(MainActivity.this);
                        Toast.makeText(MainActivity.this, "login...", Toast.LENGTH_LONG).show();

                        return true;
                    }
                }).start(MainActivity.this);;

            }
        });

    }

    protected void setTips(TextView tips){
        String jomeslu = getIntent().getStringExtra("jomeslu");
        jomeslu = TextUtils.isEmpty(jomeslu) ?"网页参数":jomeslu;
        int id = getIntent().getIntExtra("id", -1);
        String message = new StringBuffer("来自网页启动!  \n 接收到网页参数 String jomeslu=").append(jomeslu).append(" \n 接收到网页参数 int id = ").append(id).toString();
        tips.setText(message);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentParam("url");
        setTips(tips);
    }


    private void getIntentParam(String param) {
        Intent intent = getIntent();
        if (intent != null && intent.getDataString() != null) {
            String spitParam=new StringBuffer(param).append("=").toString();
            if(intent.getDataString().contains(spitParam)) {
                String url = intent.getDataString().split(spitParam)[1];
                Router.build(url).start(this);
            }


        }

    }
}
