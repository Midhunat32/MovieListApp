package com.example.movielistapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.movielistapp.R;
import com.example.movielistapp.cloud.Result;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>{
    List<Result>moviesList;
    Result movieItem;
    ClickListener listener;

    public MoviesListAdapter(ClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Result>moviesList){
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movies_mob_,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return moviesList ==null ? 0: moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMoviesName;
        ImageView ivMoviePoster;
        LinearLayout layoutMain;
        android.support.v7.widget.CardView cv_listcontents;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMoviesName = itemView.findViewById(R.id.tvMovieName);
            ivMoviePoster = itemView.findViewById(R.id.ivMovieImage);
            layoutMain = itemView.findViewById(R.id.layoutMain);
        }
        public void onBind(final int position){
            movieItem = moviesList.get(position);
            tvMoviesName.setText(movieItem.getId());
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(movieItem,position,itemView);
                }
            });

        }
    }
}
