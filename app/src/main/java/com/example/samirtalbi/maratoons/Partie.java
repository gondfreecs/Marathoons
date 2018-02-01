package com.example.samirtalbi.maratoons;

import java.util.ArrayList;

/**
 * Created by Samir Talbi on 02/01/2018.
 */

public class Partie {
    int id;
    String temps;
    String date;
    Joueur gagnan;
    ArrayList<Joueur> liste;

    public Partie(int id, String temps, String date, Joueur gagnan, ArrayList<Joueur> liste) {
        this.id = id;
        this.temps = temps;
        this.date = date;
        this.gagnan = gagnan;
        this.liste = liste;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Joueur getGagnan() {
        return gagnan;
    }

    public void setGagnan(Joueur gagnan) {
        this.gagnan = gagnan;
    }

    public ArrayList<Joueur> getListe() {
        return liste;
    }

    public void setListe(ArrayList<Joueur> liste) {
        this.liste = liste;
    }




}
