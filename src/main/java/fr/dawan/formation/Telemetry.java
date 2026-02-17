package fr.dawan.formation;

public class Telemetry {
    private double latitude;
    private double longitude;
    private float batteryLevel;

    public Telemetry() {
    }

    public Telemetry(double latitude, double longitude, float batteryLevel) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryLevel = batteryLevel;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public String toString() {
        return "Telemetry{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            ", batteryLevel=" + batteryLevel +
            '}';
    }
}
