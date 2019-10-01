package com.p2p.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.R;
import com.p2p.adapters.CoinAdapter;
import com.p2p.models.Coin;
import com.p2p.models.Javis;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.ConversionUtils;
import com.p2p.utils.IconUtils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JavisFragment extends Fragment {

    CoinAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<Coin> coins;
    SwipeRefreshLayout swipeRefreshLayout;
    APIServices apiServices;
    TextView javisTotalAmountView;
    TextView totalInvestmentAmountView;
    TextView dailyProfitAmountView;
    TextView totalMemberView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_javis, container, false);
        ListView listViewCoins = rootView.findViewById(R.id.list_coins);
        swipeRefreshLayout = rootView.findViewById(R.id.javis_refresh);
        coins = new ArrayList<>();
        adapter = new CoinAdapter(getActivity(), R.layout.row_coins_listview, coins);
        progressDialog = new ProgressDialog(getActivity());
        listViewCoins.setAdapter(adapter);

        totalMemberView = rootView.findViewById(R.id.javis_total_member);
        dailyProfitAmountView = rootView.findViewById(R.id.daily_profit_amount);
        totalInvestmentAmountView = rootView.findViewById(R.id.total_investment_amount);
        javisTotalAmountView = rootView.findViewById(R.id.javis_total_amount);

        apiServices = RetrofitClientServices.getAPIServices();

        retrieveData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.show();
                retrieveData();
            }
        });

        return rootView;
    }

    private void retrieveData() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<Javis> caller = apiServices.getJavis();
        caller.enqueue(new Callback<Javis>() {
            @Override
            public void onResponse(Call<Javis> call, Response<Javis> response) {
                if (response.isSuccessful()) {
                    Javis javis = response.body();
                    coins.clear();
                    assert javis != null;
                    for (Coin coin : javis.getCoins()) {
                        coin.setIcon(IconUtils.getCoinIconByName(coin.getName()));
                        coins.add(coin);
                    }
                    totalMemberView.setText("Total member: " + ConversionUtils.intToString(javis.getTotalMemebers()));
                    totalInvestmentAmountView.setText("$"+ConversionUtils.doubleToString(javis.getTotalInvestment()));
                    dailyProfitAmountView.setText(ConversionUtils.doubleToString(javis.getTotalProfit()) + " PPN");
                    javisTotalAmountView.setText(ConversionUtils.doubleToString(javis.getTotalProfit() + javis.getTotalInvestment() + 20000.0) + " PPN");
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Javis> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network error!!", Toast.LENGTH_LONG).show();
            }
        });
    }





}
