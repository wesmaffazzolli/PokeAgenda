package br.com.androidpro.pokeagenda;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Wesley on 17/06/2018.
 */

public class PokemonList {
    @JsonProperty("pokemons")
    private List<Pokemon> pokemons = null;

    @JsonProperty("pokemons")
    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    @JsonProperty("pokemons")
    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
