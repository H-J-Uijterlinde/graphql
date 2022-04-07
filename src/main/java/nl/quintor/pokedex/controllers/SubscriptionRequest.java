package nl.quintor.pokedex.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class SubscriptionRequest {
    private String id;
    private String type;
    private Payload payload;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Payload {
        private String operationName;
        private String query;
    }
}
