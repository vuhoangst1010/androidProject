package com.example.covid_defender.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_defender.R;
import com.example.covid_defender.model.entity.Data;
import com.example.covid_defender.presentation.ChartActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    int m = 1;
    Context context;
    List<Data> countryList;
    List<Data> filterList;

    public DataAdapter(Context context, List<Data> countryList) {
        this.context = context;
        this.countryList = countryList;
        this.filterList = countryList;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data data = filterList.get(position);
        if (m == 1) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        } else if (m == 2) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getRecovered())));
        } else if (m == 3) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getDeaths())));
        } else {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getActive())));
        }
        holder.country.setText(data.getCountry());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onCLick(View v, int position, boolean isLongClick) {

                Intent intent = new Intent(context, ChartActivity.class);
                Data country = filterList.get(position);
                intent.putExtra("country", country);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView cases, country;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            cases = itemView.findViewById(R.id.countrycase);
            country = itemView.findViewById(R.id.countryname);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onCLick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onCLick(view, getAdapterPosition(), true);
            return true;
        }
    }

    public void caseFilter(String charText) {
        if (charText.equals("cases")) {
            m = 1;
        } else if (charText.equals("recovered")) {
            m = 2;
        } else if (charText.equals("deaths")) {
            m = 3;
        } else {
            m = 4;
        }

        notifyDataSetChanged();
    }

    public void countryFilter(String keyword) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            filterList = countryList.stream().filter(x -> x.getCountry().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }

}
