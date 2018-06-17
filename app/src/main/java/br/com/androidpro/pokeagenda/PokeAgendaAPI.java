package br.com.androidpro.pokeagenda;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeAgendaAPI {

    @GET("autenticar/{login}/{senha}")
    Call<Treinador> autenticar(@Path("login") String login, @Path("senha") String senha);

    @GET("/")
    Call<String> getPokemons();
}
