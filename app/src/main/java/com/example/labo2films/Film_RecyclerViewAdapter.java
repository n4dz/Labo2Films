package com.example.labo2films;;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Film_RecyclerViewAdapter extends RecyclerView.Adapter<Film_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Film> listefilms;
    public  Film_RecyclerViewAdapter(Context context, ArrayList<Film> listefilms){
        this.context=context;
        this.listefilms = listefilms;
    }
    @NonNull
    @Override
    public Film_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((context));
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new Film_RecyclerViewAdapter.MyViewHolder(view);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull Film_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.vw_num.setText(listefilms.get(position).getNum()+"");
        holder.vw_titre.setText(listefilms.get(position).getTitre());
        holder.vw_categorie.setText(listefilms.get(position).getCodeCateg()+"");
        holder.vw_langue.setText(listefilms.get(position).getLangue());
        holder.vw_cote.setText(listefilms.get(position).getCote()+"");
        String pochette = listefilms.get(position).getPochette();
        if (pochette.indexOf(0) =='c'){
            holder.vw_image.setImageURI(Uri.parse(pochette));
        }
        else if(!pochette.equals("Pochette")){
            holder.vw_image.setImageResource(Integer.parseInt(pochette));
        }
        else{
            holder.vw_image.setImageResource(2131230856);
        }
    }

    @Override
    public int getItemCount() {
        return listefilms.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView vw_num,vw_titre,vw_categorie,vw_langue,vw_cote;
        ImageView vw_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vw_num = itemView.findViewById(R.id.num);
            vw_titre = itemView.findViewById(R.id.titre);
            vw_categorie = itemView.findViewById(R.id.categorie);
            vw_langue = itemView.findViewById(R.id.langue);
            vw_cote = itemView.findViewById(R.id.cote);
            vw_image = itemView.findViewById(R.id.image);
        }
    }
}
