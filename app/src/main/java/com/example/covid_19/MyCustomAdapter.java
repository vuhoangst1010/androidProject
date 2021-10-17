package com.example.covid_19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> {

    private List<RecycleItem> recycleItemList;
    private Context context;

    public MyCustomAdapter(List<RecycleItem> recycleItemList, Context context) {
        this.recycleItemList = recycleItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imvImage.setBackgroundResource(recycleItemList.get(position).getImageId());
        holder.tvTitle.setText(recycleItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recycleItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imvImage;
        TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imvImage = itemView.findViewById(R.id.imvImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, MainActivity.class));
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }
}
