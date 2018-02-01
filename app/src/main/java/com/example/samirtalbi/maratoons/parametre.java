package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Samir Talbi on 28/12/2017.
 */

public class parametre extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    LinearLayout editjoueur;

    Spinner Nbpseudo;

    Button Go;
    Button valid;

    Boolean test = true;

    Boolean chronoT = false;

    CheckBox checkbox;

    boolean checked;


    private List<EditText> editPlayerText = new ArrayList<>();

    int s=0;

    SeekBar distanceBar;

    TextView distanceText;

    int distance= 42195;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editjoueur);
        Intent intent = getIntent();
        this.editjoueur = (LinearLayout) findViewById(R.id.editJ);

        Nbpseudo = (Spinner) findViewById(R.id.editnombre);

        Nbpseudo.setOnItemSelectedListener(this);

        checkbox= (CheckBox) findViewById(R.id.chrono) ;


        Go = (Button) findViewById(R.id.gopseudo);
        Go.setOnClickListener(this);

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

    }


    @Override
    public void onClick(View view) {
        if (view == Go) {

            if(!editPlayerText.isEmpty())
            for (EditText b : editPlayerText) {
                b.setVisibility(View.GONE);
                valid.setVisibility(View.GONE);
            }

            //while (test == true) {
                int a = Integer.valueOf(s);

                editPlayerText.clear();

                for (int i = 1; i <= a; i++) {
                    EditText b = new EditText(this);
                    b.setId(View.generateViewId());
                    b.setText("Joueur " + i);
                    b.setGravity(Gravity.CENTER_HORIZONTAL);
                    editjoueur.addView(b);
                    editPlayerText.add(b);
                }

                valid = new Button(this);
                valid.setText("Commencer !");
                valid.setOnClickListener(this);
                editjoueur.addView(valid);
                test = false;

           //}
        }
        if (view == valid) {

            checked = checkbox.isChecked();
            if (checked)
                chronoT=true;
            else
                chronoT=false;



            ArrayList<String> pseudoliste = new ArrayList<String>();

            for (EditText b : editPlayerText) {

                pseudoliste.add(b.getText().toString());


            }

            Intent intent = new Intent(this, ingame.class);
            intent.putExtra("chrono",chronoT);
            intent.putExtra("distance",distance);
            intent.putStringArrayListExtra("liste",(ArrayList<String>) pseudoliste);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        s=i+2;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }






}
