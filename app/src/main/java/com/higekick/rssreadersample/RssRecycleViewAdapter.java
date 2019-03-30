package com.higekick.rssreadersample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RssRecycleViewAdapter extends RecyclerView.Adapter<RssRecycleViewAdapter.RssItemViewHolder> {
    private List<RssReaderManager.RssItem> list;

    public void setList(List<RssReaderManager.RssItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent,false);
        RssItemViewHolder vh = new RssItemViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        holder.titleView.setText(list.get(position).title);
        holder.detailView.setText(list.get(position).description);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RssItemViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView detailView;
        public RssItemViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            detailView = itemView.findViewById(R.id.detail);
        }
    }
}
