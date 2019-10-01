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
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.adapters.QuotationAdapter;
import com.p2p.models.ListUnit;
import com.p2p.models.Quotation;
import com.p2p.models.Unit;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.IconUtils;
import com.p2p.utils.URLUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationTabFragment extends Fragment {

    ArrayList<Quotation> quotations;
    QuotationAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog mProgressDialog;
    APIServices apiServices;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quotation_tab, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.quotation_refresh);
        ListView listViewQuotation = rootView.findViewById(R.id.list_quotations);
        mProgressDialog = new ProgressDialog(getContext());
        quotations = new ArrayList<>();
        adapter = new QuotationAdapter(getActivity(), R.layout.row_quotation_listview, quotations);
        listViewQuotation.setAdapter(adapter);

        apiServices = RetrofitClientServices.getAPIServices();

        retrieveData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                retrieveData();
            }
        });
        return rootView;
    }

    private void retrieveData() {
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.show();

        APIServices apiServices = RetrofitClientServices.getAPIServicesForOtherSource("https://min-api.cryptocompare.com/data/");

        Call<JsonObject> caller = apiServices.getListQuotation();
        quotations.clear();

        caller.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject body = response.body();
                    assert body != null;
                    Set<Map.Entry<String, JsonElement>> entrySet = body.entrySet();
                    for(Map.Entry<String,JsonElement> entry : entrySet){
                        String key = entry.getKey();
                        JsonObject value = (JsonObject) entry.getValue();
                        quotations.add(new Quotation(IconUtils.getCoinIconByName(key), key, value.get("USD").getAsDouble()));
                    }
//                    assert body != null;
//                    quotations.clear();
//                    Iterator<String> keys = body.keys();
//                    while (keys.hasNext()) {
//                        try {
//                            String key = keys.next();
//                            JSONObject value = (JSONObject) body.get(key);
//                            quotations.add(new Quotation(IconUtils.getCoinIconByName(key), key, (double) value.get("USD")));
//                        } catch (Exception e) {
//
//                        }
//                    }
                    getPPNQuotation();
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPPNQuotation() {
        Call<ListUnit> caller = apiServices.getAllUnits();
        caller.enqueue(new Callback<ListUnit>() {
            @Override
            public void onResponse(Call<ListUnit> call, Response<ListUnit> response) {
                if (response.isSuccessful()) {
                    ListUnit listUnit = response.body();
                    assert listUnit != null;
                    for(Unit unit : listUnit.getUnits()) {
                        quotations.add(0, new Quotation(IconUtils.getCoinIconByName("PPN"), unit.getName(), unit.getRate()));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        String err = response.errorBody().string();
                        if (err.equals("")) throw new IOException();
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Undefined error!!", Toast.LENGTH_SHORT).show();
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListUnit> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
