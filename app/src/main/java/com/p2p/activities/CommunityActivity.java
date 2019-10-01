package com.p2p.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.R;
import com.p2p.models.Community;
import com.p2p.models.User;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.ConversionUtils;
import com.p2p.utils.PrefsUtils;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityActivity extends AppCompatActivity {

    APIServices apiServices;
    TextView teamMemberView;
    TextView teamValueView;
    ProgressDialog progressDialog;
    TextView teamProfitView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ImageView btnBack = findViewById(R.id.btn_back);
        apiServices = RetrofitClientServices.getAPIServices();
        progressDialog = new ProgressDialog(this);
        teamMemberView = findViewById(R.id.team_member_value);
        teamValueView = findViewById(R.id.team_value_value);
        teamProfitView = findViewById(R.id.team_profit_value);
        user = PrefsUtils.getPreferenceValue(this, PrefsUtils.CURRENT_USER, new User());




        getCommunityInformation();
        btnBack.setOnClickListener(goBack);
    }

    private void getCommunityInformation() {
        progressDialog.setMessage("Retrieving community info...");
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", user.getId());
        Call<Community> caller = apiServices.getCommunityInfo(APIUtils.getOAuthToken(this), params);
        caller.enqueue(new Callback<Community>() {
            @Override
            public void onResponse(Call<Community> call, Response<Community> response) {
                if (response.isSuccessful()) {
                    Community community = response.body();
                    assert community != null;
                    teamMemberView.setText(ConversionUtils.intToString(community.getTeamMembers()));
                    teamValueView.setText(ConversionUtils.doubleToString(community.getTotalInvestment()));
                    teamProfitView.setText(ConversionUtils.doubleToString(community.getTotalProfit()));
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();;
            }

            @Override
            public void onFailure(Call<Community> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CommunityActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
