package fr.dawan.formation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Filters.eq;

@Slf4j
public class FleetAnalytics {
    private final MongoCollection<Document> vehicles;

    public FleetAnalytics(MongoCollection<Document> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Document> getAverageBatteryByBrand() {
        List<Bson> pipeline = List.of(
            group("$brand", avg("avg_battery_level", "$telemetry.battery_level"))
        );
        return vehicles.aggregate(pipeline).into(new ArrayList<>());
    }

    public List<Document> getUserByIncidentEngine() {
        List<Bson> pipeline = List.of(
            lookup("users", "_id", "vehicle_id", "user"),
            match(eq("incident_history.type", "moteur")),
            project(
                Projections.fields(
                    Projections.excludeId(),
                    Projections.include("brand", "model", "user")
                )
            )
        );
        return vehicles.aggregate(pipeline).into(new ArrayList<>());
    }

    public List<Document> getTop3UserWithHighestNumberVehicle() {
        List<Bson> pipeline = List.of(
            lookup("users", "_id", "vehicle_id", "user"),
            unwind("$user"),
            group("$user._id", sum("total_vehicle", 1)),
            sort(eq("total_vehicle", -1)),
            limit(3),
            lookup("users", "_id", "_id", "user"),
            unwind("$user"),
            project(
                Projections.fields(
                    Projections.excludeId(),
                    Projections.include("user", "total_vehicle")
                )
            )
        );
        return vehicles.aggregate(pipeline).into(new ArrayList<>());
    }

    public static void main(String[] args) {
        FleetAnalytics fleetAnalytics;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27018")) {
            MongoDatabase database = mongoClient.getDatabase("eco_drive_db");
            MongoCollection<Document> vehiculeCollection = database.getCollection("vehicles");
            fleetAnalytics = new FleetAnalytics(vehiculeCollection);

            log.info("Moyenne de batterie par marque");
            fleetAnalytics.getAverageBatteryByBrand().forEach(vehicle -> log.info(vehicle.toString()));

            log.info("Utilisateur par incident de type moteur");
            fleetAnalytics.getUserByIncidentEngine().forEach(vehicle -> log.info(vehicle.toString()));

            log.info("Top 3 des utilisateurs avec le plus de voiture");
            fleetAnalytics.getTop3UserWithHighestNumberVehicle().forEach(vehicle -> log.info(vehicle.toString()));
        } catch (Exception e) {
            log.error("Erreur de connexion : {}", e.getMessage());
        }
    }
}
