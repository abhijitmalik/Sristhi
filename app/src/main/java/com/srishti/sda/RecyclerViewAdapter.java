package com.srishti.sda;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Model> dataList;
    private LayoutInflater inflater;
    private HorizontalScrollView headerScrollView;

    public RecyclerViewAdapter(Context context, List<Model> dataList, HorizontalScrollView headerScrollView) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.headerScrollView = headerScrollView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model item = dataList.get(position);
        holder.arrangerCode.setText(item.ArrangerCode);
        holder.arrangerName.setText(item.ArrangerName);
        holder.sponsorCode.setText(item.SponsorCode);
        // Set other fields...

        // Sync Horizontal Scrolling with Header
        holder.horizontalScroll.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            headerScrollView.scrollTo(scrollX, 0);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView arrangerCode, arrangerName, sponsorCode;
        HorizontalScrollView horizontalScroll;

        public ViewHolder(View itemView) {
            super(itemView);
            arrangerCode = itemView.findViewById(R.id.arrangerCode);
            arrangerName = itemView.findViewById(R.id.arrangerName);
            sponsorCode = itemView.findViewById(R.id.sponsorCode);
            horizontalScroll = itemView.findViewById(R.id.horizontalScroll);
        }
    }
}

