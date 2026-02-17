package fr.dawan.formation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Slf4j
public class VehicleService {
    private final MongoCollection<Document> vehicles;

    public VehicleService(MongoCollection<Document> vehicles) {
        this.vehicles = vehicles;
    }

    public Document registerVehicle(Vehicule vehicule) {
        Document lastPos = new Document("latitude", vehicule.getTelemetry().getLatitude())
            .append("longitude", vehicule.getTelemetry().getLongitude());
        Document telemetry = new Document("last_position", lastPos)
            .append("battery_level", vehicule.getTelemetry().getBatteryLevel());

        List<Document> incidentHistories = new ArrayList<>();
        for (Incident incident : vehicule.getIncidentHistories()) {
            incidentHistories.add(
                new Document("date", incident.getDate())
                    .append("type", incident.getType())
                    .append("description", incident.getDescription())
            );
        }

        Document vehicle = new Document("brand", vehicule.getBrand())
            .append("model", vehicule.getModel())
            .append("registration", vehicule.getRegistration())
            .append("telemetry", telemetry)
            .append("incident_history", incidentHistories);

        ObjectId vehiculeId = vehicles.insertOne(vehicle).getInsertedId().asObjectId().getValue();
        vehicle.append("_id", vehiculeId);

        return vehicle;
    }

    public boolean updateTelemetry(String registration, Telemetry telemetry) {
        UpdateResult result = vehicles.updateOne(
            eq("registration", registration),
            combine(
                set("telemetry.battery_level", telemetry.getBatteryLevel()),
                set("telemetry.last_position.latitude", telemetry.getLatitude()),
                set("telemetry.last_position.longitude", telemetry.getLongitude())
            )
        );

        return result.getModifiedCount() > 0;
    }

    public boolean reportIncident(String registration, Incident incident) {
        Document update = new Document("date", incident.getDate())
            .append("type", incident.getType())
            .append("description", incident.getDescription());

        UpdateResult result = vehicles.updateOne(eq("registration", registration), push("incident_history", update));

        return result.getModifiedCount() > 0;
    }

    public List<Document> getLowBatteryVehicle() {
        return vehicles.find(
            and(lt("telemetry.battery_level", 20), exists("incident_history.3"))
        ).into(new ArrayList<>());
    }

    public static void main(String[] args) {
        VehicleService vehicleService;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27018")) {
            MongoDatabase database = mongoClient.getDatabase("eco_drive_db");
            MongoCollection<Document> vehiculeCollection = database.getCollection("vehicles");
            vehicleService = new VehicleService(vehiculeCollection);

            Telemetry telemetry = new Telemetry();
            telemetry.setLatitude(28.45155d);
            telemetry.setLongitude(1.41541d);
            telemetry.setBatteryLevel(100f);

            Vehicule vehicule = new Vehicule();
            vehicule.setBrand("Hyundai");
            vehicule.setModel("Kona");
            vehicule.setRegistration("CG-781-QA");
            vehicule.setTelemetry(telemetry);
            vehicule.setIncidentHistories(List.of(
                new Incident("18/05/2015", "accident", "Un arbre est tombé sur la voiture"),
                new Incident("12/12/2012", "panne", "Panne d'électricité"),
                new Incident("20/01/2020", "vol", "Pierre a volé ma voiture")
            ));

            Document vehiculeDocument = vehicleService.registerVehicle(vehicule);
            log.info("Véhicule ajouté : {}", vehiculeDocument.toJson());

            Telemetry telemetryUpdate = new Telemetry();
            telemetryUpdate.setLatitude(28.45141d);
            telemetryUpdate.setLongitude(1.4142d);
            telemetryUpdate.setBatteryLevel(5.05f);

            boolean hasUpdated = vehicleService.updateTelemetry("CG-781-QA", telemetryUpdate);
            log.info("Véhicule mis à jour telemetry ? : {}", hasUpdated);
            log.info("Véhicule : {}", vehiculeCollection.find(eq("registration", "CG-781-QA")).first());

            Incident incident = new Incident("17/02/2026", "accident", "La voiture s'est pris un poteau");
            boolean hasAddedIncident = vehicleService.reportIncident("CG-781-QA", incident);
            log.info("Véhicule ajouté un incident ? : {}", hasAddedIncident);
            log.info("Véhicule : {}", vehiculeCollection.find(eq("registration", "CG-781-QA")).first());

            log.info("Véhicule avec plus de 2 incident et moins de 20% de batterie");
            vehicleService.getLowBatteryVehicle().forEach(vehicle -> log.info(vehicle.toString()));
            log.info("Taille de la liste : {}", vehicleService.getLowBatteryVehicle().size());
        } catch (Exception e) {
            log.error("Erreur de connexion : {}", e.getMessage());
        }
    }
}
