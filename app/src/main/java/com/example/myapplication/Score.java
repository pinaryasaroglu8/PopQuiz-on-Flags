package com.example.myapplication;

public class Score {

    int scoreIn;

    public Score(int scoreIn) {
        this.scoreIn = scoreIn;
    }

    public int setScoreUp(int scoreIn) {

        return this.scoreIn = scoreIn + 1;
    }

    public int setScoreDown(int scoreIn) {

        return this.scoreIn = scoreIn - 1;
    }

}