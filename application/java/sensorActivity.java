package com.example.grad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class sensorActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    String temp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);

        final ImageView imageView=findViewById(R.id.state);

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

        new Thread() {
            @Override
            public void run() {
                h.post(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                //super.run();
                                bun.clear();

                                sensorState sensor = new sensorState();
                                arrayList = sensor.getSensor();

                                bun.putStringArrayList("sensor", arrayList);
                                Message msg = h.obtainMessage();
                                msg.setData(bun);
                                h.sendMessage(msg);

                                for (int i = 0; i <= 1; i++) {
                                    temp = arrayList.get(i);
                                    String[] data = temp.split(":");
                                    arrayList.set(i, data[1]);
                                }
                                Integer m = Integer.parseInt(arrayList.get(0));
                                Integer f = Integer.parseInt(arrayList.get(1));
                                RelativeLayout relativeLayout = findViewById(R.id.main);
                                if (f.equals(1)) {   //fire sensor
                                    imageView.setImageResource(R.drawable.firestate);
                                    relativeLayout.setBackgroundColor(Color.rgb(221, 141, 141));
                                    NotificationManager.sendNotification(getApplicationContext(), 2, NotificationManager.Channel.MESSAGE, "FIRE detect SENSOR", "please CHECK the SENSOR-state!!");
                                } else if (!(m.equals(0))) {
                                    imageView.setImageResource(R.drawable.nofirestate);
                                    relativeLayout.setBackgroundColor(Color.rgb(243, 205, 91));
                                }
                            }
                        }catch (IndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

    public void onclick(View view){
        Intent intent=null;
        switch(view.getId()){
            case R.id.callE:
                intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)119"));
                break;
            case R.id.back:
                intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
        }
        if(intent!=null){
            startActivity(intent);
        }
    }
}