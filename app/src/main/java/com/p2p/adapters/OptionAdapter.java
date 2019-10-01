package com.p2p.adapters;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2p.R;
import com.p2p.models.Coin;
import com.p2p.models.Options;

import java.util.ArrayList;

public class OptionAdapter extends ArrayAdapter<Options> {

    private int resourceLayout;

    private static class ViewHolder {
        ImageView iconView;
        TextView optionNameView;
    }

    public OptionAdapter(Context context, int resource, ArrayList<Options> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Options option = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            viewHolder.iconView = convertView.findViewById(R.id.option_icon);
            viewHolder.optionNameView = convertView.findViewById(R.id.option_name);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert option != null;
        viewHolder.iconView.setImageResource(option.getIcon());
        viewHolder.optionNameView.setText(option.getName());
        return result;
    }
}
