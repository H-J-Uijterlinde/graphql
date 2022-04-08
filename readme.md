Om real-time je GraphQL queries te testen gaan we onze eigen playground optuigen. De naam zegt het al; het is een plek om met je queries te spelen. Hiermee kan je direct zien of de structuur van je query goed is of je kan de query laten uitvoeren om de resultaten in te zien.

# Opdrachten

## 2. Configureer de playground.

### A. Maak een PlaygroundController configuration bean aan

Om de PlaygroundController aan te kunnen maken zullen we eerst een dependency moeten toevoegen. 
Voeg de `com.graphql-java-kickstart:graphql-spring-boot-starter:12.0.0` dependency toe aan je dependencies. 

Maak een playground configuration class aan en definieer hier een PlaygroundController bean.

### B. Navigeer naar je playground en test je query uit

Wanneer je de playground geconfigureerd hebt kan je deze benaderen door te navigeren naar http://localhost:8080/playground.
Dit is een grafische interface om GraphQL queries te testen!

### C. Voeg een PlaygroundTab toe aan je controller.

We kunnen de playground onderverdelen in meerdere tabs indien gewenst. Je zou bijvoorbeeld per entiteit een tabblad kunnen maken, het is maar net wat jij wil!

In een playground tab kunnen we een query file opgeven.
In deze files kan je queries schrijven die automatisch in je tabblad getoont worden. 

Maak hiervoor de `playground_queries.graphqls` file aan en plaats deze in `resources/playground`. Voeg de `PlaygroundTab` toe aan je `PlaygroundController` configuratie.
Je kan hier een zelf gedefinieerde query schrijven die jouw query aanroept in je schema van opdracht 1.

### D. Navigeer naar je playground en test je automatisch getoonde query uit

Navigeer naar je playground. Je zult nu zien dat je query die gedefinieerd is in `playground_queries.graphqls` getoont wordt in je tabblad.
Verifieer dat je bij het aanroepen van allPokemon op dit moment drie pokemon als resultaat krijgt.

naar [Opdracht 3](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/3/readme.md)
