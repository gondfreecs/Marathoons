package com.example.samirtalbi.maratoons;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samir Talbi on 04/12/2017.
 */

public class Joueuradapter extends BaseAdapter {

    private Context context;

    Boolean first = true;

    private List<Joueur> joueurList = new ArrayList<>();

    public Joueuradapter(Context context, List<Joueur> accountList) {
        this.context = context;
        this.joueurList = accountList;
    }

    @Override
    public int getCount() {
        return joueurList.size();
    }

    @Override
    public Object getItem(int pos) {
        return joueurList.get(pos);
    }

    @Override
    public long getItemId(int id) {
        return joueurList.get(id).getId();
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        Joueur joueurs = (Joueur) getItem(pos);
        if (view == null) {
            view = View.inflate(context, R.layout.listtext, null);

        }
        TextView nom = (TextView) view.findViewById(R.id.text1);
        TextView desc = (TextView) view.findViewById(R.id.text2);

        if(first == true){
            nom.setText(String.valueOf("\uD83C\uDFC6"+joueurs.getPseudo()));
            desc.setText("Score : "+String.valueOf(joueurs.getScore()));
            first = false;
        }
        nom.setText(String.valueOf(joueurs.getPseudo()));
        desc.setText("Score : "+String.valueOf(joueurs.getScore()));

        // Lookup view for data population
        /*ListView list = (ListView) view.findViewById(R.id.listView);
        // Populate the data into the template view using the data object
        list.setText(user.name);
        tvHome.setText(user.hometown);
        // Return the completed view to render on screen*/


        return view;
    }
}