package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ImageView image;

    Button button1;
    Button button2;
    Button button3;

    TextView textView;
    TextView timer;
    TextView scoreTextView;

    ArrayList<Integer> flags = new ArrayList<Integer>();
    ArrayList<Integer> random = new ArrayList<Integer>();
    ArrayList<String> buttonTexts = new ArrayList<String>();
    ArrayList<String> arr = new ArrayList<String>();

    String mLine;
    String answerOnButton;

    int scoreValue = 0;
    int questionNumber = 1;
    int getButtonId;

    Field[] fields = R.drawable.class.getFields();
    List<Integer> drawables = new ArrayList<Integer>();
    Score score = new Score(0);

    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);

        image = findViewById(R.id.imageView2);

        textView = findViewById(R.id.textView);

        timer = findViewById(R.id.timer);

        scoreTextView = findViewById(R.id.highScore);

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
       // timerClass.startTimer();

        for (Field field : fields) {
            if (field.getName().startsWith("fl")) {
                try {
                    drawables.add(field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < drawables.size(); i++) {
            random.add(i);
        }

        Collections.shuffle(random);
        scoreTextView.setText("Score: " + scoreValue);
        readData();
        try {
            algorithm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;

            timer.setText("" + mins + ":"+ String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };

    public void readData() {

        for (int i = 0; i < drawables.size(); i++) {
            flags.add(getResources().getIdentifier("flag" + i, "drawable", getPackageName()));
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("Flag50.txt")));

            while ((mLine = reader.readLine()) != null) {
                buttonTexts.add(mLine);
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void algorithm() throws IOException {

        textView.setText("Question " + questionNumber);
        questionNumber++;

        for (int i = 0; i < 3; i++) {
            arr.add(buttonTexts.get(random.get(i)));
        }

        image.setImageResource(flags.get(random.get(0))); //Image

        answerOnButton = arr.get(0);
        Collections.shuffle(arr);

        button1.setText(arr.get(0));
        button2.setText(arr.get(1));
        button3.setText(arr.get(2));

        arr.clear();

        random.remove(0);
        Collections.shuffle(random);
    }

    public void clickFunc(View view) {

        getButtonId = view.getId();
        Button button = findViewById(getButtonId);

        Handler handler = new Handler();

        final Animation animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin);
        final Animation animZoomIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

        if (button.getText() == answerOnButton) {
            scoreValue = score.setScoreUp(scoreValue);

            if (random.size() == 2) {

                image.startAnimation(animShake);

                handler.postDelayed(new Runnable() {
                    public void run() {
                        sendMessage();
                    }
                }, 1450);
            } else {

                image.startAnimation(animShake);

                handler.postDelayed(new Runnable() {
                    public void run() {
                        try {
                            algorithm();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1450);
            }

        } else if (button.getText() != answerOnButton) {

            scoreValue = score.setScoreDown(scoreValue);
            view.startAnimation(animZoomIn);
            Toast();
        }
        scoreTextView.setText("Score: " + score.getScoreIn());
    }

    public void sendMessage() {
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("Score", score.getScoreIn());
        startActivity(intent);
    }

    public void Toast() {
        final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vib.vibrate(500);
        }
    }

}
