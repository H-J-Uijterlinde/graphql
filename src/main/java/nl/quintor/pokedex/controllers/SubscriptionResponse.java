package nl.quintor.pokedex.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {
    private final String type = "data";

    private Object payload;
    private String id;
}
