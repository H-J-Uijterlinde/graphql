## 7. Subscriptions
Naast Queries en Mutations kent GraphQL nog een derde operatie, namelijk Subscriptions. Via een Subscription kan een client een verbinding met de server opzetten, waarover vervolgens door de server data gepushed kan worden. 

Vaak worden Subscriptions geimplementeerd door websockets, dat gaan wij in deze opdracht ook doen. Spelers van de game kunnen aangeven of ze pushnotificaties willen ontvangen wanneer een admin een nieuwe Pokemon Species heeft toegevoegd. We willen dus een Subscription aan ons schema toevoegen, waarmee de client notificaties ontvangt wanneer er een Species is aangemaakt via de `createSpecies` mutation.

### A. Voeg het Subscription type toe aan je schema
Voeg een nieuw type toe aan je schema, Subscription, met daaronder 1 veld: `speciesCreated`. Vergeet niet om Subscription ook toe te voegen aan je schema type.

### B. Update de configuratie om websockets te enablen
Voeg de volgende regels toe aan je `build.gradle` onder de dependencies:
```
implementation 'org.springframework.boot:spring-boot-starter-websocket'
implementation 'io.reactivex.rxjava2:rxjava:2.2.21'
```




naar [Opdracht 7](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/7/readme.md)
