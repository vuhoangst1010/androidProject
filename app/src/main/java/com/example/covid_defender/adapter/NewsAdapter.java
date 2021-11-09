package com.example.covid_defender.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_defender.R;
import com.example.covid_defender.model.entity.News;
import com.example.covid_defender.presentation.NewsDetail;

import java.io.InputStream;
import java.util.List;

import retrofit2.Retrofit;

/**
 * @author PhuocNDT
 */
public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{
    private List<News> newsList;
    private Context context;
    private Retrofit retrofit;

    public NewsAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_news, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(newsList.get(position).getTitle());
        holder.tvDes.setText(newsList.get(position).getDescription());
        String imagePath = newsList.get(position).getImage();
        new DownloadImageTask(holder.imvImage, imagePath)
                .execute();
        holder.imvImage.setAdjustViewBounds(false);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onCLick(View v, int position, boolean isLongClick) {
                String url = newsList.get(position).getUrl();
                onClickGoToDetail(url);
            }
        });
    }

    private void onClickGoToDetail(String url){
        Intent intent = new Intent(context, NewsDetail.class);
        Bundle bundle = new Bundle();
        bundle.putString("news_url",url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ConstraintLayout recycleNews;
        ImageView imvImage;
        TextView tvTitle, tvDes;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imvImage = itemView.findViewById(R.id.imvNewImage);
            tvTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvDes = itemView.findViewById(R.id.tvNewsDes);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onCLick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    /**
     * Download image from url
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        String imagePath;

        public DownloadImageTask(ImageView bmImage, String imagePath) {
            this.bmImage = bmImage;
            this.imagePath = imagePath;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = imagePath;
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
