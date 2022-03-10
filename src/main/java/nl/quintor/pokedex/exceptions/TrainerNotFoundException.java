package nl.quintor.pokedex.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrainerNotFoundException extends RuntimeException implements GraphQLError {

    private final Integer errorCode;

    public TrainerNotFoundException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public List<Object> getPath() {
        return GraphQLError.super.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return GraphQLError.super.toSpecification();
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> customAttributes = new LinkedHashMap<>();

        customAttributes.put("HTTP_STATUS", this.errorCode);
        customAttributes.put("MESSAGE", this.getMessage());

        return customAttributes;
    }
}
