package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.repositories.PokemonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QueryResolvers {
    private final PokemonRepository pokemonRepository;

    public DataFetcher<List<Pokemon>> getAllPokemon() {
        return dataFetchingEnvironment -> pokemonRepository.findAll();
    }
}
