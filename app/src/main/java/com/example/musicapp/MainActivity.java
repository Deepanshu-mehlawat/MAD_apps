package com.example.musicapp;

import android.Manifest;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import android.os.Build;
import android.os.Bundle;
import android.media.MediaPlayer;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import android.os.Environment;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ImageButton playpause;
    boolean isplaying = false;
    MediaPlayer music;
    TextView tv;
    private ListView mp3ListView;
    private static final int PERMISSION_REQUEST_CODE = 100;
    int i=0;
    ArrayList<String> mp3Files;
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
        mp3ListView = findViewById(R.id.mp3ListView);
        tv.setText("Play a Song");
        checkPermission(permission.READ_MEDIA_AUDIO, PERMISSION_REQUEST_CODE);
        mp3Files = getMP3Files();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mp3Files);
        mp3ListView.setAdapter(adapter);
        mp3ListView.setOnItemClickListener(this);

    }

    public void checkPermission(String permission, int requestCode)
    {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }


    private ArrayList<String> getMP3Files() {
        ArrayList<String> mp3Files = new ArrayList<>();
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] files = downloadDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    mp3Files.add(file.getName());
                }
            }
        }
        return mp3Files;
    }


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

    public void prev(View v)
    {
        music.stop();
        isplaying=false;
        playpause.setImageResource(R.drawable.pause);

        i-=1;
        if (i < 0) {
            i = mp3Files.size() - 1;
        }
        setmusic(mp3Files.get(i));
        play(v);
    }

    public void next(View v){
        music.stop();
        playpause.setImageResource(R.drawable.pause);
        isplaying=false;
        i+=1;
        if (i >= mp3Files.size()) {
            i = 0;
        }
        setmusic(mp3Files.get(i));
        play(v);
    }

    private void setmusic(String song) {
        try {
            if (music != null) {
                music.release();
            }
            music = new MediaPlayer();
            music.setDataSource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + song);
            music.prepare();
            tv.setText("Currently playing: " + song);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        i=position;
        String selectedSong = (String) parent.getItemAtPosition(position);
        music.stop();
        isplaying=false;
        setmusic(selectedSong);
        play(view);
    }
}