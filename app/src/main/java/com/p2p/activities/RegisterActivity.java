package com.p2p.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hbb20.CountryCodePicker;
import com.p2p.R;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.AppUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout inputBox;
    EditText invitationCode;
    ImageView scanQR;
    EditText userNameView;
    EditText mobilePhoneView;
    EditText emailView;
    EditText validationCodeView;
    EditText passwordView;
    EditText confirmPasswordView;
    TextView btnGetValidationCode;
    Button registerButton;
    APIServices apiServices;
    CountryCodePicker countryCodePicker;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView btnBack = findViewById(R.id.btn_back);
        inputBox = findViewById(R.id.register_input_box);
        scanQR = findViewById(R.id.register_qr_scanner);
        invitationCode = findViewById(R.id.register_invitation_code);
        btnGetValidationCode = findViewById(R.id.register_get_validation_code);
        confirmPasswordView = findViewById(R.id.register_password_confirm);
        passwordView = findViewById(R.id.register_password);
        validationCodeView = findViewById(R.id.register_validation_code);
        emailView = findViewById(R.id.register_email);
        mobilePhoneView = findViewById(R.id.register_mobile_phone);
        userNameView = findViewById(R.id.register_username);
        registerButton = findViewById(R.id.register_button_register);
        countryCodePicker = findViewById(R.id.register_cpp);
        apiServices = RetrofitClientServices.getAPIServices();
        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(this);
        btnGetValidationCode.setOnClickListener(this);
        scanQR.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        inputBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button_register:

                if (confirmPasswordView.getText().toString().equals("") || passwordView.getText().toString().equals("") || validationCodeView.getText().toString().equals("") || emailView.getText().toString().equals("")
                        || mobilePhoneView.getText().toString().equals("") || userNameView.getText().toString().equals("")) {
                    Toast.makeText(this, "Please fill out all the fields!!", Toast.LENGTH_LONG).show();
                    break;
                }
                if (!passwordView.getText().toString().equals(confirmPasswordView.getText().toString())) {
                    Toast.makeText(this, "Password and confirm password don't match!!", Toast.LENGTH_SHORT).show();
                    break;
                }
                registerHandler();
                break;
            case R.id.register_get_validation_code:
                if (emailView.getText().toString().equals("")) {
                    Toast.makeText(this, "Please enter email first", Toast.LENGTH_SHORT).show();
                    break;
                }
                sendValidationCodeHandler();
                break;
            case R.id.register_input_box:
                AppUtils.hideKeyboard(this, v);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.register_qr_scanner:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan for invitation code");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
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
                    Toast.makeText(RegisterActivity.this, "Validation code has been sent to your email!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Send validation code failed!!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerHandler() {

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
        child.addProperty("invitation_code", invitationCode.getText().toString());
        obj.add("user", child);

        Call<String> caller = apiServices.register(obj);
        caller.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register Success. Please login!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            } else {
                invitationCode.setText(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
