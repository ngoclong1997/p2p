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
import com.p2p.models.Quotation;
import com.p2p.utils.ConversionUtils;

import java.util.ArrayList;

public class QuotationAdapter extends ArrayAdapter<Quotation> {

    private int resourceLayout;

    private static class ViewHolder {
        ImageView iconView;
        TextView nameView;
        TextView valueView;
    }

    public QuotationAdapter(Context context, int resource, ArrayList<Quotation> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Quotation quotation = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            viewHolder.nameView = convertView.findViewById(R.id.quotation_coin_name);
            viewHolder.valueView = convertView.findViewById(R.id.quotation_coin_value);
            viewHolder.iconView = convertView.findViewById(R.id.quotation_coin_icon);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert quotation != null;
        viewHolder.iconView.setImageResource(quotation.getIcon());
        viewHolder.nameView.setText(quotation.getName());
        String value = ConversionUtils.doubleToString(quotation.getValue()) + " USD";
        viewHolder.valueView.setText(value);
        return result;
    }
}
