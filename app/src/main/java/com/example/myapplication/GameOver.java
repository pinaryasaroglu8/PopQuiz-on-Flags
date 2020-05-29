package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {

    TextView totalScore;
    TextView highScoreTxt;

    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        totalScore = findViewById(R.id.totalScore);
        highScoreTxt = findViewById(R.id.highScore);

        Intent intent = getIntent();
        Intent intent2 = getIntent();

        int score = intent.getIntExtra("Score",0);
        String time = intent.getStringExtra("Time");

        totalScore.setText("Score Value: " + score);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        highScore = prefs.getInt("score", 0);
        //  highScore=0;

        if (highScore > score) {
            highScoreTxt.setText("High Score: " + highScore  );//" Time: " + time
        } else {
            highScore = score;
            highScoreTxt.setText("High Score: "+ highScore   );
            prefs.edit().putInt("score", highScore).apply();
        }
    }
}