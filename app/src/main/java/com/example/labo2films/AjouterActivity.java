package com.example.labo2films;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AjouterActivity extends AppCompatActivity {
    ArrayList<Film> listeFilms;
    String[] choix_categ =new String[]{"Choisir une catégorie","1","2","3","4","5"};
    String[] choix_langue=new String[]{"Choisir une langue","FR","AN"};
    String[] choix_cote=new String[]{"Choisir une cote","1","2","3","4","5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        chargerDonnees();
        remplirSpinner();
        gestionEvent();
    }
    private void chargerDonnees(){
        Bundle donnees = getIntent().getExtras();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            listeFilms = donnees.getParcelableArrayList("listeFilms",Film.class);
        }
    }
    private  void  remplirSpinner(){
        Spinner spinner_categ = (Spinner) findViewById(R.id.spinner_categorie);
        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, choix_categ);
        adapter_categorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categ.setAdapter(adapter_categorie);

        Spinner spinner_langue = (Spinner) findViewById(R.id.spinner_langue);
        ArrayAdapter<String> adapter_langue = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, choix_langue);
        adapter_langue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_langue.setAdapter(adapter_langue);

        Spinner spinner_cote = (Spinner) findViewById(R.id.spinner_cote);
        ArrayAdapter<String> adapter_cote = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, choix_cote);
        adapter_cote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cote.setAdapter(adapter_cote);
    }
    private void gestionEvent(){
        Button retour = findViewById(R.id.retour_ajouter);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("listeFilms", listeFilms);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        Button btn_ajouter = findViewById(R.id.ajouter);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ajouter();
            }
        });

    }

    private void ajouter(){

        int num, categ, cote;
        String titre, langue;

        TextView vw_num,vw_titre;
        Spinner vw_categ,vw_langue,vw_cote;
        vw_num = findViewById(R.id.num_ajouter);
        vw_titre = findViewById(R.id.titre_ajouter);
        vw_categ = findViewById(R.id.spinner_categorie);
        vw_langue = findViewById(R.id.spinner_langue);
        vw_cote = findViewById(R.id.spinner_cote);

        num = Integer.parseInt(vw_num.getText().toString());
        titre = vw_titre.getText().toString();
        categ = Integer.parseInt(vw_categ.getSelectedItem().toString());
        langue = vw_langue.getSelectedItem().toString();
        cote = Integer.parseInt(vw_cote.getSelectedItem().toString());

        listeFilms.add(new Film(num, titre, categ, langue, cote));

    }
}