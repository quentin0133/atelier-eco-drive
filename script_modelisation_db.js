// part 1
db.vehicules.insertOne({
    brand: "Citroën", 
    model: "C3", 
    registration: "MD-674-CD", 
    telemetry: {
        last_position: {
            latitude: 46.806592,
            longitude: 1.686377
        },
        battery_level: 55
    },
    incident_history: [
        {
            date: "08/02/2026",
            type: "panne",
            description: "Le véhicule a perdu sa roue"
        },
        {
            date: "18/01/2026",
            type: "accident",
            description: "Accrochage avec un autre véhicule"
        }
    ]
});

db.users.insertOne({
    name: "Jean-michel BARREAU",
    age: 47,
    vehicule_id: ObjectId('69942a0391610d8a868ce5b0') // insert your vehicule id
});

// part 2
/*
Défi de modélisation : Comment stocker les caractéristiques techniques qui
varient selon le modèle (ex: autonomie, puissance moteur, options logicielles)
sans avoir de colonnes vides ?

On imbrique le model, elle va pouvoir varier pour chaque véhicule
*/

db.vehicules.insertOne({
    brand: "Peugeot", 
    model: {
        name: "SUV E-208",
        autonomy: 433,
        engine_power: 156
    }, 
    registration: "MZ-644-CD", 
    telemetry: {
        last_position: {
            latitude: 41.416592,
            longitude: 1.686377
        },
        battery_level: 55
    },
    incident_history: []
});

db.vehicules.insertOne({
    brand: "Tesla", 
    model: {
        name: "Model Y",
        autonomy: 657,
        engine_power: 156,
        auto_pilot: true
    }, 
    registration: "DD-634-CS", 
    telemetry: {
        last_position: {
            latitude: 21.516592,
            longitude: 0.646377
        },
        battery_level: 99
    },
    incident_history: []
});