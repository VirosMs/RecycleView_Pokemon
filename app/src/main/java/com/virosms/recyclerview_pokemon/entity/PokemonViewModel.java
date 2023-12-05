package com.virosms.recyclerview_pokemon.entity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PokemonViewModel extends AndroidViewModel {

    PokemonRepositorio pokemonRepositorio;

    MutableLiveData<List<Pokemon>> listElementosMutableLiveData = new MutableLiveData<>();

    MutableLiveData<Pokemon> elementoSeleccionado = new MutableLiveData<>();



    public PokemonViewModel(@NonNull Application application) {
        super(application);

        pokemonRepositorio = new PokemonRepositorio();

        listElementosMutableLiveData.setValue(pokemonRepositorio.obtener());
    }

    public MutableLiveData<List<Pokemon>> obtener(){
        return listElementosMutableLiveData;
    }

    public void insertar(Pokemon pokemon){
        pokemonRepositorio.insertar(pokemon, new PokemonRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pokemon> pokemons) {
                listElementosMutableLiveData.setValue(pokemons);
            }
        });
    }

    public void eliminar(Pokemon pokemon){
        pokemonRepositorio.eliminar(pokemon, new PokemonRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pokemon> pokemons) {
                listElementosMutableLiveData.setValue(pokemons);
            }
        });
    }

    public void actualizar(Pokemon pokemon, float valoracion){
        pokemonRepositorio.actualizar(pokemon, valoracion, new PokemonRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pokemon> pokemons) {
                listElementosMutableLiveData.setValue(pokemons);
            }
        });
    }

    public void seleccionar(Pokemon pokemon){
        elementoSeleccionado.setValue(pokemon);
    }

    public MutableLiveData<Pokemon> seleccionado(){
        return elementoSeleccionado;
    }
}
