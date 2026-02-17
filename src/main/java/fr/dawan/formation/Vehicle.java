package fr.dawan.formation;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String brand;
    private Model model;
    private String registration;
    private Telemetry telemetry;
    private List<Incident> incidentHistories = new ArrayList<>();

    public Vehicle() {
    }

    public Vehicle(String brand, Model model, String registration, Telemetry telemetry, List<Incident> incidentHistories) {
        this.brand = brand;
        this.model = model;
        this.registration = registration;
        this.telemetry = telemetry;
        this.incidentHistories = incidentHistories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public List<Incident> getIncidentHistories() {
        return incidentHistories;
    }

    public void setIncidentHistories(List<Incident> incidentHistories) {
        this.incidentHistories = incidentHistories;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            ", registration='" + registration + '\'' +
            ", telemetry=" + telemetry +
            ", incidentHistories=" + incidentHistories +
            '}';
    }
}
