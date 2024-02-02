package com.ayoub.ayoubtv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ayoub.ayoubtv.adapter.BannerMoviesPagerAdapter;
import com.ayoub.ayoubtv.adapter.MainRecyclerAdapter;
import com.ayoub.ayoubtv.model.AllCategory;
import com.ayoub.ayoubtv.model.BannerMovies;
import com.ayoub.ayoubtv.retrofit.RetrofitClient;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout IndicatorTab,categoryTab;
    ViewPager bannerMoviesViewPager;
    Timer sliderTimer;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    List<BannerMovies> KidsHomeBannerList;
    List<BannerMovies> KidsCartoonBannerList;
    List<BannerMovies> KidsMovieBannerList;
    List<BannerMovies> KidsSongBannerList;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;

    List<AllCategory> allCategoryList;
    //ads
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ads
        //banner
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-1299787344129628/2276280171");

        // TODO: Add adView to your view hierarchy.
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //interstitial
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
        } else {
            Toast.makeText(this,"mazal",Toast.LENGTH_LONG).show();
        }





        IndicatorTab =findViewById(R.id.tab_indicator);
        categoryTab=findViewById(R.id.tabLayout);
        nestedScrollView=findViewById(R.id.nested_scroll);
        appBarLayout=findViewById(R.id.appbar);

        KidsHomeBannerList =new ArrayList<>();
        KidsCartoonBannerList =new ArrayList<>();
        KidsMovieBannerList =new ArrayList<>();
        KidsSongBannerList =new ArrayList<>();

        //fetch banner Data from server
        getBannerData();
        //getting movies from server
        getAllMoviesData(1);

        setBannerMoviesPagerAdapter(KidsHomeBannerList);

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                case 1:
                    setScrollDefaultState();
                    setBannerMoviesPagerAdapter(KidsCartoonBannerList);
                    getAllMoviesData(2);

                    return;
                case 2:
                    setScrollDefaultState();
                    setBannerMoviesPagerAdapter(KidsMovieBannerList);
                    getAllMoviesData(3);
                    return;
                case 3:
                    setScrollDefaultState();
                    setBannerMoviesPagerAdapter(KidsSongBannerList);
                    getAllMoviesData(4);
                    return;
                default:
                    setScrollDefaultState();
                    setBannerMoviesPagerAdapter(KidsHomeBannerList);
                    getAllMoviesData(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        allCategoryList=new ArrayList<>();



    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList){
        bannerMoviesViewPager=findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter=new BannerMoviesPagerAdapter(this,bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);
        IndicatorTab.setupWithViewPager(bannerMoviesViewPager);

        sliderTimer=new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
        IndicatorTab.setupWithViewPager(bannerMoviesViewPager,true);
    }
    class AutoSlider extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(()->{
                if(bannerMoviesViewPager.getCurrentItem()< KidsHomeBannerList.size()-1){

                    bannerMoviesViewPager.setCurrentItem((bannerMoviesViewPager.getCurrentItem()+1));

                }
                else{
                    bannerMoviesViewPager.setCurrentItem(0);
                }

            });
        }
    }
    public void setMainRecycler(List<AllCategory> allCategoryList){

        mainRecycler=findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }
    private void setScrollDefaultState(){

        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);

    }

    private void getBannerData(){

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver <List<BannerMovies>>(){
                    @Override
                    public void onNext(List<BannerMovies> bannerMovies) {
                        for (int i = 0; i <bannerMovies.size() ; i++) {
                            if(bannerMovies.get(i).getBannerCategoryId().equals("1")){
                                KidsHomeBannerList.add(bannerMovies.get(i));
                            }else if (bannerMovies.get(i).getBannerCategoryId().equals("2")){
                                KidsCartoonBannerList.add(bannerMovies.get(i));
                            }else if (bannerMovies.get(i).getBannerCategoryId().equals("3")){
                                KidsMovieBannerList.add(bannerMovies.get(i));
                            }else if (bannerMovies.get(i).getBannerCategoryId().equals("4")){
                                KidsSongBannerList.add(bannerMovies.get(i));
                            }else{

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("banner",""+e);

                    }

                    @Override
                    public void onComplete() {
                        setBannerMoviesPagerAdapter(KidsHomeBannerList);
                    }
                }));

    }
    private void getAllMoviesData(int categoryId){

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllCategoriesMovies(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver <List<AllCategory>>(){
                    @Override
                    public void onNext(List<AllCategory> allCategoryList) {
                        setMainRecycler(allCategoryList);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("banner",""+e);

                    }

                    @Override
                    public void onComplete() {
                    }
                }));

    }

}
