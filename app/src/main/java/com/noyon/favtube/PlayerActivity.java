package com.noyon.favtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlayerActivity extends AppCompatActivity {

    WebView web;
 public static String VideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        web=findViewById(R.id.web);

     String url="https://www.youtube.com/embed/";

        WebSettings webset=web.getSettings();
        webset.setJavaScriptEnabled(true);

      // to loading any link into the app
        web.setWebViewClient(new WebViewClient());

        // videoid will change .
        web.loadUrl(url+VideoId);



    }

}