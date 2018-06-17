package br.com.androidpro.pokeagenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExibirPokemon extends AppCompatActivity {

    TextView nome, especie, altura, peso, treinador;
    ImageView imagem;
    AlertDialog alerta;

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
        nome.setText(myIntent.getStringExtra("nomePokemon"));
        especie.setText(myIntent.getStringExtra("especie"));
        altura.setText(String.valueOf(myIntent.getDoubleExtra("altura", 0)) + " m");
        peso.setText(String.valueOf(myIntent.getDoubleExtra("peso", 0)) + " kg");
        treinador.setText(myIntent.getStringExtra("nomeTreinador"));

    }

    protected void favoritar(View view) {
        instaciaDialog("Sucesso", "O Pokemon foi favoritado!");
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
}
