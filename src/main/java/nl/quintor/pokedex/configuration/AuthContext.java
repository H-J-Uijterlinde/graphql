package nl.quintor.pokedex.configuration;

import graphql.servlet.GraphQLContext;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.model.Trainer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Implementatie naar eigen keus, bijvoorbeeld spring security. Zie workshop Sander ten Hoorn :-)
 */
public class AuthContext extends GraphQLContext {

//    private final UserDetailsService userDetailsService;

//    private final Trainer trainer;
//    public AuthContext(Trainer trainer, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
//        super(request, response);
//        this.trainer = trainer;
//    }

    public AuthContext() {
        super();
        //this.trainer = trainer;
    }


//    public Trainer getCurrentTrainer() {
//        return trainer;
//    }
    boolean hasRole(String roleName) {
        return roleName.equals("trainer");
    }

//    boolean hasRole(String roleName) {
//        return userDetailsService.loadUserByUsername("user")
//                .getAuthorities()
//                .stream()
//                .anyMatch(authority -> authority.getAuthority()
//                        .equals(roleName));
//    }
}
