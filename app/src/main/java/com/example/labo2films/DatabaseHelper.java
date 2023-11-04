package com.example.labo2films;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public Context context;
    private static final String DATABASE_FILMS="bdfilms";
    private static final int DB_VERSION = 1;

    private static final String TABLE_FILMS = "films";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_TITRE = "titre";
    private static final String COLUMN_NUMCATEG = "codecateg";
    private static final String COLUMN_LANGUE = "langue";
    private static final String COLUMN_COTE = "cote";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_CODECATEG = "idcateg";
    private static final String COLUMN_NOMCATEG = "nomcateg";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_FILMS, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Création table catégories
        String tablecategories = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES +
                " (" + COLUMN_CODECATEG + " INTEGER PRIMARY KEY, " +
                COLUMN_NOMCATEG + " TEXT);";
        db.execSQL(tablecategories);


        //Création table films
        String tablefilms = "CREATE TABLE IF NOT EXISTS " + TABLE_FILMS + "(" +
                COLUMN_CODE + " INTEGER PRIMARY KEY," +
                COLUMN_TITRE + " TEXT," +
                COLUMN_NUMCATEG + " INTEGER," +
                COLUMN_LANGUE + " TEXT," +
                COLUMN_COTE + " INTEGER," +
                "FOREIGN KEY (" + COLUMN_NUMCATEG +") REFERENCES " + TABLE_CATEGORIES+ " ("+ COLUMN_CODECATEG +"))";
        db.execSQL(tablefilms);

        //Inserer données pour table catégories

        db.execSQL("INSERT INTO " + TABLE_CATEGORIES+ " ('idcateg', 'nomcateg') VALUES (2, 'animation');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES+ " ('idcateg', 'nomcateg') VALUES (3, 'Thriller horrifique');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES+ " ('idcateg', 'nomcateg') VALUES (4, 'Drame');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES+ " ('idcateg', 'nomcateg') VALUES (5, 'Thriller');");
        db.execSQL("INSERT INTO " + TABLE_CATEGORIES+ " ('idcateg', 'nomcateg') VALUES (1, 'Comédie');");

        //Initialisation de la table films

        db.execSQL("INSERT INTO " + TABLE_FILMS+ " ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1125, 'Le dernier empereur', 1, 'FR', 4);");
        db.execSQL("INSERT INTO " + TABLE_FILMS+ " ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1279, 'Ére de glace', 2, 'FR', 5);");
        db.execSQL("INSERT INTO " + TABLE_FILMS+ " ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1486, 'ET', 2, 'AN', 5);");

        ContentValues values = new ContentValues();
        values.put("code", "1487");
        values.put("titre", "Maman j'ai raté l'avion");
        values.put("codecateg", "2");
        values.put("langue", "FR");
        values.put("cote", "5");
        db.insert("films", null, values);

        db.execSQL("INSERT INTO " + TABLE_FILMS+ " ('code', 'titre', 'codecateg', 'langue', 'cote') VALUES (1979, 'Le sixième sens', 3, 'FR', 5);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FILMS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
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

        values.put(COLUMN_CODE, film.getNum());
        values.put(COLUMN_TITRE, film.getTitre());
        values.put(COLUMN_NUMCATEG,film.getCodeCateg());
        values.put(COLUMN_LANGUE, film.getLangue());
        values.put(COLUMN_COTE, film.getCote());

        long result = db.insert(TABLE_FILMS, null, values);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

}
