package com.p2p.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.p2p.R;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout userAgreementView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageView btnBack = findViewById(R.id.btn_back);

        userAgreementView = findViewById(R.id.abt_us_user_agreement);
        btnBack.setOnClickListener(this);
        userAgreementView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.abt_us_user_agreement:
                Intent i = new Intent(getApplicationContext(), UserAgreementActivity.class);
                startActivity(i);
                break;
        }
    }
}
