package br.com.androidpro.pokeagenda;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import io.realm.Realm;

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
        Realm realm = Realm.getDefaultInstance();
        Pokemon imgPoke = realm.where(Pokemon.class).equalTo("idPokemon", pokemons.get(position).getIdPokemon()).findFirst();
        if (imgPoke != null) {
            byte[] outImage = imgPoke.getFoto();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(imageBitmap);
        } else {
            imageView.setImageResource(R.drawable.coca);
        }
        realm.close();
        return rowView;
    }
}
