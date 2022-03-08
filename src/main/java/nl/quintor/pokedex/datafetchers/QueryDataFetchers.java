package nl.quintor.pokedex.datafetchers;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Species;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.SpeciesRepository;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QueryDataFetchers {
    private final TrainerRepository trainerRepository;
    private final SpeciesRepository speciesRepository;

    public DataFetcher<Trainer> getTrainerByName() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return trainerRepository.findByName(name).orElse(null);
        };
    }

    public DataFetcher<List<Trainer>> getAllTrainers() {
        return dataFetchingEnvironment -> trainerRepository.findAll();
    }

    public DataFetcher<List<Species>> getAllSpecies() {
        return dataFetchingEnvironment -> speciesRepository.findAll();
    }
}
