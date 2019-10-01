package com.p2p.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.p2p.R;
import com.p2p.models.ChangePasswordObject;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.AppUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout bodyView;
    EditText currentPasswordView;
    EditText newPasswordView;
    EditText confirmNewPasswordView;
    Button btnChangePassword;
    APIServices apiServices;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ImageView btnBack = findViewById(R.id.btn_back);
        bodyView = findViewById(R.id.change_password_body);
        currentPasswordView = findViewById(R.id.current_pass);
        newPasswordView = findViewById(R.id.new_pass);
        confirmNewPasswordView = findViewById(R.id.confirm_new_pass);
        btnChangePassword = findViewById(R.id.change_password_btn);
        progressDialog = new ProgressDialog(this);
        apiServices = RetrofitClientServices.getAPIServices();
        btnChangePassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        bodyView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.change_password_body:
                AppUtils.hideKeyboard(this, v);
                break;
            case R.id.change_password_btn:
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String currentPass = currentPasswordView.getText().toString();
                String newPass = newPasswordView.getText().toString();
                String confirm = confirmNewPasswordView.getText().toString();

                if (!newPass.equals(confirm)) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Password field and confirm password field are not match!!", Toast.LENGTH_LONG).show();
                } else {
                    changePassword(APIUtils.getOAuthToken(this), currentPass, newPass);
                }
                break;
        }
    }

    private void changePassword(String token, String currentPassword, String newPassword) {
        ChangePasswordObject changePasswordObject = new ChangePasswordObject();
        changePasswordObject.setCurrentPassword(currentPassword);
        changePasswordObject.setNewPassword(newPassword);
        Call<String> caller = apiServices.changePassword(token, changePasswordObject);
        caller.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String value = response.body();
                    Toast.makeText(ChangePasswordActivity.this, "Change password successfully!!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(ChangePasswordActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                currentPasswordView.setText("");
                newPasswordView.setText("");
                confirmNewPasswordView.setText("");
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChangePasswordActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
