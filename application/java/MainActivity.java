package com.example.grad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //메인 메뉴 화면

    ArrayList<String> arrayList;
    String temp="";
    Integer state=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Bundle bun = new Bundle();
        // Thread 로 웹서버에 접속
        HandlerThread ht=new HandlerThread("ht");
        ht.start();
        final Handler h=new Handler(ht.getLooper()){
            @Override
            public void handleMessage(Message msg){
                try {
                    Bundle bun = msg.getData();
                    for (String key : bun.keySet()) {
                        if (key.equals("sensor")) {
                            ArrayList<String> arrayList = bun.getStringArrayList(key);
                        }
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        };

        // 센서값들을 받아와서 Thread로  실시간으로 동시에 처리해준다. -> 센서값 변경시 푸시 알림 주도록 함
        //메인의 다른 기능들과 독립적으로 실행 가능하도록 해줌 -> 다른 작업을 해도 알림이 옴
        new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        //super.run();
                        bun.clear();

                        sensorState sensor = new sensorState();
                        //sensorState에 정의된 함수를 사용하여 웹crawling
                        arrayList = sensor.getSensor();
                        state=sensor.getState();

                        bun.putStringArrayList("sensor", arrayList);
                        Message msg = h.obtainMessage();
                        msg.setData(bun);
                        h.sendMessage(msg);

                        for (int i = 0; i <= 1; i++) {
                            temp = arrayList.get(i);
                            String[] data = temp.split(":");
                            arrayList.set(i, data[1]);
                        }
                        //움직임센서값만 추출하여 변수 m에 저장
                        Integer m=Integer.parseInt(arrayList.get(0));
                        //화재경보센서만 추출하여 변수 f에 저장
                        Integer f=Integer.parseInt(arrayList.get(1));

                        //모드에 따라서 움직임에 대한 알림은 달라져야한다.
                        if(state==2) {  //외출모드(움직임이 있으면 알림)
                            if (m.equals(100)) {  //moving sensor
                                NotificationManager.sendNotification(getApplicationContext(), 1, NotificationManager.Channel.MESSAGE, "MOVEMENT SENSOR", "Movement is detected!\nPlease CHECK the app!!");
                            }
                        }else if(state==1){ //일반모드(움직임이 없으면 알림)
                            if (m.equals(0)) {  //moving sensor
                                NotificationManager.sendNotification(getApplicationContext(), 1, NotificationManager.Channel.MESSAGE, "MOVEMENT SENSOR", "No movement!\nPlease CHECK the app!!");
                            }
                        }

                        //화재 경보는 모드와 상관없이 언제나 작동함
                        if (f.equals(1)) {   //fire sensor
                            //화재 발생 = 1 , 화재 X = 0
                            NotificationManager.sendNotification(getApplicationContext(), 2, NotificationManager.Channel.MESSAGE, "FIRE detect SENSOR", "please CHECK the SENSOR-state!!");
                        }
                    }
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void introClick(View view){
        switch(view.getId()){
            case R.id.info:
                startActivity(new Intent(getApplicationContext(),explanationActivity.class));
                break;
            case R.id.intro_cctv:
                startActivity(new Intent(getApplicationContext(),cctvActivity.class));
                break;
            case R.id.callE:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)119")));
                break;
            case R.id.motionSetting:
                startActivity(new Intent(getApplicationContext(),settingsActivity.class));
                break;
            case R.id.sensor:
                startActivity(new Intent(getApplicationContext(), sensorActivity.class));
                break;

        }
    }
}
