In deze hands on gaan we de backend voor een PokeDex bouwen. Omdat we verwachten dat er in de loop van de tijd heel veel specifieke wensen vanuit de client gaan komen betreft de data die ze nodig hebben, is besloten om de data te onstluiten middels een GraphQL API.

De kern van ons model is simpel. Wij als pokemon trainers kunnen pokemons vangen, die pokemons kunnen we een naam geven, en deze pokemons zijn van een bepaalde soort (Pikachu, Charmander, etc..). We hebben dus drie classes in ons model; Trainer, Pokemon en Species.

Een trainer kan meerdere pokemon hebben, een pokemon kan maar van een trainer zijn, en maar van een soort. Van een bepaalde soort kunnen er meerdere pokemons op de wereld rond lopen.

Voor deze opdrachten gaan we gebruik maken van Java om onze GraphQL API te implementeren. We maken daarbij ook gebruik van het GraphQL-Java project, maar we gaan zo weinig mogelijk abstracties gebruiken om een beter beeld te krijgen van de verschillende aspecten van een GraphQL API.

# Opdrachten

## 1. Implementeer een methode voor het ophalen van alle pokemon.

De kern van GraphQL is het GraphQL schema. Het schema is als het ware de documentatie van de API en beschrijft welke operaties er allemaal mogelijk zijn, en welke data beschikbaar is.

### A. Voeg voor de pokemon entiteit een type toe aan het schema

We gaan een begin maken met ons GraphQL schema. Voeg daarvoor een file genaamd `schema.graphqls` toe aan de resources van je project.

Voeg voor de pokemon entiteit een type toe aan het schema, maar nog zonder de relaties met Trainer en Species.


### B. Voeg een query type toe aan het schema met 1 veld voor het ophalen van alle pokemons.

Binnen GraphQL is de conventie om al je queries binnen het type Query onder te brengen. Voeg dit type toe aan je schema, en definieer daarin een query voor het ophalen van alle pokemon. De return type van deze query is een lijst van objecten van het type pokemon (die we net hebben aangemaakt).


### C. Bouw een resolver voor de query die we zojuist hebben gedefinieerd.

In graphQL wordt het daadwerkelijk ophalen van data gedaan door resolvers. Dit geld uiteraard voor queries zelf. Maar ook voor het ophalen van bepaalde velden van andere types, zoals bijvoorbeeld het ophalen van alle pokemons van een trainer, zoals we straks nog zullen zien.

In deze opdracht gaan we voor de query om alle pokemons op te halen een resolver schrijven, en we gaan deze resolver aan het juiste veld in ons GraphQL schema koppelen.

Het GraphQL-Java project gebruikt voor resolvers het woord datafetchers. Dit is wat verwarrend, maar er wordt hetzelfde mee bedoeld.

Wij hebben ervoor gekozen om resolver functies (DataFetchers) onder te brengen in een class voor het type waarvoor ze een veld resolven, dus bijvoorbeeld de class QueryResolvers. Maar waar je ze definieert maakt uiteindelijk niet zoveel uit.

Definieer een functie die een DataFetcher voor een lijst van Pokemon returned. De repositories voor het ophalen van data zitten al in het project. Zie https://www.graphql-java.com/documentation/data-fetching voor een voorbeeld. DataFetcher is een functional interface, dus je kunt er ook voor kiezen om een lambda te returnen in plaats van een anonymous class.


### D. Koppel de zojuist gemaakte resolver functie aan het juiste veld van het schema.

We hebben nu een schema en we hebben een resolver om een veld uit ons schema te resolven. De volgende stap is het koppelen van onze resolver aan ons schema veld. 

De GraphQL-Java library die wij gebruiken ondersteund twee manieren voor het creeren van een schema, door middel van SDL (Schema Definition Language) of programmatisch. Wij hebben ervoor gekozen om gebruikt te maken van SDL door het schema expliciet vast te leggen in een schema.graphqls file. 

GraphQL-Java maakt gebruikt van zogenaamde runtimewiring om het schema, en de implementatie daarvan aan elkaar te koppelen. 

Voeg de volgende configuratie toe aan je project:
```
    @Bean
    public GraphQL graphQL() throws IOException {
        var url = Resources.getResource("schema.graphqls");
        var sdl = Resources.toString(url, Charsets.UTF_8);
        var graphQLSchema = buildSchema(sdl);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        var typeRegistry = new SchemaParser().parse(sdl);
        var runtimeWiring = buildWiring();
        var schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }
```

Zoals je ziet zul je zelf nog de `buildWiring()` methode moeten implementeren, die een `RuntimeWiring` moet returnen. Zie https://www.graphql-java.com/documentation/schema#creating-a-schema-using-the-sdl voor een voorbeeld.

naar [Opdracht 2](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/2/readme.md)
