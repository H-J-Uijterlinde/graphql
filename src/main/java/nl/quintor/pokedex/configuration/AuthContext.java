package nl.quintor.pokedex.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class AuthContext {
    private String role;
}
