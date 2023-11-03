package com.example.labo2films;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FILMS="bdfilms";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_FILMS, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Création table catégories
        String tablecategories = "CREATE TABLE IF NOT EXISTS categories (" +
                "code INTEGER PRIMARY KEY," +
                "nomcateg TEXT)";
        db.execSQL(tablecategories);

        //Inserer données
        db.execSQL("INSERT INTO categories ('code', 'nomcateg') VALUES (1, 'Comédie');");
        db.execSQL("INSERT INTO categories ('code', 'nomcateg') VALUES (2, 'animation');");
        db.execSQL("INSERT INTO categories ('code', 'nomcateg') VALUES (3, 'Thriller horrifique');");
        db.execSQL("INSERT INTO categories ('code', 'nomcateg') VALUES (4, 'Drame');");
        db.execSQL("INSERT INTO categories ('code', 'nomcateg') VALUES (5, 'Thriller');");


        //Création table films
        String tablefilms = "CREATE TABLE IF NOT EXISTS films (" +
                "code INTEGER PRIMARY KEY," +
                "titre TEXT," +
                "codecateg INTEGER," +
                "langue TEXT," +
                "cote INTEGER," +
                "FOREIGN KEY (codecateg) REFERENCES categories (code))";
        db.execSQL(tablefilms);


        //Initialisation de la table

        db.execSQL("INSERT INTO films ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1125, 'Le dernier empereur', 1, 'FR', 4);");
        db.execSQL("INSERT INTO films ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1279, 'Ére de glace', 2, 'FR', 5);");
        db.execSQL("INSERT INTO films ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1486, 'ET', 2, 'AN', 5);");

        ContentValues values = new ContentValues();
        values.put("code", "1487");
        values.put("titre", "Maman j'ai raté l'avion");
        values.put("codecateg", "2");
        values.put("langue", "FR");
        values.put("cote", "3");
        db.insert("films", null, values);

        db.execSQL("INSERT INTO films ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1487, 'Maman j'ai rate l'avion', 2, 'FR', 5);");
        db.execSQL("INSERT INTO films ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1979, 'Le sixième sens', 3, 'FR', 5);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bdfilms");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void ajouterFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("code", film.getNum());
        values.put("titre", film.getTitre());
        values.put("codecateg",film.getCodeCateg());
        values.put("langue", film.getLangue());
        values.put("cote", film.getCote());

        db.insert("films", null, values);
        db.close();

    }

}
