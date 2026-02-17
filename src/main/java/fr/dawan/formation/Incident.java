package fr.dawan.formation;

import java.time.LocalDate;

public class Incident {
    private String date;
    private String type;
    private String description;

    public Incident() {
    }

    public Incident(String date, String type, String description) {
        this.date = date;
        this.type = type;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Incident{" +
            "date='" + date + '\'' +
            ", type='" + type + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
