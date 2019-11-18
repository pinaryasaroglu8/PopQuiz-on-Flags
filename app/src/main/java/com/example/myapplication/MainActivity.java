package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


    public MainActivity() {

    }

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

        for (int i = 0; i < 3; i++) {
            arr.add(buttonTexts.get(random.get(i)));
        }

        image.setImageResource(flags.get(random.get(0))); //Image

        answerOnButton = arr.get(0);

        Collections.shuffle(arr);

        textView.setText("Question " + questionNumber);
        questionNumber++;

        button1.setText(arr.get(0));
        button2.setText(arr.get(1));
        button3.setText(arr.get(2));

        arr.clear();
        random.remove(0);
        Collections.shuffle(random);

        if (random.size() == 3) {
            sendMessage();

        }
    }

    public void clickFunc(View view) throws IOException {

        getButtonId = view.getId();
        Button button = (Button) findViewById(getButtonId);

        if (button.getText() == answerOnButton) {
            scoreValue = score.setScoreUp(scoreValue);
            Animation animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin);
            image.startAnimation(animShake);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    try {
                        algorithm();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1450);


        } else {
            scoreValue = score.setScoreDown(scoreValue);
            Animation animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
            view.startAnimation(animShake);

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





