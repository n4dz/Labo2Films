package com.example.labo2films;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Film> listeFilms;
    private String fichier = "films.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            creationListe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void creationListe() throws IOException {
        listeFilms = new ArrayList<Film>();

        InputStreamReader testReader = new InputStreamReader(getAssets().open(fichier));
        BufferedReader ficEntree = new BufferedReader(testReader);

        String ligne = ficEntree.readLine();
        StringTokenizer strtok;

        while (ligne != null){
            strtok = new StringTokenizer(ligne,";");
            listeFilms.add(new Film(parseInt(strtok.nextToken()),strtok.nextToken(),parseInt(strtok.nextToken()),strtok.nextToken(),parseInt(strtok.nextToken())));
            ligne = ficEntree.readLine();
        }
    }
    private void gestionEvents(){
       // Button lister = findViewById(R.id.lister);
    }
}