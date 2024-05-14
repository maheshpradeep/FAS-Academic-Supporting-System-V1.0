package com.example.fas;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class reslinks extends AppCompatActivity {

    WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Toast.makeText(reslinks.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
        webView.loadUrl("https://youtube.com/playlist?list=PLFYUVpHAwCDWz8wHRC1_yMFvIddPnJ5dZ&si=i4vsMvtjTtL6mJot");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();

        }
    }
}
