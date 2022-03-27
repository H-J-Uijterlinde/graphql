package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.enums.PokemonType;
import nl.quintor.pokedex.repositories.PokemonRepository;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QueryResolvers {
    private final PokemonRepository pokemonRepository;
    private final SpeciesRepository speciesRepository;

    public DataFetcher<List<Pokemon>> getAllPokemon() {
        return dataFetchingEnvironment -> pokemonRepository.findAll();
    }

    public DataFetcher<List<Species>> speciesByType() {
        return dataFetchingEnvironment -> {
            PokemonType pokemonType = PokemonType.fromString(dataFetchingEnvironment.getArgument("type"));
            return speciesRepository.findByType(pokemonType);
        };
    }
}
