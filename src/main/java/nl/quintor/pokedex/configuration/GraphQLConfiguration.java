package nl.quintor.pokedex.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.spring.web.servlet.GraphQLInvocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.pokedex.directives.AuthDirective;
import nl.quintor.pokedex.resolvers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GraphQLConfiguration {
    private final QueryResolvers queryResolvers;
    private final TrainerResolvers trainerResolvers;
    private final SpeciesResolvers speciesResolvers;
    private final PokemonResolvers pokemonResolvers;
    private final MutationResolvers mutationResolvers;
    private final AuthDirective authDirective;

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

    @Bean
    @Primary
    public GraphQLInvocation graphQLInvocation() throws Exception {
        return (invocationData, webRequest) -> {
            ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(invocationData.getQuery())
                    .context(webRequest)
                    .operationName(invocationData.getOperationName())
                    .variables(invocationData.getVariables())
                    .build();
            System.out.println(webRequest.getHeader("role"));
            System.out.println("hello");

            try {
                return graphQL().executeAsync(executionInput);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };
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
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createSpecies", mutationResolvers.createSpecies())
                        .dataFetcher("createTrainer", mutationResolvers.createTrainer())
                        .dataFetcher("catchPokemon", mutationResolvers.catchPokemon()))
                .directive("auth", authDirective)
                .build();
    }
}
