package com.example.musicapp;

import android.os.Bundle;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton playpause;
    boolean isplaying = false;
    MediaPlayer music;
    TextView tv;
    int i=0;
    @Override
    protected void onCreate(
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playpause = findViewById(R.id.playPauseButton);
        // Adding the music file to our
        // newly created object music
        music = MediaPlayer.create(this, R.raw.royalty);
        tv = findViewById(R.id.sname);
        tv.setText("Royalty");
    }

    // Playing the music
    public void play(View view) {
        if (isplaying) {
            music.pause();
            playpause.setImageResource(R.drawable.play); // Change to play icon
        } else {
            music.start();
            playpause.setImageResource(R.drawable.pause); // Change to pause icon
        }
        isplaying = !isplaying;
    }

    // Pausing the music
    public void prev(View v)
    {
        music.stop();
        isplaying=false;
        playpause.setImageResource(R.drawable.pause);
        i-=1;
        setmusic();
        play(v);
    }

    public void next(View v){
        music.stop();
        playpause.setImageResource(R.drawable.pause);
        isplaying=false;
        i+=1;
        setmusic();
        play(v);
    }

    private void setmusic() {
        if (i%2==0){music = MediaPlayer.create(this, R.raw.royalty);
            tv.setText("Royalty");
        }
        else{music = MediaPlayer.create(this, R.raw.going_bad);
            tv.setText("Going Bad");
        }
    }

}