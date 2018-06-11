package br.com.androidpro.pokeagenda;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokeAgendaAPI {
    @GET
    Call<Pokemon> getPokemons();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.182:8080/SistemaCentral/webresources/pokeagenda/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
