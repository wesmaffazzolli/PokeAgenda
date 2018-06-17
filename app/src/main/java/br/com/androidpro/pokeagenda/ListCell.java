package br.com.androidpro.pokeagenda;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListCell extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] pokemon;
    private final String[] especie;
    private final Integer[] imageId;

    public ListCell(Activity context, String[] pokemon, String[] especie, Integer[] imageId) {
        super(context, R.layout.activity_list_cell, pokemon);
        this.context = context;
        this.especie = especie;
        this.pokemon = pokemon;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_list_cell, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtEspecie = (TextView) rowView.findViewById(R.id.especie);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(pokemon[position]);
        txtEspecie.setText(especie[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }

    /*private final ArrayList<Pokemon> pokemons;

    public ListCell(Activity context, ArrayList<Pokemon> pokemons) {
        super(context, R.layout.activity_list_cell, pokemons);
        this.context = context;
        this.pokemons = pokemons;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_list_cell, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtEspecie = (TextView) rowView.findViewById(R.id.especie);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(pokemons.get(position).getNomePokemon());
        //txtEspecie.setText(especie[position]);
        //imageView.setImageResource(imageId[position]);
        return rowView;
    }*/

}
