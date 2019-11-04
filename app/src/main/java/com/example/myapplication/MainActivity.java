package com.example.myapplication;

import android.content.Context;
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

    ArrayList<Integer> flags = new ArrayList<Integer>();
    ArrayList<Integer> random = new ArrayList<Integer>();
    ArrayList<String> buttonTexts = new ArrayList<String>();

    Field[] fields = R.drawable.class.getFields();
    List<Integer> drawables = new ArrayList<Integer>();

    ArrayList<String> arr = new ArrayList<String>();

    String mLine;
    Object answerOnButton;

    int questionNumber = 1;

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


        // a=random.get(0);
        readData();
        algorithm();


    }

    public void readData() {

        for (int i = 0; i <  drawables.size(); i++) {
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

        image.setImageBitmap(null);
        image.setImageResource(flags.get(random.get(0))); //Image

        int b = flags.get(random.get(0));
        answerOnButton = arr.get(0);

        Collections.shuffle(arr);
     //   Collections.shuffle(random);

        textView.setText("Question " + questionNumber);
        questionNumber++;

        button1.setText(arr.get(0));
        button2.setText(arr.get(1));
        button3.setText(arr.get(2));

        //  random.remove(0);

        buttonClick();


    }


    public void buttonClick() {

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button1.getText() == answerOnButton) {

                    arr.clear();
                    random.remove(0);
                    algorithm();
                } else {
                    Toast();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button2.getText() == answerOnButton) {
                    arr.clear();
                    random.remove(0);
                    algorithm();

                } else {
                    Toast();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button3.getText() == answerOnButton) {

                    arr.clear();
                    random.remove(0);
                    algorithm();
                } else {
                    Toast();
                }
            }
        });

    }


    public void Toast() {
        final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vib.vibrate(500);
        }
    }

}
