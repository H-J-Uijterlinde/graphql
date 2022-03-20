package nl.quintor.pokedex.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.datafetchers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@RequiredArgsConstructor
@Configuration
public class GraphQLConfiguration {
    private final QueryDataFetchers queryDataFetchers;
    private final TrainerDataFetchers trainerDataFetchers;
    private final PokemonDataFetchers pokemonDataFetchers;
    private final SpeciesDataFetchers speciesDataFetchers;
    private final MutationResolvers mutationResolvers;

    @Bean
    public GraphQL graphQL() throws IOException {
        var url = Resources.getResource("schema.graphqls");
        var sdl = Resources.toString(url, Charsets.UTF_8);
        var graphQLSchema = buildSchema(sdl);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        var typeRegistry = new SchemaParser().parse(sdl);
        var runtimeWiring = buildWiring();
        var schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("trainerByName", queryDataFetchers.getTrainerByName())
                        .dataFetcher("allTrainers", queryDataFetchers.getAllTrainers())
                        .dataFetcher("allSpecies", queryDataFetchers.getAllSpecies()))
                .type(newTypeWiring("Trainer")
                        .dataFetcher("pokemons", trainerDataFetchers.getPokemonByTrainer()))
                .type(newTypeWiring("Species")
                        .dataFetcher("pokemons", speciesDataFetchers.getPokemonBySpecies()))
                .type(newTypeWiring("Pokemon")
                        .dataFetcher("trainer", pokemonDataFetchers.getTrainerByPokemon())
                        .dataFetcher("species", pokemonDataFetchers.getSpeciesByPokemon()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createSpecies", mutationResolvers.createSpecies())
                        .dataFetcher("createTrainer", mutationResolvers.createTrainer())
                        .dataFetcher("catchPokemon", mutationResolvers.catchPokemon()))
                .build();
    }
}
