package com.ayoub.ayoubtv.retrofit;


import com.ayoub.ayoubtv.model.AllCategory;
import com.ayoub.ayoubtv.model.BannerMovies;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("banner_movie.json")
    Observable<List<BannerMovies>> getAllBanners();

    @GET("{categoryId}/all_movies.json")
    Observable<List<AllCategory>> getAllCategoriesMovies(@Path("categoryId")int categoryId);
}
