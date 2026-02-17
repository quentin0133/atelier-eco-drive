package fr.dawan.formation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
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
            .append("incident_history", incidentHistories.toArray());

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

    public List<Document> getIncidentHistory(String registration) {
        return vehicles.find(eq("registration", registration)).into(new ArrayList<>());
    }

    public List<Document> getLowBatteryVehicle() {
        return vehicles.find(
            and(lt("telemetry.battery_level", 20), exists("incident_history.3"))
        ).into(new ArrayList<>());
    }
}
