package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class mode extends Activity implements View.OnClickListener {
    Button joueur;
    Button bot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode);
        Intent intent = getIntent();
        joueur = (Button)findViewById(R.id.vsjoueur);
        joueur.setOnClickListener(this);

        bot = (Button)findViewById(R.id.vsbot);
        bot.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.vsjoueur :
                Intent intent =  new Intent( this, parametre.class);
                startActivity(intent);
                break;

            case R.id.vsbot :
                Intent intent2 =  new Intent(this,choixmodeia.class );
                startActivity(intent2);
                break;
        }

    }
}
