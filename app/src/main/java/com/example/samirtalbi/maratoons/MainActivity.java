package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    Button play;
    Button score;
    Button option;
    Button audiobutton;



    int length;// seconde ou la mussique s'arrete android thread example
    MediaPlayer mediaPlayer;
    boolean audio = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button)findViewById(R.id.playbut);
        play.setOnClickListener(this);

        score = (Button)findViewById(R.id.score);
        score.setOnClickListener(this);

        audiobutton = (Button)findViewById(R.id.audiobutton);
        audiobutton.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this,R.raw.song);

        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }


    public void onStop() {
        super.onStop();
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
    }




    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.playbut :
                Intent intent =  new Intent( this, mode.class);
                startActivity(intent);
                break;

            case R.id.score :
                Intent intent2 =  new Intent(this, meilleurescore.class );
                startActivity(intent2);
                break;

            case R.id.audiobutton :
                if(audio==true){
                    mediaPlayer.pause();
                    audio = false;
                    audiobutton.setText("\uD83C\uDFB5");
                }
                else{
                    mediaPlayer.start();
                    audio = true;
                    audiobutton.setText("\uD83D\uDD07");
                }
                break;



        }

    }
}
