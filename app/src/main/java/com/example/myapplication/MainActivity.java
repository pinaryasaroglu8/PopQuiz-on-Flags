package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ImageView image;

    Button button1;
    Button button2;
    Button button3;

    TextView textView;
    TextView highScore;

    ArrayList<Integer> flags = new ArrayList<Integer>();
    ArrayList<Integer> random = new ArrayList<Integer>();
    ArrayList<String> buttonTexts = new ArrayList<String>();
    ArrayList<String> arr = new ArrayList<String>();

    String mLine;
    String answerOnButton;

    int scoreValue = 0;
    int questionNumber = 1;

    Field[] fields = R.drawable.class.getFields();
    List<Integer> drawables = new ArrayList<Integer>();
    Score score = new Score(0);
    int a;

    public final static String EXTRA_MESSAGE = "Score Value";

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        image = (ImageView) findViewById(R.id.imageView2);
        textView = (TextView) findViewById(R.id.textView);
        highScore = (TextView) findViewById(R.id.highScore);


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
        highScore.setText("Score: " + scoreValue);
        readData();
        algorithm();

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


    public void algorithm() {

        for (int i = 0; i < 3; i++) {
            arr.add(buttonTexts.get(random.get(i)));
        }

        image.setImageResource(flags.get(random.get(0))); //Imag

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

    public void clickFunc(View view) {

        a = view.getId();
        Button button = (Button) findViewById(a);

        if (button.getText() == answerOnButton) {
            scoreValue = score.setScoreUp(scoreValue);
            algorithm();
        } else {
            scoreValue = score.setScoreDown(scoreValue);
            Toast();
        }
        highScore.setText("Score: " + score.getScoreIn());
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
