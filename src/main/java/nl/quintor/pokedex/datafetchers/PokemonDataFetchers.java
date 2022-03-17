package nl.quintor.pokedex.datafetchers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.publishers.PokemonPublisher;
import nl.quintor.pokedex.repositories.PokemonRepository;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class PokemonDataFetchers {
    private final TrainerRepository trainerRepository;
    private final SpeciesRepository speciesRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonPublisher pokemonPublisher;

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

    @Transactional
    public DataFetcher<Pokemon> createPokemon() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("pokemonName");
            String speciesType = dataFetchingEnvironment.getArgument("type");
            String trainerName = dataFetchingEnvironment.getArgument("trainerName");

            Species species = speciesRepository.findByType(speciesType).orElse(null);
            Trainer trainer = trainerRepository.findByName(trainerName).orElse(null);

            Pokemon pokemon = new Pokemon();
            pokemon.setName(name);
            pokemon.setSpecies(species);
            pokemon.setTrainer(trainer);

            pokemonRepository.save(pokemon);
            pokemonPublisher.publish(pokemon);

            return pokemon;
        };
    }
}
