package nl.quintor.pokedex.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Setter
@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer experience;
    @ManyToOne(fetch = FetchType.LAZY)
    private Species species;
    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer;


}
