package com.example.wsywu.jumpingblocks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainGame extends Activity {
    private final String SAVE_HIGH_SCORE = "save_high_score";
    private GameControl mGameControl;
    private MediaPlayer audio1;
    private int highScore;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constant.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constant.SCREEN_HEIGHT = displayMetrics.heightPixels;

        mSharedPreferences = getPreferences(MODE_PRIVATE);
        highScore = mSharedPreferences.getInt(SAVE_HIGH_SCORE, 0);

        mGameControl = new GameControl(this, highScore);

        setContentView(mGameControl);

    }

    @Override
    public void onPause(){
        super.onPause();
        overridePendingTransition(0, 0);
        highScore = mGameControl.getHighScore();
        mSharedPreferences.edit().putInt(SAVE_HIGH_SCORE, highScore).commit();
        finish();
        //Log.w("my", "pause");
    }

    @Override
    public void onStop(){
        super.onStop();
        //Log.w("my", "stop");
        highScore = mGameControl.getHighScore();
        mSharedPreferences.edit().putInt(SAVE_HIGH_SCORE, highScore).commit();
        finish();
    }

    @Override
    public void onDestroy(){
        //Log.w("my", "destroy");
        super.onDestroy();
        highScore = mGameControl.getHighScore();
        mSharedPreferences.edit().putInt(SAVE_HIGH_SCORE, highScore).commit();
        finish();
    }
}

