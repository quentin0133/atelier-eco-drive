package fr.dawan.formation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    public static void main( String[] args )
    {
        VehicleService vehicleService;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27018")) {
            MongoDatabase database = mongoClient.getDatabase("eco_drive_db");
            vehicleService = new VehicleService(database.getCollection("vehicles"));
        } catch (Exception e) {
            log.error("Erreur de connexion : {}", e.getMessage());
        }
    }
}
