package com.example.grad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class pushActivity extends AppCompatActivity {

    //화재나 움직임 관련 알림이 온 경우 연결하는 페이지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push);

        //집안의 모습을 보여주는 페이지로 이동
        WebView webView =findViewById(R.id.cctvView);
        webView.setWebViewClient(new WebViewClient());
        webView.setInitialScale(10);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //카메라가 실시간으로 웹으로 화면을 보내고 있어서 웹뷰를 통해 카메라 화면을 비추도록함
        //직접 데이터를 가져오지 않아서 앱이 가벼워짐
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
