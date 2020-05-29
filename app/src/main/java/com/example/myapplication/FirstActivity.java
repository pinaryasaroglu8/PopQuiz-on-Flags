package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {

    TextView totalnumberOfFlagsTxt;
    TextView numberOfFlagsTxt;
    SeekBar seekBar;
    Button okButton;

    int number;
    int prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        totalnumberOfFlagsTxt = findViewById(R.id.numberOfFlags);
        seekBar = findViewById(R.id.seekBar);
        numberOfFlagsTxt = findViewById(R.id.totalNumber);
        okButton = findViewById(R.id.okButton);


        final Intent intent = new Intent(this, MainActivity.class);

        seekBar.setMax(50);

        numberOfFlagsTxt.setText("Covered: " + seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfFlagsTxt.setText("Number of flags:" + progress + "/" + seekBar.getMax());
                prog = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                number = prog;

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (number == 0) {
                    Toast.makeText(getApplicationContext(), "Number of flags must be higher than 0", Toast.LENGTH_LONG).show();
                } else {
                    intent.putExtra("NumberOfFlags", number);
                    startActivity(intent);
                }

            }
        });
    }
}
