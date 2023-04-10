package com.example.grad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashActivity extends AppCompatActivity {

    //첫 시작 화면

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //handler를 사용하여 시작화면에서 로그인페이지로 넘어가기 전의 시간을 조절
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        },3000);        //3초간 화면 표시 후 intent
    }
}
