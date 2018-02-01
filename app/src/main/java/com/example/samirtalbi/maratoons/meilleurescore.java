package com.example.samirtalbi.maratoons;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class meilleurescore extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        Intent intent = getIntent();
        DBManager bd = new DBManager(this.getBaseContext());
        bd.open();
        try{
            Joueuradapter c = new Joueuradapter(getApplicationContext(),bd.getAllAccounts());
            list = (ListView) findViewById(R.id.listview);
            list.setAdapter(c);
        }catch(CursorIndexOutOfBoundsException e){
        }
        bd.close();
    }


}
