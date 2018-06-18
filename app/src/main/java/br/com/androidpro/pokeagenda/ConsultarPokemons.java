package br.com.androidpro.pokeagenda;

import android.app.ProgressDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pokemons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ConsultarPokemons.this);
        progressDialog.setMessage("Carregando....");
        progressDialog.setTitle("Aguarde");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
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
                        progressDialog.show();
                        Call<Pokemon> call = new RetrofitConfig().getPokeAgendaAPI().getPokemon(arg2+1);
                        call.enqueue(new Callback<Pokemon>() {
                            @Override
                            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                final Pokemon pokemonzin = response.body();
                                Call<String> call2 = new RetrofitConfig().getPokeAgendaAPI().getNomeTreinador(pokemonzin.getIdTreinador());
                                call2.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        String nome = response.body();
                                        chamaActivity(ExibirPokemon.class, pokemonzin, nome);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.e("PokeAgendaAPI   ", "Erro ao buscar nome do treinador: " + t.getMessage());
                                    }
                                });
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Pokemon> call, Throwable t) {
                                Log.e("PokeAgendaAPI   ", "Erro ao buscar detalhes do pokemon: " + t.getMessage());
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
                Log.e("PokeAgendaAPI   ", "Sucesso ao buscar a lista de pokemons: " + response.message());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Pokemon>> call, Throwable t) {
                Log.e("PokeAgendaAPI   ", "Erro ao buscar a lista de pokemons: " + t.getMessage());
                progressDialog.dismiss();
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

    public void chamaActivity(Class cls, final Pokemon p, String nome) {
        Intent it = new Intent(this, cls);
        it.putExtra("nomePokemon", p.getNomePokemon());
        it.putExtra("especie", p.getEspecie());
        it.putExtra("altura", p.getAltura());
        it.putExtra("peso", p.getPeso());
        it.putExtra("nomeTreinador", nome);
        startActivity(it);
    }
}
