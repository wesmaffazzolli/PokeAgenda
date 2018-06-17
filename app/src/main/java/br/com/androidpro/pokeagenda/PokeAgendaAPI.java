package br.com.androidpro.pokeagenda;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeAgendaAPI {

    @GET("autenticar/{login}/{senha}")
    Call<Treinador> autenticar(@Path("login") String login, @Path("senha") String senha);

    @GET("all")
    Call<ArrayList<Pokemon>> getPokemons();

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    @GET("nometreinador/{id}")
    Call<String> getNomeTreinador(@Path("id") int id);
}
