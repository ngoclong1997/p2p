package com.p2p.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.p2p.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout changePasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageView btnBack = findViewById(R.id.btn_back);

        changePasswordView = findViewById(R.id.acc_settings_btn);

        changePasswordView.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.acc_settings_btn:
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(i);
                break;
        }
    }
}
