package com.example.grad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class infoActivity extends AppCompatActivity {

    //앱에 대한 정보를 가진 페이지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appinfo);

        findViewById(R.id.exit).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}
