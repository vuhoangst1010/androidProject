package com.example.covid_19.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.model.entity.RecycleItem;
import com.example.covid_19.presentation.MainActivity;
import com.example.covid_19.presentation.NewsActivity;

import java.util.List;

/**
 * @author PhuocNDT
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<RecycleItem> recycleItemList;
    private Context context;

    public HomeAdapter(List<RecycleItem> recycleItemList, Context context) {
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
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onCLick(View v, int position, boolean isLongClick) {
                Intent intent = null;
                RecycleItem item = recycleItemList.get(position);
                switch (item.getName()) {
                    case "Data":
                        intent = new Intent(context, MainActivity.class);
                        break;
                    case "News":
                        intent = new Intent(context, NewsActivity.class);
                }
                context.startActivity(intent);
//                setAnimation(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recycleItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imvImage;
        TextView tvTitle;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imvImage = itemView.findViewById(R.id.imvImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onCLick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }

}
