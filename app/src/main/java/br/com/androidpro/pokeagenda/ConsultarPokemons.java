package br.com.androidpro.pokeagenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarPokemons extends AppCompatActivity {

    ListView list;
    String[] pokemon = {"Coca", "Fanta"};
    ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
    String[] especie = {"Fogo", "Água"};
    Integer[] imageId = {R.drawable.coca, R.drawable.fanta};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Call<String> call = new RetrofitConfig().getPokeAgendaAPI().getPokemons();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("PokeAgendaAPI   ", "Sucesso ao buscar a lista de pokemons:" + response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("PokeAgendaAPI   ", "Erro ao buscar a lista de pokemons:" + t.getMessage());
            }
        });

        ListCell adapter = new ListCell(ConsultarPokemons.this, pokemon, especie, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
                Toast.makeText(ConsultarPokemons.this, "Clicou na " + pokemon[+arg2], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
