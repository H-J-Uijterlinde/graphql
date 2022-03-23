package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.PokemonRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QueryResolvers {
    private final TrainerRepository trainerRepository;
    private final PokemonRepository pokemonRepository;

    public DataFetcher<List<Pokemon>> getAllPokemon() {
        return dataFetchingEnvironment -> pokemonRepository.findAll();
    }

    public DataFetcher<List<Trainer>> getAllTrainers() {
        return dataFetchingEnvironment -> trainerRepository.findAll();
    }

    public DataFetcher<Trainer> getTrainerByName() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return trainerRepository.findByName(name).orElse(null);
        };
    }


}
