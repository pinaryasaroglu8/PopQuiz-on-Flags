package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    int scoreIn;

    public Score(int scoreIn) {
        this.scoreIn = scoreIn;
    }

    public int getScoreIn() {

        return scoreIn;
    }

    public int setScoreUp(int scoreIn) {

        return this.scoreIn = scoreIn + 1;
    }

    public int setScoreDown(int scoreIn) {

        return this.scoreIn = scoreIn - 1;
    }

}