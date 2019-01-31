package com.example.movielistapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


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


    public MoviesListAdapter(Context mContext, ClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
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
        Result result = moviesList.get(position);
        if(null != result){
            Integer id = result.getId();
            loadFragment(id);
        }

    }

    private void loadFragment(Integer id) {
     /*   MovieItemListFragment fr = MovieItemListFragment.newInstance(id);

        FragmentTransaction fragmentTransaction = mContext.fr.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fr);

        fragmentTransaction.commit();*/

    }

    @Override
    public int getItemCount() {
        return moviesList ==null ? 0: moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        FrameLayout flContainer;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            flContainer = itemView.findViewById(R.id.flContainer);
        }
    }
}
