package nl.quintor.pokedex.graph;


import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.configuration.AuthContext;
import nl.quintor.pokedex.model.Trainer;
import nl.quintor.pokedex.repositories.TrainerRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//@Component
//@WebServlet(urlPatterns = "/graphql")
@Component
@RequiredArgsConstructor
public class GraphQLEndpoint {
//
    private final TrainerRepository trainerRepository;
//
//    public GraphQLEndpoint(GraphQLSchema schema, TrainerRepository trainerRepository) {
//        SimpleGraphQLHttpServlet.newBuilder(schema).build();
//        this.trainerRepository = trainerRepository;
//
//    }
//
//    public GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
//        Trainer user = request
//                .map(req -> req.getHeader("Authorization"))
//                .filter(id -> !id.isEmpty())
//                .map(id -> id.replace("Bearer ", ""))
//                .map(id -> trainerRepository.getById(Long.valueOf(id)))
//                .orElse(null);
//        return new AuthContext(user);
//    }
//
}
