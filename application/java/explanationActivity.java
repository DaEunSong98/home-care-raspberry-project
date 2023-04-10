package com.example.grad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class explanationActivity extends AppCompatActivity {

    //앱에 대한 설명이 들어있는 페이지의 메뉴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explan_menu);

    }

    public void onclick(View view){
        Intent intent=null;
        switch(view.getId()){
            case R.id.appfunc:  //앱 기능에 대해 설명하는 페이지로 이동
                intent=new Intent(getApplicationContext(),funcActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.appinfo:  //앱 정보에 대한 페이지로 이동
                intent=new Intent(getApplicationContext(),infoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.exit:
                intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

}
