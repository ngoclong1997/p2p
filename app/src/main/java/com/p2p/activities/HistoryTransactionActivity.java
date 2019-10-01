package com.p2p.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.p2p.R;
import com.p2p.adapters.HistoryTransferAdapter;
import com.p2p.models.HistoryTransaction;
import com.p2p.models.TransferHistory;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.APIUtils;
import com.p2p.utils.ConversionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    ListView transferListView;
    ArrayList<TransferHistory> transferHistories;
    SwipeRefreshLayout swipeRefreshLayout;
    HistoryTransferAdapter adapter;
    ProgressDialog mProgressDialog;
    APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaction);
        ImageView btnBack = findViewById(R.id.btn_back);
        transferListView = findViewById(R.id.list_transfer);
        transferHistories = new ArrayList<>();
        apiServices = RetrofitClientServices.getAPIServices();

        swipeRefreshLayout = findViewById(R.id.transfer_body);

        mProgressDialog = new ProgressDialog(getApplicationContext());
        adapter = new HistoryTransferAdapter(getApplicationContext(), R.layout.row_history_transfer_listview, transferHistories);
        transferListView.setAdapter(adapter);

        retrieveData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                retrieveData();
            }
        });
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

        }
    }

    private void retrieveData() {
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        Call<List<HistoryTransaction>> caller = apiServices.getHistoricalTransaction(APIUtils.getOAuthToken(this));

        caller.enqueue(new Callback<List<HistoryTransaction>>() {
            @Override
            public void onResponse(Call<List<HistoryTransaction>> call, Response<List<HistoryTransaction>> response) {
                if (response.isSuccessful()) {
                    List<HistoryTransaction> historyTransactions = response.body();
                    for (HistoryTransaction historyTransaction : historyTransactions) {
                        String toWallet = historyTransaction.getToWallet();
                        String createTs = historyTransaction.getCreateTs();
                        double PPNAmount = historyTransaction.getPPNAmount();
                        double BTCAmount = historyTransaction.getBTCAmount();
                        double ETHAmount = historyTransaction.getETHAmount();
                        double BATAmount = historyTransaction.getBATAmount();
                        double ETCAmount = historyTransaction.getETCAmount();
                        double USDCAmount = historyTransaction.getUSDCAmount();
                        double ZRXAmount = historyTransaction.getZRXAmount();
                        double LTCAmount = historyTransaction.getLTCAmount();

                        String description = "You have transferred to \"" + toWallet + "\" ";
                        if (PPNAmount > 0) {
                            description += ConversionUtils.doubleToString(PPNAmount) + " PPN ";
                        }
                        if (BTCAmount > 0) {
                            description += ConversionUtils.doubleToString(BTCAmount) + " BTC ";
                        }
                        if (ETHAmount > 0) {
                            description += ConversionUtils.doubleToString(ETHAmount) + " ETH ";
                        }
                        if (BATAmount > 0) {
                            description += ConversionUtils.doubleToString(BATAmount) + " BAT ";
                        }
                        if (ETCAmount > 0) {
                            description += ConversionUtils.doubleToString(ETCAmount) + " ETC ";
                        }
                        if (USDCAmount > 0) {
                            description += ConversionUtils.doubleToString(USDCAmount) + " USDC ";
                        }
                        if (ZRXAmount > 0) {
                            description += ConversionUtils.doubleToString(ZRXAmount) + " ZRX ";
                        }
                        if (LTCAmount > 0) {
                            description += ConversionUtils.doubleToString(LTCAmount) + " LTC ";
                        }
                        transferHistories.add(new TransferHistory(description, createTs));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<HistoryTransaction>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(HistoryTransactionActivity.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
