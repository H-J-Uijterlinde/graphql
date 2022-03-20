package nl.quintor.pokedex.model.types;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Species species;
    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer;
}
