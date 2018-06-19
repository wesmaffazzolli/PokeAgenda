package br.com.androidpro.pokeagenda;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExibirPokemon extends AppCompatActivity {

    TextView nome, especie, altura, peso, treinador;
    ImageView imagem;
    AlertDialog alerta;
    private int idTreinador, idPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_pokemon);

        //Código do botão voltar do Android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nome = (TextView) findViewById(R.id.exibeNomeTextView);
        especie = (TextView) findViewById(R.id.exibeEspecieTextView);
        altura = (TextView) findViewById(R.id.exibeAlturaTextView);
        peso = (TextView) findViewById(R.id.exibePesoTextView);
        treinador = (TextView) findViewById(R.id.exibeTreinadorTextView);
        imagem = (ImageView) findViewById(R.id.exibeImageViewPokemon);
        Intent myIntent = getIntent();
        byte[] imgPoke = myIntent.getByteArrayExtra("imgPoke");
        if (imgPoke.length != 0) {
            byte[] outImage = imgPoke;
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
            imagem.setImageBitmap(imageBitmap);
        }
        nome.setText(myIntent.getStringExtra("nomePokemon"));
        especie.setText(myIntent.getStringExtra("especie"));
        altura.setText(String.valueOf(myIntent.getDoubleExtra("altura", 0)) + " m");
        peso.setText(String.valueOf(myIntent.getDoubleExtra("peso", 0)) + " kg");
        treinador.setText(myIntent.getStringExtra("nomeTreinador"));
        idTreinador = myIntent.getIntExtra("idTreinador", 0);
        idPokemon = myIntent.getIntExtra("idPokemon", 0);
    }

    protected void favoritar(View view) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ExibirPokemon.this);
        progressDialog.setMessage("Carregando....");
        progressDialog.setTitle("Aguarde");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<Void> call = new RetrofitConfig().getPokeAgendaAPI().updateFavorito(idTreinador, idPokemon);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<String> call2 = new RetrofitConfig().getPokeAgendaAPI().getNomeTreinador(idTreinador);
                call2.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String nomeT = response.body();
                        chamaActivity(NavigationActivity.class, nomeT);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("PokeAgendaAPI   ", "Erro ao buscar o nome do treinador: " + t.getMessage());
                    }
                });
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("PokeAgendaAPI   ", "Erro ao favoritar pokemon: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
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

    //Código do botão voltar do Android
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void chamaActivity(Class cls, String nomeTreinador) {
        Intent it = new Intent(this, cls);
        it.putExtra("idTreinador", idTreinador);
        it.putExtra("nomeTreinador", nomeTreinador);
        it.putExtra("nomeFavorito", nome.getText().toString());
        it.putExtra("idPokemonFav", idPokemon);
        startActivity(it);
    }
}
