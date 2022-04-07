## 7. Subscriptions
Naast Queries en Mutations kent GraphQL nog een derde operatie, namelijk Subscriptions. Via een Subscription kan een client een verbinding met de server opzetten, waarover vervolgens door de server data gepushed kan worden. 

Vaak worden Subscriptions geimplementeerd door websockets, dat gaan wij in deze opdracht ook doen. Spelers van de game kunnen aangeven of ze pushnotificaties willen ontvangen wanneer een admin een nieuwe Pokemon Species heeft toegevoegd. We willen dus een Subscription aan ons schema toevoegen, waarmee de client notificaties ontvangt wanneer er een Species is aangemaakt via de `createSpecies` mutation.

- Voor meer informatie over hoe GraphQL-java met subscriptions omgaat zie: https://www.graphql-java.com/documentation/subscriptions/
- Voor documentatie over RxJava zie: https://reactivex.io/intro.html
- Voor een voorbeeld implementatie zie: https://github.com/graphql-java/graphql-java-subscription-example

### Let op:
dit is een vrij ingewikkelde opdracht. Het is geen schande als je zo nu en dan even in de uitwerkingen kijkt als je er niet uit komt!

### A. Voeg het Subscription type toe aan je schema
Voeg een nieuw type toe aan je schema, Subscription, met daaronder 1 veld: `speciesCreated`. Vergeet niet om Subscription ook toe te voegen aan je schema type.

### B. Update de configuratie om websockets te enablen
Voeg de volgende regels toe aan je `build.gradle` onder de dependencies:
```
implementation 'org.springframework.boot:spring-boot-starter-websocket'
implementation 'io.reactivex.rxjava2:rxjava:2.2.21'
```

Voeg onder de configuratie package de volgende class toe:
```
import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.controllers.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/subscriptions")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new AbstractHandshakeHandler() {});
    }
}
```
In bovenstaande configuratie koppelen we een handler voor websocket berichten aan een websocket endpoint. Het `/subscriptions` endpoint is het standaard subscriptions endpoint van onze PlayGround client.
Zoals de IDE nu vast aangeeft bestaat de WebsocketHandler class nog niet. Deze gaan we later implementeren.

### C. Voeg een event publisher toe aan de createSpecies mutation
We willen uiteindelijk kunnen subscriben op het createSpecies event. Daarvoor moeten we er eerst voor zorgen dat er zo'n event gepublished wordt. Hoe je dat doet maakt niet zoveel uit. De makkelijkste manier is om in de `createSpecies` resolver zelf, net na het persisten een event te publishen. Je zou daarvoor gebruik kunnen maken van de `PublishSubject` class van RxJava.

### D. Implementeer de speciesCreated resolver
Voeg een `SubscriptionResolvers` class toe met een resolver voor onze speciesCreated subscription. Zorg ervoor dat je methode een `DataFetcher<Flowable<Species>>` returned. De flowable kun je creeren uit de `PublishSubject` waar de createSpecies resolver events naar published.

Koppel de resolver aan het juiste veld door je runtimewiring te updaten.

### E. Implementeer de websocket handler
In tegenstelling tot de queries en mutations zullen de subscription requests niet via het REST endpoint binnenkomen maar via het websocket endpoint. Om hiermee om te moeten gaan zullen we rauwe tekst uit een binnenkomend websocket bericht moeten vertalen naar een GraphQL subscription.

Maak de class `WebsocketHandler` aan en laat deze `TextWebSocketHandler` extenden en `SubProtocolCapable` implementeren. Autowire een bean van het type `GraphQL` in je class, en voeg in ieder geval de volgende code toe: 
```
    @NotNull
    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("graphql-ws");
    }
```
Als laatste zullen we de volgende functie moeten overriden: `public void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception`. De tekst die je via de websocket in die methode binnenkrijgt is in JSON formaat en kan vertaald worden naar de volgende pojo: 
```
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
```
Met de informatie in objecten van bovenstaande class kun je middels de GraphQL bean die je tot je beschikking hebt een `Publisher<ExecutionResult>` ophalen. Kijk hier https://www.graphql-java.com/documentation/subscriptions/ hoe je dat doet. 

Op de publisher uit de vorige stap moeten we subscriben. Dit kun je doen door de `subcribe` methode aan te roepen waarbij je een implementatie van de `Subscriber` interface uit de reactivestreams package moet meegeven. In de `onNext` methode van deze subscriber worden de events "gepushed". Bijvoorbeeld het aanmaken van een nieuwe species. In die `onNext` methode zullen we dus een bericht terug moeten sturen over de websocket met daarin de aangemaakte species. 
De tekst die je terug stuurt over de websocket moet ook in Json formaat zijn, afgeleid van de volgende class: 
```
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {
    private final String type = "data";

    private Object payload;
    private String id;
}
```
Waarbij de waarde voor payload de nieuw aangemaakte species is, en de waarde voor id gelijk is aan de waarde van id uit het request hierboven.

### F. Test je subscription
Het leukste is om in twee vensters naast elkaar naar de playground te navigeren.
In 1 van de vensters verstuur je een request voor de zojuist aangemaakte subscription. Als alles goed werkt geeft de playground dan aan "listening..." met een spinner. In het tweede venster run je nu de createSpecies mutation. Je zou nu in het eerste venster, zonder te verversen, een bericht moeten zien over de nieuw aangemaakte species! 

Dat was m, bedankt voor de inzet!
