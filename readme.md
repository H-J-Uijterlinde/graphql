## 4. Maak een TrainerResolver

### A. Voeg een Trainer type toe aan je schema en pas de Runtime Wiring aan.

Voeg net als Pokemon en Species een nieuw type toe aan je schema en verwerk deze in je Runtime Wiring configuratie.

### B. Maak een Trainer Resolver en zorg dat je alle pokemon van een bepaalde trainer kan ophalen.

Wanneer je de resolver gemaakt hebt, kan je de werking verifieren door in je playground alle trainers op te halen. 
Momenteel haalt de query allTrainers alleen de velden id en name op. Je kan hier bijvoorbeeld het veld `pokemons { name }` aan toevoegen.

### C. Voeg een nieuwe functie toe aan je Query Resolver om een trainer op naam op te kunnen halen.

Je kan in je GraphQL query een argument (parameter) meegeven in je query. Deze kan je in een resolver ophalen met de `getArgument("argumentNaam")` methode.
Wanneer je een methode gemaakt hebt om een trainer op basis van naam te resolven, kan je deze testen in je playground!

### D. Breid je playground uit.

We kunnen de query van opdracht C ook laten tonen bij het openen van de playground net als je bij de andere queries gewend bent.
Echter, deze query bevat nu een argument en die zullen we ergens vandaan moeten halen. Maak hiervoor een `variables.json` file aan en plaats deze in je `resources/playground`. Voeg hier een entry aan toe bijvoorbeeld 
`{
"trainerName": "Brock"
}`

Pas je playground configuratie aan, zodat de file bekend is in je PlaygroundTab. Pas vervolgens ook je `playground_queries.graphqls` file aan en verwerk je functie uit opdracht C in dit schema.

Navigeer naar je playground. Bij het uitvoeren van je nieuwe query zal de playground jouw variabele vervangen met de variabele uit de json file!

### E. Voeg een functie toe om de trainer van een pokemon te resolven.

Momenteel kunnen we de Pokemon van een trainer opvragen, maar nog niet de trainer van een Pokemon. Voeg het trainer veld toe aan de type Pokemon in je schema.
Maak hiervoor een functie in je Pokemon Resolver en verifieer de werking hiervan in je playground door een trainer op te vragen vanuit een Pokemon.

naar [Opdracht 5](https://git.quintor.nl/staq/graphql-staq-2022/-/blob/opdrachten/5/readme.md)
