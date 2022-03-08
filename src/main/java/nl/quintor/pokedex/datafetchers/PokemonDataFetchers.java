package nl.quintor.pokedex.datafetchers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Component
public class PokemonDataFetchers {
    private final TrainerRepository trainerRepository;
    private final SpeciesRepository speciesRepository;

    public DataFetcher<Trainer> getTrainerByPokemon() {
        return dataFetchingEnvironment -> {
            Pokemon pokemon = dataFetchingEnvironment.getSource();
            return trainerRepository.findByPokemon(pokemon)
                    .orElseThrow(() -> new EntityNotFoundException("Trainer not found for pokemon with id: " + pokemon.getId()));
        };
    }

    public DataFetcher<Species> getSpeciesByPokemon() {
        return dataFetchingEnvironment -> {
            Pokemon pokemon = dataFetchingEnvironment.getSource();
            return speciesRepository.findByPokemon(pokemon)
                    .orElseThrow(() -> new EntityNotFoundException("Species not found for pokemon with id: " + pokemon.getId()));
        };
    }
}
