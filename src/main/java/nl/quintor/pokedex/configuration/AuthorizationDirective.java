package nl.quintor.pokedex.configuration;

import graphql.GraphQLContext;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.Map;
@Slf4j

public class AuthorizationDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        String targetAuthRole = environment.getDirective().getArgument("role").getName();

        log.info(targetAuthRole);

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher authDataFetcher = dataFetchingEnvironment -> {
            GraphQLContext context = dataFetchingEnvironment.getGraphQlContext();
            log.info(context.toString());
            //Map<String, Object> contextMap =

            //log.info("" + contextMap.size());
            AuthContext authContext = context.get("authContext");
            if (authContext.hasRole(targetAuthRole)) {
                return originalDataFetcher.get(dataFetchingEnvironment);
            } else {
                return null;
            }
        };
        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }
}
