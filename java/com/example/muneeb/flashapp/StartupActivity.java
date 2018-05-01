package com.example.muneeb.flashapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class StartupActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000; //Time for the Splash Screen to stay
    private GifImageView gifImageView; //Declarartion of GIF

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        gifImageView = (GifImageView) findViewById(R.id.gifView);

        try {
            InputStream inputStream = getAssets().open("startupgif.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StartupActivity.this.startActivity(new Intent (StartupActivity.this, MainActivity.class));
                StartupActivity.this.finish();
            }
        },SPLASH_TIME_OUT);
    }
}
//END
