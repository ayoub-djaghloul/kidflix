package com.ayoub.ayoubtv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MovieDetails extends AppCompatActivity {
    ImageView movieImage;
    TextView movieName;
    Button playButton;

    String mName,mImage,mId,mFileUrl;
    public InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieImage =findViewById(R.id.movie_image);
        movieName=findViewById(R.id.movie_name);
        playButton=findViewById(R.id.play_button);

        //get from intent
        mId=getIntent().getStringExtra("movieId");
        mImage=getIntent().getStringExtra("movieImageUrl");
        mName=getIntent().getStringExtra("movieName");
        mFileUrl=getIntent().getStringExtra("movieFileUrl");

        Glide.with(this).load(mImage).into(movieImage);
        movieName.setText(mName);
        //ads
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-1299787344129628/6324433870", adRequest,
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

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MovieDetails.this);
                } else {
                    Toast.makeText(MovieDetails.this,"after",Toast.LENGTH_LONG).show();
                }
                /*Intent i= new Intent(MovieDetails.this,VideoPlayerActivity.class);
                i.putExtra("url",mFileUrl);
                startActivity(i);*/
                String videoUrl = "https://www.beinsports.com/live/video_link";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(videoUrl), "video/*");
                intent.setPackage("com.mxtech.videoplayer.ad");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // MX Player app is not installed, you can show an error message or prompt the user to install the app
                }
                //Toast.makeText(MovieDetails.this,"En fin",Toast.LENGTH_SHORT).show();

            }
        });

    }
}