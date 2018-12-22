package com.example.ting.larrydiary1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public adapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPosterThumbnail;
        public TextView tvPosterName;
        public TextView tvContent;
        public ImageButton btnLike;
        public ImageButton btnComment;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
