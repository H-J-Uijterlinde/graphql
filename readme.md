## 6. Directives
Directives zijn een manier op zogenaamde "cross-cutting" logica op je GraphQL schema toe te passen. Een voorbeeld hiervan is authorizatie. Met directives kun je bijvoorbeeld bepaalde velden van een type alleen beschikbaar maken voor bepaalde gebruikers. 

In deze opdracht gaan we een `@Auth` directive maken. Waarmee we ervoor willen zorgen dat alleen gebruikers met de role admin nieuwe species kunnen aanmaken. 

### A. Definieer een Auth directive en pas deze toe op de createSpecies mutation
Kijk hier: https://www.graphql-java.com/documentation/sdl-directives voor een voorbeeld.

### B. Zorg dat de GraphQL context aangevuld wordt met header informatie.
In GraphQL komt alle informatie die niet direct deel uitmaakt van de query, zoals bijvoorbeeld de headers, uit de GraphQL context. Via de `getGraphQLcontext()` methode op de `dataFetchingEnvironment` krijg je toegang tot de context. Deze is nu alleen nog leeg. Om informatie uit de headers aan de GraphQL context toe te voegen moet de configuratie wat aangepast worden. Omdat dit zo implementatie gericht is krijg je die configuratie kant en klaar.

Voeg onder de configuration package de volgende class toe:
```
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class AuthContext {
    private String role;
}
```
Voeg onder de configuration package ook deze class toe:
```
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
        AuthContext authContext = AuthContext.of(webRequest.getHeader("role"));
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(invocationData.getQuery())
                .graphQLContext(Map.of("auth", authContext))
                .operationName(invocationData.getOperationName())
                .variables(invocationData.getVariables())
                .build();

        return graphQL.executeAsync(executionInput);
    }
}

```
Als je nu via de dataFetchingEnvironment de GraphQL context ophaalt krijg je een map terug met 1 entry. Onder de key `auth` krijg je een object van de hierboven gedefinieerde `AuthContext` terug.

### C. Implementeer de Auth directive
Nu we ervoor gezorgd hebben dat header informatie in de GraphQL context terecht komt kunnen we de directive gaan schrijven. Via de link in opdracht A vind je een voorbeeld van hoe je dit moet doen.

### D. Update je runtimewiring
Net als je types en resolvers moet ook de implementatie van de authdirective gekoppeld worden aan de authdirective in je `schema.graphqls`. Update de runtimewiring om dit voor elkaar te krijgen.


naar [Opdracht 7](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/7/readme.md)
