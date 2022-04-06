package nl.quintor.pokedex.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.autoconfigure.editor.playground.PlaygroundController;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundProperties;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundTab;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class PlaygroundConfiguration {

    private final ResourceLoader resourceLoader;

    @Bean
    public PlaygroundController playgroundController(ObjectMapper objectMapper) {
        var playgroundProperties = new PlaygroundProperties();
        playgroundProperties.setPageTitle("/pokedex");
        playgroundProperties.setTabs(playgroundTabs());
        return new PlaygroundController(playgroundProperties, objectMapper);
    }

    private List<PlaygroundTab> playgroundTabs() {

        Map<String, String> headers = new HashMap<>();
        headers.put("role", "admin");

        var playgroundPokemonTab = new PlaygroundTab();
        playgroundPokemonTab.setName("Queries");
        playgroundPokemonTab.setHeaders(headers);
        playgroundPokemonTab.setQuery(resourceLoader.getResource("classpath:playground/playground_queries.graphqls"));
        playgroundPokemonTab.setVariables(resourceLoader.getResource("classpath:playground/query_variables.json"));

        var mutationTab = new PlaygroundTab();
        mutationTab.setName("Mutations");
        mutationTab.setHeaders(headers);
        mutationTab.setQuery(resourceLoader.getResource("classpath:playground/mutations.graphqls"));
        mutationTab.setVariables(resourceLoader.getResource("classpath:playground/mutation_variables.json"));

        return List.of(playgroundPokemonTab, mutationTab);
    }
}
