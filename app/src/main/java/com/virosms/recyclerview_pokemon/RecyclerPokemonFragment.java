package com.virosms.recyclerview_pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.virosms.recyclerview_pokemon.databinding.FragmentRecyclerPokemonBinding;
import com.virosms.recyclerview_pokemon.databinding.ViewholderPokemonBinding;
import com.virosms.recyclerview_pokemon.entity.Pokemon;
import com.virosms.recyclerview_pokemon.entity.PokemonViewModel;

import java.util.List;


public class RecyclerPokemonFragment extends Fragment {

    private FragmentRecyclerPokemonBinding binding;
    private PokemonViewModel pokemonViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRecyclerPokemonBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemonViewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevoElemento.setOnClickListener(v -> navController.navigate(
                R.id.action_recyclerElementosFragment_to_nuevoElementoFragment));

        ElementosAdapter elementosAdapter = new ElementosAdapter();
        binding.recyclerView.setAdapter(elementosAdapter);

        //obtener el array de elementos, y pasarlos al adapter
        pokemonViewModel.obtener().observe(getViewLifecycleOwner(),
                elementosAdapter::establecerLista);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Pokemon pokemon = elementosAdapter.obtenerElemento(position);
                pokemonViewModel.eliminar(pokemon);
            }
        }).attachToRecyclerView(binding.recyclerView);
    }


    static class PokemonViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderPokemonBinding binding;

        public PokemonViewHolder(ViewholderPokemonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ElementosAdapter extends RecyclerView.Adapter<PokemonViewHolder> {
        List<Pokemon> pokemons;

        @NonNull
        @Override
        public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PokemonViewHolder(ViewholderPokemonBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
            Pokemon pokemon = pokemons.get(position);

            holder.binding.nombre.setText(pokemon.nombre);
            holder.binding.valoracion.setRating(pokemon.valoracion);

            holder.binding.valoracion.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if (fromUser) pokemonViewModel.actualizar(pokemon, rating);
            });

            holder.itemView.setOnClickListener(v -> {
                pokemonViewModel.seleccionar(pokemon);
                navController.navigate(R.id.action_recyclerElementosFragment_to_mostrarElementoFragment);
            });
        }

        @Override
        public int getItemCount() {
            return pokemons != null ? pokemons.size() : 0;
        }

        public void establecerLista(List<Pokemon> pokemons) {
            this.pokemons = pokemons;
            notifyDataSetChanged();
        }

        public Pokemon obtenerElemento(int posicion) {
            return pokemons.get(posicion);
        }
    }
}