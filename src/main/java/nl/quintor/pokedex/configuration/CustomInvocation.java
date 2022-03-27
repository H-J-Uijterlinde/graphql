package nl.quintor.pokedex.configuration;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.spring.web.servlet.GraphQLInvocation;
import graphql.spring.web.servlet.GraphQLInvocationData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
@Primary
public class CustomInvocation implements GraphQLInvocation {
    private final GraphQL graphQL;

    @Override
    public CompletableFuture<ExecutionResult> invoke(GraphQLInvocationData invocationData, WebRequest webRequest) {
        AuthContext authContext = new AuthContext(webRequest.getHeader("role"));
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(invocationData.getQuery())
                .graphQLContext(Map.of("auth", authContext))
                .operationName(invocationData.getOperationName())
                .variables(invocationData.getVariables())
                .build();

        return graphQL.executeAsync(executionInput);
    }
}
