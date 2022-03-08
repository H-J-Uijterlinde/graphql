package nl.quintor.pokedex.repositories;

import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByName(String name);
    @Query("select p.trainer from Pokemon p where p = :pokemon")
    Optional<Trainer> findByPokemon(Pokemon pokemon);
}
