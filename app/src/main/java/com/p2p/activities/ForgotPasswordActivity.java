package com.p2p.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.p2p.R;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.AppUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    CountryCodePicker countryCodePicker;
    EditText userNameView;
    EditText mobilePhoneView;
    EditText emailView;
    EditText validationCodeView;
    APIServices apiServices;
    TextView btnGetValidationCode;
    EditText passwordView;
    EditText confirmPasswordView;
    Button confirmReset;
    RelativeLayout mainView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ImageView btnBack = findViewById(R.id.btn_back);
        countryCodePicker = findViewById(R.id.forgot_cpp);
        userNameView = findViewById(R.id.forgot_username);
        mobilePhoneView = findViewById(R.id.forgot_mobile_phone);
        emailView = findViewById(R.id.forgot_email);
        validationCodeView = findViewById(R.id.forgot_validation_code);
        btnGetValidationCode = findViewById(R.id.forgot_get_validation_code);
        passwordView = findViewById(R.id.forgot_password);
        confirmPasswordView = findViewById(R.id.forgot_password_confirm);
        confirmReset = findViewById(R.id.forgot_button_confirm);
        progressDialog = new ProgressDialog(this);
        mainView = findViewById(R.id.forgot_input_box);
        apiServices = RetrofitClientServices.getAPIServices();


        btnBack.setOnClickListener(this);
        mainView.setOnClickListener(this);
        btnGetValidationCode.setOnClickListener(this);
        confirmReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.forgot_button_confirm:
                forgotPasswordHandle();
                break;
            case R.id.forgot_get_validation_code:
                if (emailView.getText().toString().equals("")) {
                    Toast.makeText(this, "Email can not be empty!!", Toast.LENGTH_LONG).show();
                    break;
                }
                sendValidationCodeHandler();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.forgot_input_box:
                AppUtils.hideKeyboard(this, v);
                break;
        }
    }

    private void sendValidationCodeHandler() {
        progressDialog.setMessage("Getting validation code...");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", emailView.getText().toString());

        Call<Void> caller = apiServices.sendValidationEmail(jsonObject);

        caller.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Validation code has been sent to your email!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Send validation code failed!!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void forgotPasswordHandle() {
        progressDialog.setMessage("Getting validation code...");
        progressDialog.show();

        progressDialog.setMessage("Registering...");
        progressDialog.show();
        JsonObject obj = new JsonObject();
        JsonObject child = new JsonObject();

        child.addProperty("login", userNameView.getText().toString());
        child.addProperty("password", passwordView.getText().toString());
        child.addProperty("full_name", userNameView.getText().toString());
        child.addProperty("country", countryCodePicker.getSelectedCountryName());
        child.addProperty("phone_number", mobilePhoneView.getText().toString());
        child.addProperty("email", emailView.getText().toString());
        child.addProperty("validation_code", validationCodeView.getText().toString());
        obj.add("user", child);

        Call<JsonObject> caller = apiServices.resetPassword(obj);
        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Reset password success. Please login with your new password!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        Toast.makeText(ForgotPasswordActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
