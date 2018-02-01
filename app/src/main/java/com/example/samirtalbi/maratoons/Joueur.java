package com.example.samirtalbi.maratoons;

/**
 * Created by Samir Talbi on 02/01/2018.
 */

public class Joueur {
    public int id;
    public String pseudo;
    public int score;
    public int distance;

    public Joueur(int id, String pseudo, int score) {
        this.id = id;
        this.pseudo = pseudo;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
