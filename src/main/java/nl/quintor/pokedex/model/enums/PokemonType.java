package nl.quintor.pokedex.model.enums;

import java.util.Arrays;

public enum PokemonType {
    NORMAL,
    FIRE,
    WATER,
    GRASS,
    FLYING,
    FIGHTING,
    POISON,
    ELECTRIC,
    GROUND,
    ROCK,
    PSYCHIC,
    ICE,
    BUG,
    GHOST,
    STEEL,
    STONE,
    DRAGON,
    DARK,
    FAIRY;

    public static PokemonType fromString(String input) {
        return Arrays.stream(PokemonType.values())
                .filter(pokemonType -> pokemonType.toString().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow();
    }
}
