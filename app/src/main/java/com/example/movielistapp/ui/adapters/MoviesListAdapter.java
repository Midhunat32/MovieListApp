package com.example.movielistapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.movielistapp.R;
import com.example.movielistapp.cloud.Result;
import com.example.movielistapp.cloud.responsemodel.MainResponse;
import com.example.movielistapp.ui.activity.BaseActivity;
import com.example.movielistapp.ui.fragment.MovieItemListFragment;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>{
    private List<Result>moviesList;
    private Result movieItem;
    private ClickListener listener;
    private Context mContext;
    private FragmentManager fragmentManager;
    int currentItem = 0;




    public MoviesListAdapter(FragmentManager fragmentManager, Context mContext, ClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.fragmentManager = fragmentManager;
    }

    public void setData(List<Result>moviesList){
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_movie_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        currentItem = position;
        Result result = moviesList.get(position);
        if(null != result){
            Integer id = result.getId();

        }

    }

    public int getCurrentItem() {
        return currentItem;
    }

    @Override
    public int getItemCount() {
        return moviesList ==null ? 0: moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            textView = itemView.findViewById(R.id.tvText);
        }
    }
}
