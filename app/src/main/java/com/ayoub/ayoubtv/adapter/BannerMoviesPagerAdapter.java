package com.ayoub.ayoubtv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ayoub.ayoubtv.MovieDetails;
import com.ayoub.ayoubtv.R;
import com.ayoub.ayoubtv.model.BannerMovies;
import com.bumptech.glide.Glide;

import java.util.List;

public class BannerMoviesPagerAdapter extends PagerAdapter {
    Context context;
    List<BannerMovies> bannerMovieslist;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMovieslist) {
        this.context = context;
        this.bannerMovieslist = bannerMovieslist;
    }

    @Override
    public int getCount() {
        return bannerMovieslist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View view= LayoutInflater.from(context).inflate(R.layout.banner_movie_layout,null);

        final ImageView bannerImage=view.findViewById(R.id.banner_image);

        Glide.with(context).load(bannerMovieslist.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movieId",bannerMovieslist.get(position).getId());
                i.putExtra("movieName",bannerMovieslist.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerMovieslist.get(position).getImageUrl());
                i.putExtra("movieFileUrl",bannerMovieslist.get(position).getFileUrl());
                context.startActivity(i);
                //Toast.makeText(context,""+bannerMovieslist.get(position).getMovieName(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
