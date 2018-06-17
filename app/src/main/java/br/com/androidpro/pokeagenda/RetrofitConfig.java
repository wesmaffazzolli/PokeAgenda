package br.com.androidpro.pokeagenda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Wesley on 16/06/2018.
 */

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.182:8080/SistemaCentral/webresources/pokeagenda/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public PokeAgendaAPI getPokeAgendaAPI() {
        return this.retrofit.create(PokeAgendaAPI.class);
    }
}
