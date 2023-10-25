package com.example.labo2films;

import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Film> listeFilms;
    private String fichier = "films.txt";
    private int vielleHauteur;
    private BufferedWriter ficSortie=null;
    private BufferedReader ficEntree=null;
    private Context context=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            creationListe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        afficherCategorie();
        gestionEvents();

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
       Spinner spinner_categ= findViewById(R.id.spinnerCategorie);
       //Button ajouter = findViewById(R.id.ajouter);
        //Button supprimer = findViewById(R.id.supprimer);
        lister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showSpinner
            }
        });

        categorie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                afficherSpinnerCatégorie();
            }
        });
        spinner_categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    String categSelctionner = spinner_categ.getSelectedItem().toString();
                    listerCatégorie(categSelctionner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*
        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ajouter();
            }
        });
        */
        /*
        supprimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                supprimer();
            }
        });
        */

    }

    private void ajouter(){
        /*
        int num, categ, cote;
        String titre, langue;

        TextView vw_num,vw_titre,vw_categ,vw_langue,vw_cote;
        vw_num = findViewById(R.id.num);
        vw_titre = findViewById(R.id.titre);
        vw_categ = findViewById(R.id.categ);
        vw_langue = findViewById(R.id.langue);
        vw_cote = findViewById(R.id.cote);

        num = vw_num.getText();
        titre = vw_titre.getText();
        categ = vw_categ.getText();
        langue = vw_langue.getText();
        cote = vw_cote.getText();

        listeFilms.add(new Film(num, titre, categ, langue, cote));
        */
    }
    private void supprimer(int id){
        //TextView aSupprimer = findViewById(R.id.aSupprimer);
        //id = parseInt(aSupprimer.getText()+"");
        int trouver=-1;
        for (Film unFilm : listeFilms){
            if (unFilm.getNum() == id) {
                trouver =  unFilm.getNum();
            }
        }
        listeFilms.remove(trouver);
    }

    private void afficherCategorie(){
        String[] listeCategorie= new String []{"Choisir une catégorie","1","2","3","4","5"};
        Spinner s = (Spinner) findViewById(R.id.spinnerCategorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listeCategorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    private void lister(){

    }
    private void afficherSpinnerCatégorie(){
        Spinner spinner_categorie = findViewById(R.id.spinnerCategorie);
        if (spinner_categorie.getVisibility()==View.INVISIBLE){
            spinner_categorie.setVisibility(View.VISIBLE);
        }
        else {
            spinner_categorie.setVisibility(View.INVISIBLE);

        }
    }
    private void listerCatégorie(String categorire){
        Intent intent = new Intent(MainActivity.this, ListerParCategorieActivity.class);
        intent.putExtra("listeFilms", listeFilms);
        intent.putExtra("categorie", categorire);
        ListerParCategorieActivityResultLaucher.launch(intent);
    }

    ActivityResultLauncher<Intent> ListerParCategorieActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent donnees = result.getData();
                        String taille = donnees.getStringExtra("nbFrancais");
                        // On affiche la donnée envoyé par activité 2
                        Toast.makeText(MainActivity.this, taille,Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Problème avec activité 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

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