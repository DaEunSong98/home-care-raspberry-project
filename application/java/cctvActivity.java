package com.example.grad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


public class cctvActivity extends AppCompatActivity {

    //카메라 화면과 연결해주는 페이지

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cctv);

        //카메라는 웹으로 실시간으로 정보를 보내서 웹뷰를 통해 영상띄워줌
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.setInitialScale(100);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //라즈베리파이가 영상을 보내주는 페이지
        webView.loadUrl("http://192.168.0.105:5000/?action=stream");
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
