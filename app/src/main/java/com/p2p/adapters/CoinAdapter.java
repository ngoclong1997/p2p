package com.p2p.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2p.R;
import com.p2p.models.Coin;
import com.p2p.utils.ConversionUtils;

import java.util.ArrayList;

public class CoinAdapter extends ArrayAdapter<Coin> {

    private int resourceLayout;

    private static class ViewHolder {
        ImageView iconView;
        TextView coinNameView;
        TextView coinAmountView;
        TextView coinChangeRateView;
    }

    public CoinAdapter(Context context, int resource, ArrayList<Coin> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Coin coin = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            viewHolder.coinNameView = convertView.findViewById(R.id.coin_name);
            viewHolder.coinAmountView = convertView.findViewById(R.id.coin_amount);
            viewHolder.coinChangeRateView = convertView.findViewById(R.id.coin_change_rate);
            viewHolder.iconView = convertView.findViewById(R.id.coin_icon);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert coin != null;
        viewHolder.iconView.setImageResource(coin.getIcon());
        viewHolder.coinNameView.setText(coin.getName());
        String changeRate = ConversionUtils.doubleToString(coin.getChangeRate(), "#,##0.######")+ "%";
        viewHolder.coinChangeRateView.setText(changeRate);
        if (coin.getChangeRate() < 0) {
            viewHolder.coinChangeRateView.setTextColor(Color.RED);
        } else {
            viewHolder.coinChangeRateView.setTextColor(Color.GREEN);
        }
        String amount = "$" + ConversionUtils.doubleToString(coin.getAmount());
        viewHolder.coinAmountView.setText(amount);
        return result;
    }
}
