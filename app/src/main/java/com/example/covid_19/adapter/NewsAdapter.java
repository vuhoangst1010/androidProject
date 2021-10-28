package com.example.covid_19.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.model.entity.News;

import java.io.InputStream;
import java.util.List;

/**
 * @author PhuocNDT
 */
public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{
    private List<News> newsList;
    private Context context;

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

            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imvImage;
        TextView tvTitle, tvDes;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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
