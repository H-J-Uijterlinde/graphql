package nl.quintor.pokedex.publishers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.pokedex.model.Pokemon;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

@Component
@Slf4j
public class PokemonPublisher {

    private final FluxProcessor<Pokemon, Pokemon> fluxProcessor;
    private final FluxSink<Pokemon> pokemonSink;

    public PokemonPublisher() {
        this.fluxProcessor = DirectProcessor.<Pokemon>create().serialize();
        this.pokemonSink = fluxProcessor.sink();
    }

    public Publisher<Pokemon> getPokemon() {
        return fluxProcessor.map(pokemon -> {
            log.info("Publishing pokemon {}", pokemon.getName());
            return pokemon;
        });
    }

    public Publisher<Pokemon> getPokemonById(Long id) {
        return fluxProcessor.filter(pokemon -> id.equals(pokemon.getId()))
                .map(pokemon -> {
                    log.info("Publishing pokemon {}", pokemon.getName());
                    return pokemon;
        });
    }

    public void publish(Pokemon pokemon) {
        pokemonSink.next(pokemon);
    }
}
