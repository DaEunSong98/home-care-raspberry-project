package com.example.grad;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class sensorState {

    //메인화면에서 웹에서 필요한 tag와 관련된 내용만 가져오기 위해 사용하는 class
    //웹 crawling

    ArrayList<String> arrayList;
    Integer state;

    public sensorState() {
        arrayList = new ArrayList<String>();
        state=1;
    }

    public ArrayList<String> getSensor() {
        try {
            //아두이노가 센서값의 변화를 저장하는 페이지
            //아두이노는 스스로 custom한 html에 값을 저장함(아두이노 코드에 하드코딩해놓음)
            Document doc = Jsoup.connect("http://192.168.0.105:5500/sensor.html").get();
            Elements contents=doc.select("p#sensor1");
            arrayList.add(contents.text());
            contents=doc.select("p#sensor2");
            arrayList.add(contents.text());
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("getSensor() 에러: ",e.getMessage());
        }
        return arrayList;
    }

    public Integer getState(){
        state=((settingsActivity)settingsActivity.context).state;
        return state;
    }
}

