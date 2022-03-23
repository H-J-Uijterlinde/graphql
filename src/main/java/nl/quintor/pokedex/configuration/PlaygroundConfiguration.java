package nl.quintor.pokedex.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.autoconfigure.editor.playground.PlaygroundController;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundProperties;
import graphql.kickstart.autoconfigure.editor.playground.properties.PlaygroundTab;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

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
        var playgroundPokemonTab = new PlaygroundTab();
        playgroundPokemonTab.setName("Queries");
        playgroundPokemonTab.setQuery(resourceLoader.getResource("classpath:playground/playground_queries.graphqls"));
        playgroundPokemonTab.setVariables(resourceLoader.getResource("classpath:playground/variables.json"));

        return List.of(playgroundPokemonTab);
    }
}
