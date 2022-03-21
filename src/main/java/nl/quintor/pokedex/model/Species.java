package nl.quintor.pokedex.model;

import lombok.Getter;
import lombok.Setter;
import nl.quintor.pokedex.model.enums.PokemonType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PokemonType type;
    @OneToMany(mappedBy = "species")
    private List<Pokemon> pokemons;
}
