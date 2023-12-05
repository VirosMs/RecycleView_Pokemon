package com.virosms.recyclerview_pokemon.entity;

import java.util.ArrayList;
import java.util.List;

public class PokemonRepositorio {

    List<Pokemon> pokemons = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Pokemon> pokemons);
    }

    PokemonRepositorio(){
        pokemons.add(new Pokemon("Bulbasaur", "https://pokeapi.co/api/v2/pokemon-species/1/"));
        pokemons.add(new Pokemon("Charmander", "https://pokeapi.co/api/v2/pokemon-species/4/"));
        pokemons.add(new Pokemon("Squirtle", "https://pokeapi.co/api/v2/pokemon-species/7/"));
        pokemons.add(new Pokemon("Pidgey", "https://pokeapi.co/api/v2/pokemon-species/16/"));
    }

    List<Pokemon> obtener() {
        return pokemons;
    }

    void insertar(Pokemon pokemon, Callback callback){
        pokemons.add(pokemon);
        callback.cuandoFinalice(pokemons);
    }

    void eliminar(Pokemon pokemon, Callback callback) {
        pokemons.remove(pokemon);
        callback.cuandoFinalice(pokemons);
    }

    void actualizar(Pokemon pokemon, float valoracion, Callback callback) {
        pokemon.valoracion = valoracion;
        callback.cuandoFinalice(pokemons);
    }
}
