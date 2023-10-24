package com.example.labo2films;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListerParCategorieActivity extends AppCompatActivity {
    ArrayList<Film>listeFilm;
    int categorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_par_categorie);
        gestionEvents();
        chargerDonnes();
        afficherCategorie();
        afficherFilms();
    }

    private void gestionEvents(){

        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int nbF=0 , nbA = 0;
                for (Film unFilm : listeFilm) {
                    switch (unFilm.getLangue()) {
                        case "FR":
                            nbF++;
                            break;
                        case "AN":
                            nbA++;
                            break;
                    }
                }
                Intent result = new Intent();
                result.putExtra("nbFrancais", nbF);
                result.putExtra("nbAnglais", nbA);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
    private void chargerDonnes(){
        Bundle donnees = getIntent().getExtras();
        categorie = donnees.getInt("categorie");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            listeFilm = donnees.getParcelableArrayList("listeFilms",Film.class);
        }
    }
    private void afficherCategorie(){
        /*
        TextView categorie = findViewById(R.id.categorieChoisit);
        categorie.setText("La catégorie choisit est "+categorie);
        */
    }
    private void afficherFilms(){
        ArrayList<Film> newList=new ArrayList<Film>();
        for (Film unFilm : listeFilm){
            if (unFilm.getCodeCateg() == categorie){
                newList.add(unFilm);
            }
        }
        //afficher les films dans la vue
    }
}