## 1. Voeg het Species type toe aan het schema, en implementeer de relaties tussen verschillende types.
Nu we als het goed is een een werkende API hebben, waarmee we een lijstje van Pokemons kunnen ophalen, is het tijd om ons schema uit te breiden met een nieuw type, en om relaties te gaan leggen tussen verschillende types.

### A. Voeg het Species type toe aan het schema
Vertaal de `Species` entiteit naar een nieuw type binnen je `schema.graphqls`. Vergeet daarbij niet de relatie met Pokemon te definieren. GraphQL kent ook enums, deze hebben we nu ook nodig. Omgekeerd kunnen we nu ook aan het Pokemon type een species veld toevoegen.

### B. Schrijf resolvers voor de "complexe" velden van het schema
Voor velden in een graphql schema die niet resolven naar een scalar type (String, Int, Boolean, etc..) zullen ook resolvers gedefinieerd moeten worden. Het veld species op ons Pokemon type heeft bijvoorbeeld een resolver nodig om de Species van een Pokemon op te kunnen halen als de client daarom vraagt.

In ons eenvoudige schema zou dit zo simpel kunnen zijn als het aanroepen van de getter, maar die getter hebben we met opzet weggelaten. In productie applicaties, waar GraphQL vaak wordt gebruikt als een manier om meerdere microservices achter 1 API te bundelen, is het ophalen van relaties vaak minder eenvoudig. Relaties zouden zelfs door verschillende microservices beheerd kunnen worden.

Om een productie applicatie te simuleren gaan we zelf een resolver schrijven die voor een Pokemon de Species ophaalt. Net als voor de query uit opdracht 1 is deze resolver een functie die een `DataFetcher` returned. Uit de `dataFetchingEnvironment` kun je de parent entiteit halen waar deze resolver een veld voor resolved. Kijk hier: https://www.graphql-java.com/documentation/data-fetching#the-interesting-parts-of-the-datafetchingenvironment voor meer informatie, de `getSource()` methode is voor ons hier interessant.

Maak op dezelfde manier een resolver voor het pokemons veld van het type Species.

### C. Update de configuratie
Om de twee resolvers die we net gemaakt hebben aan de juiste velden in het schema te koppelen zullen we nu de `runtimeWiring` die we in opdracht 1 opgezet hebben moeten uitbreiden.

### D. Test de schema uitbreidig.
Je kunt de `allPokemons` query nu uitbreiden door ook het species veld in het resultaat te vragen. Als alles werkt krijg je nu voor alle pokemons ook zijn species terug.

### E. Voeg een query toe voor het ophalen van Species voor een bepaald Pokemon type.
Voor de game is het interessant om alle species van een bepaald Pokemon type op te halen. Dus bijvoorbeeld alle gras pokemon. 
Voeg een veld toe aan het Query type voor deze wens. Deze query krijgt dus een input parameter.
Ook voor deze query zullen we een resolver moeten schrijven. Inmiddels weet je als het goed is hoe dat moet. Via de `getArgument()` methode op de `dataFetchingEnvironment` krijg je toegang tot de arguments van de query.


naar [Opdracht 4](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/4/readme.md)
