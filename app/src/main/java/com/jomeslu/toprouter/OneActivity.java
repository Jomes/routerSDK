package com.jomeslu.toprouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        TextView textView = (TextView) findViewById(R.id.infos);
        String jomeslu = getIntent().getStringExtra("jomeslu");
        jomeslu = TextUtils.isEmpty(jomeslu) ?"":jomeslu;
        int id = getIntent().getIntExtra("id", -1);
        String message = new StringBuffer("Success! get \n jomeslu:").append(jomeslu).append(" id : ").append(id).toString();
        textView.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
