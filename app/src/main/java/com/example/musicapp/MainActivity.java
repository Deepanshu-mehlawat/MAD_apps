package com.example.musicapp;

import android.os.Bundle;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer music;
    TextView tv;
    int i=0;
    @Override
    protected void onCreate(
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding the music file to our
        // newly created object music
        music = MediaPlayer.create(this, R.raw.royalty);
        tv = findViewById(R.id.sname);
        tv.setText("Royalty");
    }

    // Playing the music
    public void play(View v)
    {
        music.start();
    }

    // Pausing the music
    public void pause(View v)
    {
        music.pause();
    }

    // Stopping the music
    public void stop(View v)
    {
        music.stop();
        if (i%2==1){music = MediaPlayer.create(this, R.raw.royalty);
        tv.setText("Royalty");
        }
        else{music = MediaPlayer.create(this, R.raw.going_bad);
        tv.setText("Going Bad");
        }
        i+=1;
    }
}