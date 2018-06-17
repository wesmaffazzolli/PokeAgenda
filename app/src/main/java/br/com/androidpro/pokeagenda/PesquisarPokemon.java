package br.com.androidpro.pokeagenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisarPokemon extends AppCompatActivity {

    EditText pesquisa;
    AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_pokemon);

        pesquisa = (EditText) findViewById(R.id.pesquisaEditText);

        //Código do botão voltar do Android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    protected void pesquisar(View view ) {
        if(!pesquisa.getText().toString().isEmpty()) {
            Call<Pokemon> call = new RetrofitConfig().getPokeAgendaAPI().searchPokemon(pesquisa.getText().toString());
            call.enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if(response.body() == null) {
                        instaciaDialog("Atenção: ", "Nenhum Pokemon encontrado!");
                    } else {
                        final Pokemon resultado = response.body();
                        Call<String> call2 = new RetrofitConfig().getPokeAgendaAPI().getNomeTreinador(resultado.getIdTreinador());
                        call2.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String nome = response.body();
                                chamaActivity(ExibirPokemon.class, resultado, nome);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("PokeAgendaAPI   ", "Erro ao buscar nome do treinador: " + t.getMessage());
                            }
                        });
                    }
                    pesquisa.setText("");
                }

                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {
                    Log.e("PokeAgendaAPI   ", "Erro ao pesquisar o pokemon: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Preencha o campo pesquisa!", Toast.LENGTH_LONG).show();
        }
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

    //Código do botão voltar do Android
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void instaciaDialog(String title, String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(title);
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
