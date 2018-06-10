package br.com.androidpro.pokeagenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ExibirPokemon extends AppCompatActivity {

    TextView nome, especie, altura, peso;
    ImageView imagem;

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
        imagem = (ImageView) findViewById(R.id.exibeImageViewPokemon);

        nome.setText("Pokemon Teste");
        especie.setText("Fire on Babilon");
        altura.setText("180cm");
        peso.setText("8.5kg");

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
