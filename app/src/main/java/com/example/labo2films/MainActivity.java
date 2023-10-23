package com.example.labo2films;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Film> listeFilms;
    private String fichier = "films.txt";
    BufferedWriter ficSortie=null;
    BufferedReader ficEntree=null;
    Context context=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            creationListe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //gestionEvents();
        //lister();
    }

    private void isInInterne() throws IOException{
        String[] listeFic=context.fileList();
        boolean trouver = false;
        for(int i=0;i<listeFic.length;i++){
            if (listeFic[i].toString().equals(fichier)){
                trouver = true;
            }
        }
        if (trouver){
            ficEntree = new BufferedReader(
                    new InputStreamReader(openFileInput(fichier)));
        }
        else {
            ficEntree = new BufferedReader(
                    new InputStreamReader(getAssets().open(fichier)));
        }
    }
    private void creationListe() throws IOException {
        listeFilms = new ArrayList<Film>();
        isInInterne();

        String ligne = ficEntree.readLine();
        StringTokenizer strtok;
        while (ligne!=null){
            strtok = new StringTokenizer(ligne,";");
            listeFilms.add(new Film(parseInt(strtok.nextToken()),strtok.nextToken(),parseInt(strtok.nextToken()),strtok.nextToken(),parseInt(strtok.nextToken())));
            ligne = ficEntree.readLine(); }
        ficEntree.close();
    }
    private void gestionEvents(){
       Button lister = findViewById(R.id.lister);
       Button categorie = findViewById(R.id.categorie);
       //Button ajouter = findViewById(R.id.ajouter);
        // lister.setOnClickListener(new EventListener());
    }

    private void ajouter(){

    }
    private void lister(){

    }
    private void listerCatégorie(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            enregistrer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void enregistrer() throws IOException{
        String eol = System.getProperty("line.separator");
        ficSortie = new BufferedWriter(
                new OutputStreamWriter(openFileOutput(fichier,
                        MainActivity.MODE_PRIVATE)));
        for (Film unFilm : listeFilms){
            ficSortie.write(unFilm.getNum()+";"+unFilm.getTitre()+";"+unFilm.getCodeCateg()+";"+unFilm.getLangue()+";"+unFilm.getCote()+";"+eol);
        }

        ficSortie.close();
        Toast.makeText(MainActivity.this,"Films bien enregistré", Toast.LENGTH_SHORT).show();
    }

}