package nl.quintor.pokedex.repositories;

import nl.quintor.pokedex.model.types.Pokemon;
import nl.quintor.pokedex.model.types.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    @Query("select p.species from Pokemon p where p = :pokemon")
    Optional<Species> findByPokemon(Pokemon pokemon);
}
