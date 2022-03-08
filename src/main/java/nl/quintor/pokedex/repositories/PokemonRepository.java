package nl.quintor.pokedex.repositories;

import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findAllByTrainer(Trainer trainer);
    List<Pokemon> findAllBySpecies(Species species);
}
