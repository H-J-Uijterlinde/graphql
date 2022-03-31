## 5. Mutations
Tot nu toe hebben we alleen nog informatie opgehaald, maar via een beetje API kun je ook data bewerken. Voor de Pokemon game bijvoorbeeld moet het mogelijk zijn om je als speler te registreren, nieuwe Pokemon te ontdekken, en om deze te vangen. 

In GraphQL gaat het bewerken van data middels `Mutations`, een speciaal type binnen het schema. Onder het type mutations vallen alle create, update en delete operaties. Eigenlijk is het type Mutation niet veel anders dan elk ander type in het schema, behalve dan dat de resolvers van de velden onder Mutation data bewerken.

### A. Voeg het mutation type toe aan je schema, met een veld voor het registreren van een trainer
Zoals gezegd is Mutation een speciaal type binnen het GraphQL schema. Als je mutation operaties wilt aanbieden zul je dit type moeten aangeven in je schema. 

De eerste mutation die we gaan toevegen is die voor het registeren van een trainer. Om een trainer aan te kunnen maken is data nodig. Binnen GraphQL komt die data uit inputs. `Input` is vergelijkbaar met `Type` alleen is specifiek bedoeld om aan te geven welke data er nodig is voor mutations. Zie ook hier: https://graphql.org/learn/schema/ onder 'Input types'.

Definieer een veld onder het mutation type, met daarbij de input die nodig is. Bedenk daarbij welke velden verplicht zijn en welke niet.

### B. Schrijf een resolver voor de zojuist gedefinieerde resolver
Het zal jullie niet meer verbazen, maar ook de mutations worden uiteindelijk uitgevoerd door resolvers. Eigenlijk werkt dit exact hetzelfde als de voorgaande resolvers die we hebben geschreven, alleen wordt er in deze resolver data gepersist. Ook hier krijg je via de `getArgument()` methode van de `dataFetchingEnvironment` toegang tot de met de query meegegeven argumenten. Let er wel op dat als je van `getArgument()` een complexer object terug krijgt, zoals in dit geval onze input, het altijd een `Map<String, Ojbect>` is. 

### C. Update de runtimewiring
Ook dit is imiddels gesneden koek. We hebben een nieuw type gedefinieerd, die zullen we toe moeten voegen aan onze runtimewiring, net als het veld voor het registreren van een trainer, met daaran gekoppeld de zojuist geschreven resolver.

### D. Voeg een mutation toe voor het vangen van een Pokemon
Op het moment dat er in de game (de client) een Pokemon gevangen wordt, moet deze in de database opgeslagen worden, en aan de juiste trainer gekoppeld. 

Voeg een mutation toe die twee input parameters vereist, ten eerste de trainer ID, en ten tweede een pokemon. Schrijf hier vervolgens een resolver voor, en koppel deze middels de runtimewiring aan het juiste veld.

### E. Voeg een mutation toe voor het aanmaken van nieuwe Species
Als laatste willen we het voor admins van de game mogelijk maken om nieuwe Species toe te voegen. In de volgende opdracht gaan we ervoor zorgen dat deze mutation ook daadwerkelijk alleen door admins gebruikt mag worden.

Voeg een mutation toe voor het aanmaken van nieuwe species.

### F. Test je werk in de playground
Als je alles goed gedaan hebt kun je nu nieuwe trainers registreren, pokemon vangen, en nieuwe species toevoegen. Ga naar de playground, en test onze nieuwe mutations uit.
Als je werkende functies in de playground hebt geschreven kan het handig zijn om deze toe tevoegen aan de playground configuratie van je project, zodat deze voor ingevuld staan bij het opnieuw opstarten van de applicatie. Maak hiervoor eventueel een nieuw playground tabblad 'mutations'.

naar [Opdracht 6](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/6/readme.md)
