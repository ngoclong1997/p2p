package com.p2p.fragments;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.p2p.R;
import com.p2p.activities.NewsActivity;
import com.p2p.adapters.NewsAdapter;
import com.p2p.models.ListNews;
import com.p2p.models.News;
import com.p2p.services.APIServices;
import com.p2p.services.RetrofitClientServices;
import com.p2p.utils.URLUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsTabFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<News> news;
    NewsAdapter adapter;
    ProgressDialog mProgressDialog;
    APIServices apiServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_news_tab, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.quotation_news_refresh);
        final ListView listViewQuotation = rootView.findViewById(R.id.list_news);
        mProgressDialog = new ProgressDialog(getActivity());
        news = new ArrayList<>();
        adapter = new NewsAdapter(getActivity(), R.layout.row_news_listview, news);
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

        listViewQuotation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), NewsActivity.class);
                i.putExtra("url", ((News)listViewQuotation.getItemAtPosition(position)).getUrl());
                startActivity(i);
            }
        });

        return rootView;
    }

    private void retrieveData() {
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        APIServices apiServices = RetrofitClientServices.getAPIServicesForOtherSource("https://min-api.cryptocompare.com/data/");

        Call<ListNews> caller = apiServices.getListNews();

        caller.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(Call<ListNews> call, Response<ListNews> response) {
                if (response.isSuccessful()) {
                    ListNews listNews = response.body();
                    assert listNews != null;
                    news.clear();
                    news.addAll(listNews.getNews());
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListNews> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Network error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
