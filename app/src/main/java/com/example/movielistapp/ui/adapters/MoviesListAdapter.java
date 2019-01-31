package com.example.movielistapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.movielistapp.Movies.DummyModel;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>{
    private List<Result>moviesList;
    private Result movieItem;
    private ClickListener listener;
    private Context mContext;
    private FragmentManager fragmentManager;
    int currentItem = 0;
    List<DataItemModel> dataMovieDetails;


    List<DummyModel>dummyModelList;




    public MoviesListAdapter( Context mContext, ClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.fragmentManager = fragmentManager;
    }

    public void setMovieIdData(List<DummyModel>moviesList){
        this.dummyModelList = moviesList;
        notifyDataSetChanged();
    }

//    public void setMovieDetailsUiData(List<DataItemModel> dataMovieDetails){
//        this.dataMovieDetails = dataMovieDetails;
//        notifyDataSetChanged();
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_movie_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        currentItem = position;
        DataItemModel model = dummyModelList.get(position).getData();
        viewHolder.tvDescription.setText(model.getOverview());
        viewHolder.tvTitle.setText(model.getTitle());

    }

    public int getCurrentItem() {
        return currentItem;
    }

    @Override
    public int getItemCount() {
        return dummyModelList ==null ? 0: dummyModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tvTitle,tvDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

    }
}
