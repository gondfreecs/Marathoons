package com.example.samirtalbi.maratoons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Samir Talbi on 01/12/2017.
 */

public class Mybd extends SQLiteOpenHelper {

    //Table
    public static final String ACCOUNT_TABLE = "Partie";
    //column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEMPS = "temps";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_GAGNANT = "gagnant";
    public static final String COLUMN_LISTEJOUEUR = "listejoueur";

    //Table
    public static final String ACCOUNT_TABLE2 = "Joueur";
    //column
    public static final String COLUMN_NAME = "pseudo";
    public static final String COLUMN_SCORE = "score";

    //Database info
    private static final String DATABASE_NAME = "partie.db";
    private static final int DATABASE_VERSION = 1;
    //Creation table request
    private static final String DATABASE_CREATE = "create table " + ACCOUNT_TABLE + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TEMPS + " text not null, " + COLUMN_DATE +" text not null, "+ COLUMN_GAGNANT+" text not null, "+ COLUMN_LISTEJOUEUR +" text not null); ";

    private static final String DATABASE_CREATE2 = "create table "+ ACCOUNT_TABLE2 +"("+COLUMN_ID+" integer primary key autoincrement, " + COLUMN_NAME +" text not null, " + COLUMN_SCORE + " text not null);";
    //Init of sqlhelper
    public Mybd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Method call on first call
    //@Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
        System.out.println("database created !");
    }
    //Method call when DATABASE_VERSION is different than actual
    //@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE +" ; " + " DROP TABLE IF EXISTS " + ACCOUNT_TABLE2 +" ;");
        onCreate(db);
    }
}