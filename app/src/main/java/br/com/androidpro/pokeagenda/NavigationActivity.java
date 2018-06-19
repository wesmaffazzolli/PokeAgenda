package br.com.androidpro.pokeagenda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

import io.realm.Realm;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int idTreinador;
    ImageView imgFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent();
        TextView nomeTreinador = findViewById(R.id.nomeTreinadorTextView);
        TextView nomeFavorito = findViewById(R.id.pokemonFavoritoTextView);
        nomeTreinador.setText(myIntent.getStringExtra("nomeTreinador"));
        nomeFavorito.setText("Pokemon Favorito: " + myIntent.getStringExtra("nomeFavorito"));
        imgFavorito = (ImageView) findViewById(R.id.imageView2);
        int idPokemonFav = myIntent.getIntExtra("idPokemonFav", 0);
        Realm realm = Realm.getDefaultInstance();
        Pokemon imgPoke = realm.where(Pokemon.class).equalTo("idPokemon", idPokemonFav).findFirst();
        if (imgPoke != null) {
            byte[] outImage = imgPoke.getFoto();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
            imgFavorito.setImageBitmap(imageBitmap);
        }
        realm.close();
        idTreinador = myIntent.getIntExtra("idTreinador", 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cadastrar_pokemon) {
            chamaActivity(CadastrarPokemon.class, idTreinador);
        } else if (id == R.id.nav_consultar_pokemon) {
            chamaActivity(ConsultarPokemons.class, idTreinador);
        } else if (id == R.id.nav_pesquisar_pokemon) {
            chamaActivity(PesquisarPokemon.class, idTreinador);
        } else if (id == R.id.nav_sair) {
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void chamaActivity(Class cls, int id) {
        Intent it = new Intent(this, cls);
        it.putExtra("idTreinador", id);
        startActivity(it);
    }
}
