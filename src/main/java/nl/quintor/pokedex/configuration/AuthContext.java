package nl.quintor.pokedex.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthContext {
    private String role;
}
