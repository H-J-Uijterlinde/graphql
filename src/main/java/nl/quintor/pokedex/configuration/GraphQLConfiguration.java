package nl.quintor.pokedex.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.resolvers.PokemonResolvers;
import nl.quintor.pokedex.resolvers.SpeciesResolvers;
import nl.quintor.pokedex.resolvers.QueryResolvers;
import nl.quintor.pokedex.resolvers.TrainerResolvers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@RequiredArgsConstructor
@Configuration
public class GraphQLConfiguration {
    private final QueryResolvers queryResolvers;
    private final TrainerResolvers trainerResolvers;
    private final SpeciesResolvers speciesResolvers;
    private final PokemonResolvers pokemonResolvers;

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
                        .dataFetcher("allPokemon", queryResolvers.getAllPokemon())
                        .dataFetcher("speciesByType", queryResolvers.speciesByType())
                        .dataFetcher("allTrainers", queryResolvers.getAllTrainers())
                        .dataFetcher("trainerByName", queryResolvers.getTrainerByName()))
                .type(newTypeWiring("Trainer")
                        .dataFetcher("pokemons", trainerResolvers.getPokemonByTrainer()))
                .type(newTypeWiring("Species")
                        .dataFetcher("pokemons", speciesResolvers.getPokemonBySpecies()))
                .type(newTypeWiring("Pokemon")
                        .dataFetcher("species", pokemonResolvers.getSpeciesByPokemon()))

                .build();
    }
}
