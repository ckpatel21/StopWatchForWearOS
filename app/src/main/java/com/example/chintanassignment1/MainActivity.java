package com.example.chintanassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chintanassignment1.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Declare Variables
    ActivityMainBinding mainBinding;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize view binding
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        // Initialize Timer
        timer = new Timer();

        // Set default state for buttons
        mainBinding.btnStart.setEnabled(true);
        mainBinding.btnStop.setEnabled(false);
        mainBinding.btnReset.setEnabled(false);

        // Start Button click listener
        mainBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Call startTimer function upon clicking Start button
                startTimer();
                // Change the state of buttons
                mainBinding.btnStart.setEnabled(false);
                mainBinding.btnStop.setEnabled(true);
                mainBinding.btnReset.setEnabled(false);
            }
        });

        // Stop Button click listener
        mainBinding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Stop the task to pause the timer and change the state of buttons
                timerTask.cancel();
                mainBinding.btnStart.setEnabled(true);
                mainBinding.btnStop.setEnabled(false);
                mainBinding.btnReset.setEnabled(true);
            }
        });

        // Reset Button click listener
        mainBinding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerTask!=null){
                    // Stop the timer and set time to 0.0 and update the state of buttons
                    timerTask.cancel();
                    time = 0.0;
                    mainBinding.tvTimer.setText(R.string.defaultTime);
                }
                mainBinding.btnReset.setEnabled(false);
            }
        });
    }

    // Method to start the timer, creating
    private void startTimer() {
        // creates a object of a 'TimerTask', used to create a task that is to be repeated at fixed rate
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //  post the runnable to be on main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Increment the timer by 0.01 seconds and set the text to update the timer display
                        time += 0.001;
                        mainBinding.tvTimer.setText(getTimerText());
                    }
                });
            }
        };
        // Schedule the task for every 1 millisecond
        timer.scheduleAtFixedRate(timerTask, 0 ,1);
    }

    // Method to format time to HH:MM:SS:mm (Hours:Minutes:Seconds:Milliseconds)
    private String getTimerText() {
        int milliseconds = (int) ((time * 1000) % 1000);
        int seconds = (int) ((time) % 60);
        int minutes = (int) ((time / 60) % 60);
        int hours = (int) (time / 3600);
        return String.format("%02d",hours) + " : "
                + String.format("%02d",minutes) + " : "
                + String.format("%02d",seconds)+ " : "
                + String.format("%02d",milliseconds / 10);
    }
}