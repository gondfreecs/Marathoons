package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class welcome extends Activity {
    private TextView credit;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        credit = (TextView)findViewById(R.id.credit);
        logo = (ImageView)findViewById(R.id.logowelcome);

        Animation anime = AnimationUtils.loadAnimation(this,R.anim.transition);
        Animation anime2 = AnimationUtils.loadAnimation(this,R.anim.transitionbas);

        credit.startAnimation(anime2);
        logo.startAnimation(anime);

        final Intent i = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    startActivity(i);
                    finish();
                }
            }

        };
            timer.start();
    }
}