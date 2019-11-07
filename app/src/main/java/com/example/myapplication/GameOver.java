package com.example.myapplication;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class GameOver extends AppCompatActivity {

    TextView totalScore;
    private Object MyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        totalScore = (TextView) findViewById(R.id.totalScore);
        Intent intent = getIntent();
        int message = intent.getIntExtra("Score",0);

        totalScore.setText("Score Value: " + message);

    }
}
