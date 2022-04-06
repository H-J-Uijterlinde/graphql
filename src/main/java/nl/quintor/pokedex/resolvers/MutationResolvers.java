package nl.quintor.pokedex.resolvers;

import graphql.schema.DataFetcher;
import io.reactivex.subjects.PublishSubject;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.model.enums.PokemonType;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class MutationResolvers {
    private final TrainerRepository trainerRepository;
    private final SpeciesRepository speciesRepository;

    private final PublishSubject<Species> createSpeciesPublishSubject = PublishSubject.create();

    public DataFetcher<Species> createSpecies() {
        return dataFetchingEnvironment -> {
            Map<String, Object> createSpeciesInput = dataFetchingEnvironment.getArgument("species");
            Species species = new Species();
            species.setName((String) createSpeciesInput.get("name"));
            species.setType(PokemonType.fromString((String) createSpeciesInput.get("type")));

            species = speciesRepository.save(species);
            createSpeciesPublishSubject.onNext(species);
            return species;
        };
    }

    public DataFetcher<Trainer> createTrainer() {
        return dataFetchingEnvironment -> {
            Map<String, Object> input = dataFetchingEnvironment.getArgument("trainer");
            Trainer trainer = new Trainer();
            trainer.setName((String) input.get("name"));
            return trainerRepository.save(trainer);
        };
    }

    public DataFetcher<Trainer> catchPokemon() {
        return dataFetchingEnvironment -> {
            String trainerId = dataFetchingEnvironment.getArgument("trainerId");
            Map<String, Object> createPokemonInput = dataFetchingEnvironment.getArgument("pokemon");
            Trainer trainer = trainerRepository.getById(Long.valueOf(trainerId));
            Species species = speciesRepository.getById(Long.valueOf((String) createPokemonInput.get("speciesId")));

            Pokemon pokemon = new Pokemon();
            pokemon.setName((String) createPokemonInput.get("name"));
            pokemon.setSpecies(species);

            trainer.addPokemon(pokemon);
            return trainerRepository.save(trainer);
        };
    }

    public PublishSubject<Species> getCreateSpeciesPublishSubject() {
        return createSpeciesPublishSubject;
    }
}
