package br.com.androidpro.pokeagenda;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("pokemonfavorito/{id}")
    Call<Pokemon> getPokemonFavorito(@Path("id") int id);

    @GET("search/{nome}")
    Call<Pokemon> searchPokemon(@Path("nome") String nome);

    @FormUrlEncoded
    @POST("novo")
    Call<Integer> insertPokemon(@Field("nome") String nome, @Field("especie") String especie,
                             @Field("peso") double peso, @Field("altura") double altura,
                             @Field("idTreinador") int idTreinador);

    @PUT("updatefav/{idTreinador}/{idPokemon}")
    Call<Void> updateFavorito(@Path("idTreinador") int idTreinador, @Path("idPokemon") int idPokemon);

}
