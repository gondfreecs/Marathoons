package com.example.samirtalbi.maratoons;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samir Talbi on 01/12/2017.
 */

public class DBManager {
    private SQLiteDatabase database;
    private Mybd dbHelper;
    private String[] allColumns = {Mybd.COLUMN_ID, Mybd.COLUMN_TEMPS, Mybd.COLUMN_DATE, Mybd.COLUMN_GAGNANT};
    private String[] allColumns2 = {Mybd.COLUMN_ID, Mybd.COLUMN_NAME, Mybd.COLUMN_SCORE};

    public DBManager(Context context) {
        dbHelper = new Mybd(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean createPartie(String temps, String date, Joueur gagnant, String listejoueur) {

       database.execSQL("INSERT INTO "+ Mybd.ACCOUNT_TABLE+" ("+Mybd.COLUMN_TEMPS+","+ Mybd.COLUMN_DATE +","+ Mybd.COLUMN_GAGNANT +","
               + Mybd.COLUMN_LISTEJOUEUR +") VALUES ('" + temps + "', '" + date + "', '"+ gagnant.getPseudo() +"' , '"+ listejoueur +"');");

        return true;
    }

    public boolean createJoueur(Joueur a) {

        database.execSQL("INSERT INTO "+ Mybd.ACCOUNT_TABLE2+" ("+Mybd.COLUMN_NAME+","+ Mybd.COLUMN_SCORE +") VALUES ('" + a.getPseudo() + "', '" + a.getScore() +"');");

        return true;
    }

    /*
    public void deleteAccount(Partie account) {
        database.execSQL("DELETE FROM Accounts WHERE id="+account.getId()+";");
        System.out.println(account.getPseudo()+" à était suprimmé !");
    }*/

    public List<Joueur> getAllAccounts() {
        List<Joueur> joueurs = new ArrayList<Joueur>();
        Cursor cursor = database.query(Mybd.ACCOUNT_TABLE2,allColumns2, null, null, null, null, Mybd.COLUMN_SCORE+"");
        cursor.moveToFirst();
        joueurs.add(new Joueur(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)) ,cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME)), cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_SCORE))));
        while (cursor.moveToNext()){
            joueurs.add(new Joueur(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)) ,cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME)), cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_SCORE))));
            System.out.println("nom: "+ cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME))+", Score : "+ cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_SCORE)));
        }
        return joueurs;
    }

    private Joueur cursorToAccount(Cursor cursor) {
        //TODO
        return null;
    }
}
