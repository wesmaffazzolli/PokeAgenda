package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PesquisarPokemon extends AppCompatActivity {

    EditText pesquisa;

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

            if(pesquisa.getText().toString().equals("teste")) {
                //Encaminha para activity
                chamaActivity(ExibirPokemon.class);
            }

        } else {
            Toast.makeText(this, "Preencha o campo pesquisa!", Toast.LENGTH_LONG).show();
        }
    }

    public void chamaActivity(Class cls) {
        Intent it = new Intent(this, cls);
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
}
