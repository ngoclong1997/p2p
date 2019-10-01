package com.p2p.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.activities.ChangeAvatarActivity;
import com.p2p.activities.ProfitSendingActivity;
import com.p2p.activities.ReceiveQRCodeActivity;
import com.p2p.activities.SystemSendingActivity;
import com.p2p.activities.TotalSendingActivity;
import com.p2p.adapters.AssetAdapter;
import com.p2p.models.Asset;
import com.p2p.models.User;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.ConversionUtils;
import com.p2p.utils.IconUtils;
import com.p2p.utils.PrefsUtils;
import com.p2p.utils.URLUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment implements View.OnClickListener {

    ListView listViewAssets;
    ArrayList<Asset> assets;
    TextView usernameView;
    TextView levelView;
    TextView systemAmountView;
    AssetAdapter adapter;
    TextView totalAmountView;
    TextView profitAmountView;
    User user;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    APIServices apiServices;
    double btcRate = 0.0, ethRate = 0.0, batRate = 0.0, ltcRate = 0.0, usdcRate = 0.0, zrxRate = 0.0, etcRate = 0.0;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.list_assets_refresh);
        listViewAssets = rootView.findViewById(R.id.list_assets);
        RelativeLayout ppnSystemView = rootView.findViewById(R.id.system_box);
        RelativeLayout ppnProfitView = rootView.findViewById(R.id.profit_box);
        RelativeLayout totalView = rootView.findViewById(R.id.total_box);
        progressDialog = new ProgressDialog(getActivity());
        Button btnManage = rootView.findViewById(R.id.btn_info_manage);
        usernameView = rootView.findViewById(R.id.user_username);
        levelView = rootView.findViewById(R.id.user_level);
        user = PrefsUtils.getPreferenceValue(getActivity(), PrefsUtils.CURRENT_USER, new User());
        usernameView.setText(user.getLogin());
        systemAmountView = rootView.findViewById(R.id.system_ammount);
        totalAmountView = rootView.findViewById(R.id.ppn_total_amount);
        profitAmountView = rootView.findViewById(R.id.ppn_profit_amount);
        apiServices = RetrofitClientServices.getAPIServices();
        assets = new ArrayList<>();

        adapter = new AssetAdapter(getActivity(), R.layout.row_assets_listview, assets);
        listViewAssets.setAdapter(adapter);

        retrieveData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                retrieveData();
            }
        });

        ppnSystemView.setOnClickListener(this);
        ppnProfitView.setOnClickListener(this);
        totalView.setOnClickListener(this);
        btnManage.setOnClickListener(this);

        listViewAssets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Asset asset = (Asset) listViewAssets.getItemAtPosition(position);
                Intent i = new Intent(view.getRootView().getContext(), ReceiveQRCodeActivity.class);
                i.putExtra("icon", asset.getIcon());
                i.putExtra("code", asset.getCode());
                startActivity(i);
            }
        });


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String dateString = "15-06-2019 23:59:59";

            long day15 = sdf.parse(dateString).getTime();
            long now = new Date().getTime();
            if (now <= day15) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Promotion info")
                        .setMessage("Promotion will be ended at 15/06/2019 23:59:59")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return rootView;

    }

    private void getUserData() {

        Call<User> caller = apiServices.getCurrentUser(APIUtils.getOAuthToken(getContext()));

        caller.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user1 = response.body();
                    if (user1 != null) {
                        PrefsUtils.setPreferenceValue(getActivity(), PrefsUtils.CURRENT_USER, user1);
                    }
                    User user = PrefsUtils.getPreferenceValue(getActivity(), PrefsUtils.CURRENT_USER, new User());

                    assets.clear();
                    assets.add(new Asset(IconUtils.getCoinIconByName("BTC"), "BTC", user.getBtcCode(), user.getBtcInvestment(), user.getBtcInvestment() * btcRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("ETH"), "ETH", user.getEthCode(), user.getEthInvestment(), user.getEthInvestment() * ethRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("BAT"), "BAT", user.getBatCode(), user.getBatInvestment(), user.getBatInvestment() * batRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("LTC"), "LTC", user.getLtcCode(), user.getLtcInvestment(), user.getLtcInvestment() * ltcRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("USDC"), "USDC", user.getUsdcCode(), user.getUsdcInvestment(), user.getUsdcInvestment() * usdcRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("ZRX"), "ZRX", user.getZrxCode(), user.getZrxInvestment(), user.getZrxInvestment() * zrxRate));
                    assets.add(new Asset(IconUtils.getCoinIconByName("ETC"), "ETC", user.getEtcCode(), user.getEtcInvestment(), user.getEtcInvestment() * etcRate));
                    systemAmountView.setText(ConversionUtils.doubleToString(user.getSystem()) + " PPN");
                    double totalInvestment = user.getBtcInvestment() * btcRate + user.getEthInvestment() * ethRate + user.getBatInvestment() * batRate + user.getLtcInvestment() * ltcRate + user.getUsdcInvestment() * usdcRate + user.getZrxInvestment() * zrxRate + user.getEtcInvestment() * etcRate;
                    totalAmountView.setText("$" + ConversionUtils.doubleToString(totalInvestment));
                    profitAmountView.setText(ConversionUtils.doubleToString(user.getProfit()) + " PPN");
                    int level;
                    if (totalInvestment >= 10000) {
                        level = 7;
                    } else if (totalInvestment >= 5000) {
                        level = 6;
                    } else if (totalInvestment >= 2000) {
                        level = 5;
                    } else if (totalInvestment >= 1000) {
                        level = 4;
                    } else if (totalInvestment >= 500) {
                        level = 3;
                    } else if (totalInvestment >= 200) {
                        level = 2;
                    } else if (totalInvestment >= 100) {
                        level = 1;
                    } else {
                        level = 0;
                    }
                    levelView.setText("Level " + ConversionUtils.intToString(level));
                    adapter.notifyDataSetChanged();
                } else {
                    String err = "Undefined error!!";
                    try {
                        err = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void retrieveData() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        APIServices apiServices = RetrofitClientServices.getAPIServicesForOtherSource("https://min-api.cryptocompare.com/data/");

        Call<JsonObject> caller = apiServices.getListQuotation();

        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response1) {
                if (response1.isSuccessful()) {

                    JsonObject response = response1.body();

                    try {
                        assert response != null;
                        btcRate = response.get("BTC").getAsJsonObject().get("USD").getAsDouble();
                        ethRate = response.get("ETH").getAsJsonObject().get("USD").getAsDouble();
                        batRate = response.get("BAT").getAsJsonObject().get("USD").getAsDouble();
                        ltcRate = response.get("LTC").getAsJsonObject().get("USD").getAsDouble();
                        usdcRate = response.get("USDC").getAsJsonObject().get("USD").getAsDouble();
                        zrxRate = response.get("ZRX").getAsJsonObject().get("USD").getAsDouble();
                        etcRate = response.get("ETC").getAsJsonObject().get("USD").getAsDouble();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Undefined error!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }

                    getUserData();

                } else {
                    String err = "Undefined error!!";
                    try {
                        err = response1.errorBody().string();
                    } catch (IOException e) {

                    }
                    Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.system_box:
                i = new Intent(getActivity(), SystemSendingActivity.class);
                startActivity(i);
                break;
            case R.id.profit_box:
                i = new Intent(getActivity(), ProfitSendingActivity.class);
                startActivity(i);
                break;
            case R.id.total_box:
                i = new Intent(getActivity(), TotalSendingActivity.class);
                startActivity(i);
                break;
            case R.id.btn_info_manage:
                i = new Intent(getActivity(), ChangeAvatarActivity.class);
                startActivity(i);
                break;
        }
    }
}
