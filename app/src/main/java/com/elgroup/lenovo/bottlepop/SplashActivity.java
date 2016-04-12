package com.elgroup.lenovo.bottlepop;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;


/**
 * Created by Lenovo on 19-01-2016.
 */
public class SplashActivity extends Activity {
    private VideoView surfaceViewFrame;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);

        InitViews();
    }

    private void InitViews() {
        surfaceViewFrame = (VideoView) findViewById(R.id.surfaceViewFrame);
        surfaceViewFrame.setVisibility(View.GONE);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
        surfaceViewFrame.setVideoURI(Uri.parse(path));
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) surfaceViewFrame.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;

        surfaceViewFrame.setLayoutParams(params);


    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                surfaceViewFrame.setVisibility(View.VISIBLE);
                surfaceViewFrame.start();
                surfaceViewFrame.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        }, 2000);
    }


}
