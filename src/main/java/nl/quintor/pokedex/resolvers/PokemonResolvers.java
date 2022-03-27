package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Component
public class PokemonResolvers {
    private final SpeciesRepository speciesRepository;

    public DataFetcher<Species> getSpeciesByPokemon() {
        return dataFetchingEnvironment -> {
            Pokemon pokemon = dataFetchingEnvironment.getSource();
            return speciesRepository.findByPokemon(pokemon)
                    .orElseThrow(() -> new EntityNotFoundException("Species not found for pokemon with id: " + pokemon.getId()));
        };
    }
}
