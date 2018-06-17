package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultarPokemons extends AppCompatActivity {

    ListView list;
    String[] pokemon = {};
    String[] especie = {};
    Integer[] imageId = {R.drawable.coca, R.drawable.fanta};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Call<ArrayList<Pokemon>> call = new RetrofitConfig().getPokeAgendaAPI().getPokemons();
        call.enqueue(new Callback<ArrayList<Pokemon>>() {
            @Override
            public void onResponse(Call<ArrayList<Pokemon>> call, Response<ArrayList<Pokemon>> response) {
                ArrayList<Pokemon> pokemons = response.body();
                ListCell adapter = new ListCell(ConsultarPokemons.this, pokemons);
                list = (ListView) findViewById(R.id.list);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
                        //Toast.makeText(ConsultarPokemons.this, "Clicou: " + arg2, Toast.LENGTH_SHORT).show();
                        Call<Pokemon> call = new RetrofitConfig().getPokeAgendaAPI().getPokemon(arg2+1);
                        call.enqueue(new Callback<Pokemon>() {
                            @Override
                            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                chamaActivity(ExibirPokemon.class);
                            }

                            @Override
                            public void onFailure(Call<Pokemon> call, Throwable t) {
                                Log.e("PokeAgendaAPI   ", "Erro ao buscar detalhes do pokemon: " + t.getMessage());
                            }
                        });

                    }
                });
                Log.e("PokeAgendaAPI   ", "Sucesso ao buscar a lista de pokemons: " + response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Pokemon>> call, Throwable t) {
                Log.e("PokeAgendaAPI   ", "Erro ao buscar a lista de pokemons: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void chamaActivity(Class cls) {
        Intent it = new Intent(this, cls);
        startActivity(it);
    }
}
