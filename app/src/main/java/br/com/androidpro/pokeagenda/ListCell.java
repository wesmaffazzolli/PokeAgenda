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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListCell extends ArrayAdapter<Pokemon> {

    private Activity context;
    private ArrayList<Pokemon> pokemons;



    public ListCell(Activity context, ArrayList<Pokemon> listaPoke) {
        super(context, R.layout.activity_list_cell, listaPoke);
        this.context = context;
        this.pokemons = listaPoke;
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
        txtEspecie.setText(pokemons.get(position).getEspecie());
        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
