package com.example.labo2films;

import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        remplirCategorie();
        gestionEvents();

        lister();
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
       Button categorie = findViewById(R.id.btn_categorie);
       Spinner spinner_categ= findViewById(R.id.spinnerCategorie);
       //Button ajouter = findViewById(R.id.ajouter);
        Button total = findViewById(R.id.total);
        // Button supprimer = findViewById(R.id.supprimer);
        Button supprimer = findViewById(R.id.a_supprimer);
        lister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lister();
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

        supprimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                supprimer();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ajouter();
            }
        });

    }
    private void ajouter(){
        Intent intent = new Intent(MainActivity.this, AjouterActivity.class);
        intent.putExtra("listeFilms", listeFilms);
        AjouterActivityResultLaucher.launch(intent);
    }

    ActivityResultLauncher<Intent> AjouterActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent donnees = result.getData();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            listeFilms = donnees.getParcelableArrayListExtra("listeFilms",Film.class);
                        }
                        lister();
                    }else {
                        Toast.makeText(MainActivity.this, "Problème avec activité 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void afficherSpinnerSupprimer(){
        LinearLayout l = findViewById(R.id.layout_supprimer);
        if (l.getVisibility()==View.GONE){
            l.setVisibility(View.VISIBLE);
        }
        else {
            l.setVisibility(View.GONE);
        }
    }
    private void supprimer(){
        int id=0;
        EditText spinner_num = findViewById(R.id.NumSupp);
        id = parseInt(spinner_num.getText().toString());
        Film trouver=null;
        for (Film unFilm : listeFilms){
            if (unFilm.getNum() == id) {
                trouver =  unFilm;
            }
        }
        if (trouver!= null){
            listeFilms.remove(trouver);
            Toast.makeText(MainActivity.this, "Film supprimer",Toast.LENGTH_SHORT).show();
            lister();
        }
        else{
            Toast.makeText(MainActivity.this, "Cet id n'existe pas",Toast.LENGTH_SHORT).show();
        }
        LinearLayout l = findViewById(R.id.layout_supprimer);
        l.setVisibility(View.GONE);
    }

    private void remplirCategorie(){
        String[] listeCategorie= new String []{"Choisir une catégorie","1","2","3","4","5"};
        Spinner s = (Spinner) findViewById(R.id.spinnerCategorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listeCategorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        afficherSpinnerCatégorie();
    }

    private void lister(){
        RecyclerView recyclerView = findViewById(R.id.listeFilm);
        Film_RecyclerViewAdapter adapter = new Film_RecyclerViewAdapter(this,listeFilms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void afficherSpinnerCatégorie(){

        Spinner spinner_categorie = findViewById(R.id.spinnerCategorie);
        if (spinner_categorie.getVisibility()==View.GONE){
            spinner_categorie.setVisibility(View.VISIBLE);
        }
        else {
            spinner_categorie.setVisibility(View.GONE);

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
                        String nbF = donnees.getStringExtra("nbFrancais");
                        String nbA = donnees.getStringExtra("nbAnglais");
                        // On affiche la donnée envoyé par activité 2
                        TextView textNombre = findViewById(R.id.resultat);
                        textNombre.setText("Nombre de film en français : "+nbF+"\nNombre de film en Anglais : "+nbA);
                        textNombre.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(MainActivity.this, "Problème avec activité 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onStop() {
        super.onStop();
        try {
            enregistrer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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