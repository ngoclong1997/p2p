package com.p2p.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.p2p.R;

import java.util.Objects;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    WebView newsWebView;
    ProgressBar progressBar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ImageView btnBack = findViewById(R.id.btn_back);
        newsWebView = findViewById(R.id.views_web_view);
        progressBar = findViewById(R.id.progress_bar);
        url = Objects.requireNonNull(getIntent().getExtras()).getString("url");
        newsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        btnBack.setOnClickListener(this);
        newsWebView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
