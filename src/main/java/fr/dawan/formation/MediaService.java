package fr.dawan.formation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.*;
import java.net.URL;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Slf4j
public class MediaService {
    private final MongoCollection<Document> vehicles;
    private final GridFSBucket gridFSBucket;

    public MediaService(MongoCollection<Document> vehicles, GridFSBucket gridFSBucket) {
        this.vehicles = vehicles;
        this.gridFSBucket = gridFSBucket;
    }

    public void uploadIncidentEvidenceFile(String registration, int incidentIndex, String filePath) {
        Document vehicle = vehicles.find(eq("registration", registration)).first();

        try (InputStream is = new FileInputStream(filePath)) {
            GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1024 * 1024)
                .metadata(new Document("type", "evidence")
                .append("vehicle_id", vehicle.getObjectId("_id")));

            ObjectId fileId = gridFSBucket.uploadFromStream("evidence_" + UUID.randomUUID(), is, options);
            vehicles.updateOne(
                eq("registration", registration),
                set("incident_history." + incidentIndex + ".incident_evidence_id", fileId)
            );
            log.info("Incident evidence file uploaded");
        }
        catch (IOException e) {
            log.error("Erreur d'upload de fichier : {}", e.getMessage());
        }
    }

    public void getIncidentEvidence(String vehicleId, int incidentIndex) {
        Document vehicle = vehicles.find(eq("_id", new ObjectId(vehicleId))).first();
        try (OutputStream os = new FileOutputStream("incident_downloaded.jpg")) {
            gridFSBucket.downloadToStream(
                vehicle.getList("incident_history", Document.class)
                    .get(incidentIndex)
                    .getObjectId("incident_evidence_id"),
                os
            );
            log.info("Fichier téléchargé !");
        }
        catch (IOException e) {
            log.error("Erreur d'upload de fichier : {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        MediaService mediaService;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27018")) {
            MongoDatabase database = mongoClient.getDatabase("eco_drive_db");
            MongoCollection<Document> vehicleCollection = database.getCollection("vehicles");
            mediaService = new MediaService(vehicleCollection, GridFSBuckets.create(database));

            URL resource = MediaService.class.getClassLoader().getResource("incident_evidence_2.jpg");
            if (resource != null) {
                // Put the hyundai registration here
                mediaService.uploadIncidentEvidenceFile("CG-781-QA", 0, resource.getPath());
            }

            // Put the hyundai id here
            mediaService.getIncidentEvidence("699485bc10bfc80840d4219b", 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erreur de connexion : {}", e.getMessage());
        }
    }
}
