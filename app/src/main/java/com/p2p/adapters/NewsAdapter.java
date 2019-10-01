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
import com.p2p.models.Quotation;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceLayout;

    private static class ViewHolder {
        ImageView thumbnailImageView;
        TextView titleView;
        TextView descriptionView;
        TextView tagView;
    }

    public NewsAdapter(Context context, int resource, ArrayList<News> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            viewHolder.thumbnailImageView = convertView.findViewById(R.id.news_thumbnail);
            viewHolder.titleView = convertView.findViewById(R.id.news_title);
            viewHolder.descriptionView = convertView.findViewById(R.id.news_description);
            viewHolder.tagView = convertView.findViewById(R.id.news_tags);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert news != null;
//        viewHolder.thumbnailImageView.setImageResource(R.drawable.ic_smiling_avt);
        viewHolder.titleView.setText(news.getTitle());
        viewHolder.descriptionView.setText(news.getDescription());
        viewHolder.tagView.setText(news.getTags());
        Picasso.get().load(news.getImageURL()).into(viewHolder.thumbnailImageView);
        return result;
    }
}
