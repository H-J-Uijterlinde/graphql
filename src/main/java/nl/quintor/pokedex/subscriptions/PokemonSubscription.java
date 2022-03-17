package nl.quintor.pokedex.subscriptions;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Pokemon;
import nl.quintor.pokedex.publishers.PokemonPublisher;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor
//@Component
public class PokemonSubscription implements GraphQLSubscriptionResolver {

//    private final PokemonPublisher pokemonPublisher;
//
//    public Publisher<Pokemon> pokemons() {
//        return pokemonPublisher.getPokemon();
//    }

}
