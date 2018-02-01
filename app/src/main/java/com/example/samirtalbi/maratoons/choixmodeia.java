package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carbo on 05/01/2018.
 */


public class choixmodeia extends Activity implements View.OnClickListener {


    Button vsfort;
    Button vsfaible;

    EditText pseudo;

    Boolean chronoT = false;

    CheckBox checkbox;

    boolean checked;

    SeekBar distanceBar;

    TextView distanceText;

    int distance= 42195;


    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choixmodeia_layout);
        Intent intent = getIntent();
        vsfort = (Button) findViewById(R.id.vsfort);
        vsfort.setOnClickListener(this);

        checkbox= (CheckBox) findViewById(R.id.chrono) ;

        pseudo = (EditText)findViewById(R.id.pseudobot);

        vsfaible = (Button) findViewById(R.id.vsfaible);
        vsfaible.setOnClickListener(this);

        distanceBar= (SeekBar) findViewById(R.id.distanceBar);


        distanceText= (TextView) findViewById(R.id.distanceText);


        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distanceText.setText("Distance : "+(i+10000));
                distance=i+10000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



 /*IL MANQUE */
    }




    @Override
    public void onClick(View view) {



        switch (view.getId()) {
            case R.id.vsfort:

                checked = checkbox.isChecked();
                if (checked)
                    chronoT=true;
                else
                    chronoT=false;


                Intent intent = new Intent(this, vsbot.class);
                String a = "fort";
                intent.putExtra("level", a);
                intent.putExtra("pseudo",String.valueOf(pseudo.getText()));
                intent.putExtra("chrono",chronoT);
                intent.putExtra("distance",distance);
                startActivity(intent);
                break;


            case R.id.vsfaible:

                checked = checkbox.isChecked();
                if (checked)
                    chronoT=true;
                else
                    chronoT=false;

                Intent intent2 = new Intent(this, vsbot.class);
                String b = "faible";
                intent2.putExtra("level", b);
                intent2.putExtra("pseudo",String.valueOf(pseudo.getText()));
                intent2.putExtra("chrono",chronoT);
                intent2.putExtra("distance",distance);

                startActivity(intent2);
                break;
        }

    }


}