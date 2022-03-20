package nl.quintor.pokedex.datafetchers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.types.Pokemon;
import nl.quintor.pokedex.model.types.Species;
import nl.quintor.pokedex.repositories.PokemonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SpeciesDataFetchers {
    private final PokemonRepository pokemonRepository;

    public DataFetcher<List<Pokemon>> getPokemonBySpecies() {
        return dataFetchingEnvironment -> {
            Species species = dataFetchingEnvironment.getSource();
            var result = pokemonRepository.findAllBySpecies(species);
            return result;
        };
    }
}
