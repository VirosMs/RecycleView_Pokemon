package com.virosms.recyclerview_pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.virosms.recyclerview_pokemon.databinding.FragmentMostrarPokemonBinding;
import com.virosms.recyclerview_pokemon.entity.PokemonViewModel;


public class MostrarPokemonFragment extends Fragment {
    private FragmentMostrarPokemonBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarPokemonBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokemonViewModel pokemonViewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);

        pokemonViewModel.seleccionado().observe(getViewLifecycleOwner(), elemento -> {
            binding.nombre.setText(elemento.nombre);
            binding.descripcion.setText(elemento.descripcion);
            binding.valoracion.setRating(elemento.valoracion);

            binding.valoracion.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if(fromUser) pokemonViewModel.actualizar(elemento, rating);
            });
        });
    }
}