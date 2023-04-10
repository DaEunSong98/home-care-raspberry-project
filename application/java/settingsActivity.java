package com.example.grad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class settingsActivity extends AppCompatActivity {

    //움직임에 대한 모드를 설절하기 위한 설정화면

    public static Integer state=1;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        context=this;

        ToggleButton toggle1 = (ToggleButton) findViewById(R.id.settingOn);
        final ToggleButton toggle2 = (ToggleButton) findViewById(R.id.settingMode);

        if(state==1){   //일반모드
            toggle1.setChecked(false);
            toggle2.setChecked(false);
        }else if(state==2){     //외출모드
            toggle1.setChecked(false);
            toggle2.setChecked(true);
        }else{      //off 모드
            toggle1.setChecked(true);
            toggle2.setEnabled(false);
        }

        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    state=0;
                    toggle2.setEnabled(false);
                } else {
                    // The toggle is disabled
                    state=1;
                    toggle2.setEnabled(true);
                }
            }
        });

        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    state=2;
                } else {
                    // The toggle is disabled
                    state=1;
                }
            }
        });


    }

    public void onclick(View view){
        switch(view.getId()){
            case R.id.exitBtn:
                //intent로 state 전달해주기
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
    }
}
