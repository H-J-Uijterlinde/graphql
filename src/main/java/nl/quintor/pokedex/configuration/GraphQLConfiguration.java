package nl.quintor.pokedex.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.execution.ExecutionStrategy;
import graphql.kickstart.autoconfigure.editor.playground.PlaygroundController;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundProperties;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundTab;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.kickstart.spring.error.GraphQLErrorHandlerFactory;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.spring.web.servlet.GraphQLEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.datafetchers.PokemonDataFetchers;
import nl.quintor.pokedex.datafetchers.QueryDataFetchers;
import nl.quintor.pokedex.datafetchers.SpeciesDataFetchers;
import nl.quintor.pokedex.datafetchers.TrainerDataFetchers;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@RequiredArgsConstructor
@Configuration
public class GraphQLConfiguration {
    private final QueryDataFetchers queryDataFetchers;
    private final TrainerDataFetchers trainerDataFetchers;
    private final PokemonDataFetchers pokemonDataFetchers;
    private final SpeciesDataFetchers speciesDataFetchers;
    private final ResourceLoader resourceLoader;

    @Bean
    public GraphQL graphQL() throws IOException {
        var url = Resources.getResource("schema.graphqls");
        var sdl = Resources.toString(url, Charsets.UTF_8);
        var graphQLSchema = buildSchema(sdl);

        //ExecutionInput executionInput = ExecutionInput.newExecutionInput().context(authContext).build();
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        return graphQL;
    }

    String query = "";
    void contextWiring() {

        AuthContext authContext = new AuthContext();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .graphQLContext(builder -> builder.put("authContext", authContext))
                .build();
    }

    @Bean
    public GraphQLSchema graphQLSchema() throws IOException {
        var url = Resources.getResource("schema.graphqls");
        var sdl = Resources.toString(url, Charsets.UTF_8);
        return buildSchema(sdl);
    }

    @Bean
    public PlaygroundController playgroundController(ObjectMapper objectMapper) {
        var playgroundProperties = new PlaygroundProperties();
        playgroundProperties.setPageTitle("/pokedex");
        playgroundProperties.setHeaders(playgroundHeaders());
        playgroundProperties.setTabs(playgroundTabs());
        return new PlaygroundController(playgroundProperties, objectMapper);
    }

    private List<PlaygroundTab> playgroundTabs() {
        var playgroundTrainerTab = new PlaygroundTab();
        playgroundTrainerTab.setName("Trainers");
        playgroundTrainerTab.setQuery(resourceLoader.getResource("classpath:playground/trainer_query.graphqls"));
        playgroundTrainerTab.setVariables(resourceLoader.getResource("classpath:playground/trainers.json"));

        var playgroundPokemonTab = new PlaygroundTab();
        playgroundPokemonTab.setName("Pokemons");
        playgroundPokemonTab.setQuery(resourceLoader.getResource("classpath:playground/pokemon_query.graphqls"));
        playgroundPokemonTab.setVariables(resourceLoader.getResource("classpath:playground/pokemons.json"));

        var playgroundPokemonMutationTab = new PlaygroundTab();
        playgroundPokemonMutationTab.setName("Create Pokemon");
        playgroundPokemonMutationTab.setQuery(resourceLoader.getResource("classpath:playground/pokemon_mutation.graphqls"));
        playgroundPokemonMutationTab.setVariables(resourceLoader.getResource("classpath:playground/pokemon_mutation.json"));

        return List.of(playgroundTrainerTab, playgroundPokemonTab, playgroundPokemonMutationTab);
    }

    /**
     * Mogelijk iets voor stukje security later.
     * @return
     */
    private Map<String, String> playgroundHeaders() {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer token");
        return headers;
    }

    private GraphQLSchema buildSchema(String sdl) {
        var typeRegistry = new SchemaParser().parse(sdl);
        var runtimeWiring = buildWiring();
        var schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .directive("auth", new AuthorizationDirective())
                .type(newTypeWiring("Query")
                        .dataFetcher("trainerByName", queryDataFetchers.getTrainerByName())
                        .dataFetcher("allTrainers", queryDataFetchers.getAllTrainers())
                        .dataFetcher("allSpecies", queryDataFetchers.getAllSpecies())
                        .dataFetcher("allPokemons", queryDataFetchers.getAllPokemons())
                        .dataFetcher("pokemonsBySpecies", queryDataFetchers.getPokemonsBySpecies()))
                .type(newTypeWiring("Trainer")
                        .dataFetcher("pokemons", trainerDataFetchers.getPokemonByTrainer()))
                .type(newTypeWiring("Species")
                        .dataFetcher("pokemons", speciesDataFetchers.getPokemonBySpecies()))
                .type(newTypeWiring("Pokemon")
                        .dataFetcher("trainer", pokemonDataFetchers.getTrainerByPokemon())
                        .dataFetcher("species", pokemonDataFetchers.getSpeciesByPokemon()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createPokemon", pokemonDataFetchers.createPokemon()))
                .build();
    }
}
