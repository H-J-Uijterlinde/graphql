package nl.quintor.pokedex.directives;

import graphql.GraphQLContext;
import graphql.language.StringValue;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.springframework.stereotype.Component;

@Component
public class AuthDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        StringValue targetAuthRole = (StringValue) environment.getDirective().getArgument("role").getArgumentValue().getValue();
        String authRole = targetAuthRole.getValue();

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher<?> authDataFetcher = dataFetchingEnvironment -> {
            GraphQLContext graphQLContext = dataFetchingEnvironment.getGraphQlContext();

            if (graphQLContext != null) {
                return originalDataFetcher.get(dataFetchingEnvironment);
            } else {
                return null;
            }
        };

        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }
}
