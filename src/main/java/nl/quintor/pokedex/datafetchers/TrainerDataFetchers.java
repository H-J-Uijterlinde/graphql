package nl.quintor.pokedex.datafetchers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.PokemonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TrainerDataFetchers {
    private final PokemonRepository pokemonRepository;

    public DataFetcher<List<Pokemon>> getPokemonByTrainer() {
        return dataFetchingEnvironment -> {
            Trainer trainer = dataFetchingEnvironment.getSource();
            return pokemonRepository.findAllByTrainer(trainer);
        };
    }
}
