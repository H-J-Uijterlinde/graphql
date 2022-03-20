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
public class PlayGroundConfiguration {
    private final ResourceLoader resourceLoader;

    @Bean
    public PlaygroundController playgroundController(ObjectMapper objectMapper) {
        var playgroundProperties = new PlaygroundProperties();
        playgroundProperties.setPageTitle("/pokedex");
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

        var mutationTab = new PlaygroundTab();
        mutationTab.setName("Mutations");
        mutationTab.setQuery(resourceLoader.getResource("classpath:playground/mutations.graphqls"));

        return List.of(playgroundTrainerTab, playgroundPokemonTab, mutationTab);
    }
}
