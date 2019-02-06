package com.example.movielistapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.movielistapp.Movies.DummyModel;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.utility.CallApiListener;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>{
    private List<Result>moviesList;
    private ClickListener listener;
    private Context mContext;
    int currentItem = 0;
    List<DataItemModel> dataMovieDetails;

    public void setCallApiListener(CallApiListener callApiListener) {
        this.callApiListener = callApiListener;
    }

    private CallApiListener callApiListener;


    List<DummyModel>dummyModelList;




    public MoviesListAdapter( Context mContext, ClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setMovieIdData(List<Result>moviesList){
       // this.dummyModelList = moviesList;
        this.moviesList = moviesList;
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
        Result model = moviesList.get(position);
        DataItemModel dataItemModel = model.getDataItemModel();
        if(null != dataItemModel){
            String title = dataItemModel.getTitle();
            String overview = dataItemModel.getOverview();
            //viewHolder.tvDescription.setText(title);
            viewHolder.tvTitle.setText(title);

        }else{
            Log.i("POSITION FROM ADAPTER", String.valueOf(position));
            callApiListener.callApi(model, position);
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
        TextView tvTitle,tvDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

    }
}
