package com.p2p.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2p.R;
import com.p2p.models.News;
import com.p2p.models.TransferHistory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryTransferAdapter extends ArrayAdapter<TransferHistory> {

    private int resourceLayout;

    private static class ViewHolder {
        TextView transferDescription;
        TextView transferDate;
    }

    public HistoryTransferAdapter(Context context, int resource, ArrayList<TransferHistory> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TransferHistory transferHistory = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);
            viewHolder.transferDate = convertView.findViewById(R.id.transfer_date);
            viewHolder.transferDescription = convertView.findViewById(R.id.transfer_info);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert transferHistory != null;
        viewHolder.transferDate.setText(transferHistory.getDate());
        viewHolder.transferDescription.setText(transferHistory.getDescription());
        return result;
    }
}
