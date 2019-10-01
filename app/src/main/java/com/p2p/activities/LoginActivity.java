package com.p2p.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.models.User;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.AppUtils;
import com.p2p.utils.PrefsUtils;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout relativeLayout;
    Button registerButton;
    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    TextView forgotPasswordTextView;
    ProgressDialog mProgressDialog;
    APIServices apiServices;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialog = new ProgressDialog(this);
        relativeLayout = findViewById(R.id.login_wrapper);
        registerButton = findViewById(R.id.login_button_register);
        loginButton = findViewById(R.id.login_button_login);
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        forgotPasswordTextView = findViewById(R.id.forgot_password);
        apiServices = RetrofitClientServices.getRetrofitInstance().create(APIServices.class);

        relativeLayout.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.login_button_register:
                i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.login_wrapper:
                AppUtils.hideKeyboard(this, v.getRootView());
                break;
            case R.id.forgot_password:
                i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.login_button_login:
                try {
                    authenticate(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void authenticate(String login, String password) throws Exception {
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.show();
        getOAuthToken(login, password);
    }

    void getOAuthToken(final String login, final String password) {
        Call<JsonObject> caller = apiServices.getOAuthToken("password", login, password);
        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    String token = jsonObject.get("access_token").getAsString();
                    PrefsUtils.setPreferenceValue(getApplicationContext(), PrefsUtils.ACCESS_TOKEN, token);
                    getCurrentUser("Bearer " + token);
                } else {
                    Toast.makeText(LoginActivity.this, "User name or password incorrect!!", Toast.LENGTH_SHORT).show();
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        String err = "Username or password incorrect.";
//                        try {
//                            Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                        } catch (IOException e1) {
//                            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Network error!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCurrentUser(String token) {
        Call<User> caller = apiServices.getCurrentUser(token);
        caller.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    PrefsUtils.setPreferenceValue(getApplicationContext(), PrefsUtils.CURRENT_USER, user);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
