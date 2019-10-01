package com.p2p.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2p.R;
import com.p2p.models.Asset;
import com.p2p.utils.ConversionUtils;

import java.util.ArrayList;

public class AssetAdapter extends ArrayAdapter<Asset> {

    private int resourceLayout;

    private static class ViewHolder {
        ImageView iconView;
        TextView assetNameView;
        TextView assetCodeView;
        TextView assetAmount;
        TextView assetAmountInDollar;
//        ImageView copyButton;
    }

    public AssetAdapter(Context context, int resource, ArrayList<Asset> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Asset asset = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            viewHolder.assetAmount = convertView.findViewById(R.id.asset_amount);
            viewHolder.assetAmountInDollar = convertView.findViewById(R.id.asset_amount_in_usd);
            viewHolder.assetNameView = convertView.findViewById(R.id.asset_name);
            viewHolder.assetCodeView = convertView.findViewById(R.id.asset_code);
//            viewHolder.copyButton = convertView.findViewById(R.id.btn_copy);
            viewHolder.iconView = convertView.findViewById(R.id.asset_icon);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert asset != null;
        viewHolder.iconView.setImageResource(asset.getIcon());
        viewHolder.assetNameView.setText(asset.getName());
        viewHolder.assetCodeView.setText(asset.getCode());
        String amount = ConversionUtils.doubleToString(asset.getAmount()) + " " + asset.getName();
        viewHolder.assetAmount.setText(amount);
        String amountInDollar = "$" + ConversionUtils.doubleToString(asset.getAmountInDollar());
        viewHolder.assetAmountInDollar.setText(amountInDollar);
//        viewHolder.copyButton.setOnClickListener(this);
//        viewHolder.copyButton.setTag(position);

        return result;
    }
}
