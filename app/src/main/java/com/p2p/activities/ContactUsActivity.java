package com.p2p.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.p2p.R;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText contactUs;
    Button btnSend;
    APIServices apiServices;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ImageView btnBack = findViewById(R.id.btn_back);
        contactUs = findViewById(R.id.contact_us_message);
        btnSend = findViewById(R.id.contact_us_submit_button);
        progressDialog = new ProgressDialog(this);
        apiServices = RetrofitClientServices.getAPIServices();
        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.contact_us_submit_button:
                if (contactUs.getText().toString().equals("")) {
                    Toast.makeText(this, "Please describe your problem in text box!!", Toast.LENGTH_SHORT).show();
                    break;
                }
                Map<String, String> params = new HashMap<>();
                params.put("content", contactUs.getText().toString());
                progressDialog.setMessage("Sending...");
                progressDialog.show();

                sendEmail();
                break;
        }
    }

    private void sendEmail() {
        HashMap<String, String> params = new HashMap<>();
        params.put("content", contactUs.getText().toString());
        Call<String> caller = apiServices.sendSupportEmail(APIUtils.getOAuthToken(this), params);

        caller.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "An email has sent to administrator.\n We will reply you soon!!", Toast.LENGTH_LONG).show();
                    contactUs.setText("");
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContactUsActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
