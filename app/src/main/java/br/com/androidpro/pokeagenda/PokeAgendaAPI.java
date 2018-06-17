package br.com.androidpro.pokeagenda;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PokeAgendaAPI {

    @GET("autenticar/{login}/{senha}")
    Call<Treinador> autenticar(@Path("login") String login, @Path("senha") String senha);

    @GET
    Call<ArrayList<Pokemon>> getPokemons();
}
