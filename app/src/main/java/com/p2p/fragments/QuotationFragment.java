package com.p2p.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p2p.R;
import com.p2p.adapters.ViewPagerAdapter;

public class QuotationFragment extends Fragment implements View.OnClickListener {
    ViewPager viewPager;
    RelativeLayout newsTab;
    RelativeLayout quotationTab;
    TextView newsTitle;
    TextView quotationTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_quotation, container, false);
        viewPager = rootView.findViewById(R.id.quotation_view_pager);
        newsTab = rootView.findViewById(R.id.quotation_news);
        quotationTab = rootView.findViewById(R.id.quotation);
        newsTitle = rootView.findViewById(R.id.news_text);
        quotationTitle = rootView.findViewById(R.id.quotation_text);

        newsTab.setOnClickListener(this);
        quotationTab.setOnClickListener(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quotation_news:
                if (viewPager.getCurrentItem() != 1) {
                    viewPager.setCurrentItem(1, true);
                    newsTitle.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    quotationTitle.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                break;
            case R.id.quotation:
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(0, true);
                    newsTitle.setTextColor(getResources().getColor(R.color.colorBlack));
                    quotationTitle.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;
        }
    }
}
