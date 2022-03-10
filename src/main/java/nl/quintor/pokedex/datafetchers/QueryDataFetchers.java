package nl.quintor.pokedex.datafetchers;

import graphql.*;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.exceptions.TrainerNotFoundException;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.PokemonRepository;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QueryDataFetchers {
    private final TrainerRepository trainerRepository;
    private final SpeciesRepository speciesRepository;
    private final PokemonRepository pokemonRepository;

    public DataFetcher<Trainer> getTrainerByName() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            //throw new TrainerNotFoundException("Trainer niet gevonden.", 404);
            return trainerRepository.findByName(name).orElse(null);
        };
    }

    public DataFetcher<List<Trainer>> getAllTrainers() {
        return dataFetchingEnvironment -> trainerRepository.findAll();
    }

    public DataFetcher<List<Pokemon>> getAllPokemons() {
        return dataFetchingEnvironment -> pokemonRepository.findAll();
    }

    public DataFetcher<List<Species>> getAllSpecies() {
        return dataFetchingEnvironment -> speciesRepository.findAll();
    }

    public DataFetcher<List<Pokemon>> getPokemonsBySpecies() {
        return dataFetchingEnvironment -> {
            String speciesType = dataFetchingEnvironment.getArgument("type");
            Species species = speciesRepository.findByType(speciesType).orElse(null);
            return pokemonRepository.findAllBySpecies(species);
        };
    }
}
