package com.example.smartplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.smartplayer.MainActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity {

    TextView songName, artistName, durationPlayed, durationTotal;
    ImageView coverArt, nextButn, prevBtn, backBtn, shuffleBtn, repeartBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
    int position = -1;
    static ArrayList<MusicFiles> listSong = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntentMethod();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){

            }
        });


        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    durationPlayed.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }



    private String formattedTime(int mCurrentPosition) {
        String totalout = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;

        } else {
            return totalout;
        }
    }



    private void getIntentMethod(){
        position = getIntent().getIntExtra("position", -1);
        listSong = musicFiles;
        if (listSong != null)
        {
            playPauseBtn.setImageResource(R.drawable.puse);
            uri = Uri.parse(listSong.get(position).getPath());
        }
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);

    }

    private void initViews(){
        songName = findViewById(R.id.sone_name);
        artistName = findViewById(R.id.song_artist);
        durationPlayed = findViewById(R.id.sone_name);
        durationTotal = findViewById(R.id.song_artist);
        coverArt = findViewById(R.id.cover_art);
        nextButn = findViewById(R.id.next);
        backBtn = findViewById(R.id.previous);
        shuffleBtn = findViewById(R.id.shuffle);
        repeartBtn = findViewById(R.id.repeat);
        playPauseBtn = findViewById(R.id.play_puse);
        seekBar = findViewById(R.id.seekBar);
    }
}