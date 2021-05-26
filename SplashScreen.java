package com.adam.iotappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen_design);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.openingImage);
        logo = findViewById(R.id.myName);
        slogan = findViewById(R.id.openingSlogan);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        int SPLASH_TIME_OUT = 3500;
        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashScreen.this,Login.class);
            Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View, String>(image, "logoImage");
            pairs[1] = new Pair<View, String>(slogan, "slogan");
            pairs[2] = new Pair<View, String>(logo, "myName");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
            startActivity(intent, options.toBundle());
            finish();


        }, SPLASH_TIME_OUT);
    }
}