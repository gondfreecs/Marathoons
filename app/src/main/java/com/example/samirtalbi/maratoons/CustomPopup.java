package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Samir Talbi on 04/01/2018.
 */

public class CustomPopup extends Dialog {

    private String gagnant;
    private String stats;
    private Button home, again;
    private TextView gagnantView, statsGagnant;

    public CustomPopup(Activity activity){
        super(activity);
        setContentView(R.layout.popup);
        home = (Button) findViewById(R.id.home);
        again = (Button) findViewById(R.id.again);
        gagnantView = (TextView)findViewById(R.id.gagnant);
        statsGagnant = (TextView)findViewById(R.id.stats);


    }


    public void setGagnant(String gagnant) {
        this.gagnant = gagnant;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public Button getHome() {
        return home;
    }

    public Button getAgain() {
        return again;
    }

    public void build(){
        gagnantView.setText(gagnant);
        statsGagnant.setText(stats);
        show();
    }
}
