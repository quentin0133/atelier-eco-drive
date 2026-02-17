# Atelier Eco Drive

Quentin YAHIA - quentin.yahia.pro@gmail.com  
Hippolyte GAUTHERON - hippolyte.gauth@gmail.com

## Pré-requis

- Java 17
- Maven
- MongoDB

## Configuration 
Pour que le projet fonctionne, mettez le port de mongodb à `27018`.  
On utilises ici la base de donnée `eco_drive_db`

## Jeu de donnée :

```js
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
    brand: "Audi",
    model: {
        name: "San diego",
        autonomy: 657,
        engine_power: 156,
        auto_pilot: true
    },
    registration: "VX-000-XX",
    telemetry: {
        last_position: {
            latitude: 21.516592,
            longitude: 0.646377
        },
        battery_level: 15
    },
    incident_history: [
        {
            date: "27/05/2024",
            type: "turbine",
            description: "Malfonction de la turbine électrique"
        },
        {
            date: "25/06/2024",
            type: "moteur",
            description: "Le moteur a surchauffé"
        },
        {
            date: "28/06/2024",
            type: "pneumatique",
            description: "La roue s'est dégonflé"
        }
    ]
});

db.vehicles.insertOne({
    brand: "BMW",
    model: {
        name: "Peak",
        autonomy: 250,
        engine_power: 119
    },
    registration: "ZE-247-IU",
    telemetry: {
        last_position: {
            latitude: 21.516592,
            longitude: 0.646377
        },
        battery_level: 85
    },
    incident_history: [
        {
            date: "24/07/2020",
            type: "moteur",
            description: "Ne sais pas"
        },
        {
            date: "07/01/2021",
            type: "phare",
            description: "Phare gauche s'est fait percuté"
        },
        {
            date: "28/06/2024",
            type: "carosserie",
            description: "La voiture s'est enfoncé contre un mur"
        }
    ]
});

db.vehicles.insertOne({
    brand: "Tesla", 
    model: {
        name: "Model Z",
        autonomy: 657,
        engine_power: 156,
        auto_pilot: true
    }, 
    registration: "DX-600-VB", 
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
    model: {
        name: "Tiguan",
        autonomy: 657,
        engine_power: 156
    }, 
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

db.vehicles.insertOne({
    brand: "Tesla", 
    model: {
        name: "Model X",
        autonomy: 657,
        engine_power: 156,
        auto_pilot: true
    }, 
    registration: "PZ-741-AA", 
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
    name: "Véronique DUPONT",
    age: 82,
    vehicle_id: ObjectId('699482140475e74c5b8ce5d0') // don't forget to update
});

db.users.insertOne({
    name: "Jeff HARDY",
    age: 32,
    vehicle_id: ObjectId('699482130475e74c5b8ce5cf') // don't forget to update
});

db.users.insertOne({
    name: "Jérémy FORT",
    age: 22,
    vehicle_id: [ObjectId('699482130475e74c5b8ce5ce'), ObjectId('699483e60475e74c5b8ce5d1')] // don't forget to update
});

db.users.insertOne({
    name: "Papa de Jérémy",
    age: 48,
    vehicle_id: [ObjectId('699483e60475e74c5b8ce5d1'), ObjectId('699482130475e74c5b8ce5ce'), ObjectId('699483e80475e74c5b8ce5d2')] // don't forget to update
});
```