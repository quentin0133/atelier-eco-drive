// part 1
db.vehicles.insertOne({
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
    vehicle_id: ObjectId('699443950475e74c5b8ce5b4') // insert your vehicule id
});

// part 2
/*
Défi de modélisation : Comment stocker les caractéristiques techniques qui
varient selon le modèle (ex: autonomie, puissance moteur, options logicielles)
sans avoir de colonnes vides ?

On imbrique le model, elle va pouvoir varier pour chaque véhicule
*/

db.vehicles.insertOne({
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

db.vehicles.insertOne({
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

db.vehicles.insertOne({
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
        battery_level: 12
    },
    incident_history: []
});

db.vehicles.insertOne({
    brand: "Volkswagen", 
    model: "Tiguan", 
    registration: "DD-634-CS", 
    telemetry: {
        last_position: {
            latitude: 21.516592,
            longitude: 0.646377
        },
        battery_level: 14
    },
    incident_history: []
});

db.users.insertOne({
    name: "Véronique DUPONT",
    age: 82,
    vehicle_id: ObjectId('699443bc0475e74c5b8ce5b7')
});

db.users.insertOne({
    name: "Jeff HARDY",
    age: 32,
    vehicle_id: ObjectId('69944c4f0475e74c5b8ce5b8')
});

db.vehicles.insertOne({
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
        battery_level: 12
    },
    incident_history: [
        {
            date: "18/01/2026",
            type: "moteur",
            description: "Accrochage avec un autre véhicule"
        }
    ]
});

db.users.insertOne({
    name: "Jérémy FORT",
    age: 22,
    vehicle_id: [ObjectId('6994485a0f38c61f50591f52'), ObjectId('69944e5c0475e74c5b8ce5bc')]
});

db.users.insertOne({
    name: "Papa de Jérémy",
    age: 48,
    vehicle_id: [ObjectId('699453e80475e74c5b8ce5c3'), ObjectId('6994485a0f38c61f50591f52'), ObjectId('69944e5c0475e74c5b8ce5bc')]
});