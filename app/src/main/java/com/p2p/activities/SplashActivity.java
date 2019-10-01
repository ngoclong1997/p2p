package com.p2p.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.models.User;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apiServices = RetrofitClientServices.getAPIServices();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                checkVersion();
            }
        });
    }

    private void checkVersion() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("platform", "android");
        Call<JsonObject> caller = apiServices.getAppVersion(jsonObject);
        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject1 = response.body();
                    try {
                        String version = jsonObject1.get("version").getAsString();
                        PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                        String versionApp = pInfo.versionName;
                        if (!versionApp.equals(version)) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
                            alertDialogBuilder.setTitle("Update required");
                            alertDialogBuilder.setMessage("Your app is not up to date. Please upgrade to the latest version. Thank you!");
                            alertDialogBuilder.setPositiveButton("Download now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "https://p2ppitpanex.com/download/";
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            getUser();
                        }
                    } catch (Exception e) {
                        Toast.makeText(SplashActivity.this, "Can not check for the version", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        Toast.makeText(SplashActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUser() {
        Call<User> caller = apiServices.getCurrentUser(APIUtils.getOAuthToken(this));
        caller.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    private void showUpdateDialog() {

    }
}

