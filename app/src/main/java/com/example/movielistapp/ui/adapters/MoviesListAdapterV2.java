package com.example.movielistapp.ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movielistapp.Movies.MovieItemModel;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.example.movielistapp.utility.ClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapterV2 extends RecyclerView.Adapter<MoviesListAdapterV2.ViewHolder> {
    private ClickListener listener;
    private Context mContext;
    private List<MovieItemModel> listMoviesData = new ArrayList<>();
    private final String IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    private String photoUrl;
    private static Picasso singleton = null;
    private static Picasso picasso = null;
    private String transitionName;


    public MoviesListAdapterV2(Context mContext, ClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        //picasso = with(mContext);

    }

//    public static Picasso with(Context context) {
//        if (singleton == null) {
//            synchronized (Picasso.class) {
//                if (singleton == null) {
//                    singleton = new Picasso.Builder(context).build();
//                }
//            }
//        }
//        return singleton;
//    }

    public void setMovieDataList(List<MovieItemModel> listMoviesData) {
        this.listMoviesData = listMoviesData;
        notifyDataSetChanged();
    }

    public void setMovieDataItem(MovieItemModel dataItem, int position) {
        listMoviesData.add(dataItem);
        notifyItemInserted(position);
    }

    public void clearData() {
        if (null != listMoviesData) {
            listMoviesData.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MoviesListAdapterV2.ViewHolder(LayoutInflater.from
                (viewGroup.getContext()).inflate(R.layout.rv_movie_list, viewGroup, false));

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MovieDetailsModel detailsModel = listMoviesData.get(position).getData();
        viewHolder.onBind(detailsModel,position);
    }

    @Override
    public int getItemCount() {
        return listMoviesData == null ? 0 : listMoviesData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvDescription, tvRating;
        ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void onBind(final MovieDetailsModel dataItemModel, final int position) {
            ViewCompat.setTransitionName(itemView,""+dataItemModel.getId());
            tvTitle.setText(dataItemModel.getTitle());
            tvDescription.setText(dataItemModel.getOverview());
            tvRating.setText("" + dataItemModel.getVoteAverage());
            if (null != dataItemModel.getPosterPath()) {
                photoUrl = IMAGE_URL + dataItemModel.getPosterPath();
                Picasso.get()
                        .load(photoUrl)
                        .noFade()
                        .centerCrop()
                        .resize(200,200)
                        .placeholder(R.drawable.ic_noimage)
                        .into(ivPoster);
            }

            cardView.setOnClickListener(v -> listener.onItemClicked(dataItemModel, position, v));

        }

    }

}
