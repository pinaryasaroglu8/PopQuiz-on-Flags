package com.example.myapplication;
import android.os.Handler;
import java.util.TimerTask;

public class Timer {

   Timer timer;
   TimerTask timertask;
   final Handler handler = new Handler();

   public void startTimer() {
       timer = new Timer();

   }
}
