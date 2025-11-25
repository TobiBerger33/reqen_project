package org.gruppeEins;

public class Station {

    private static int nextID = 0;
    private final int id;
    private ChargingType type;
    private ChargingStatus status;
    private Location location;

    public Station(ChargingType type, ChargingStatus status, Location location) {
        this.id = ++nextID;
        this.type = type;
        this.status = status;
        this.location = location;
        // Automatically add this new station to the location's list of stations
        if (location != null) {
            location.addStation(this);
        }
    }

    public void updateStatus(ChargingStatus status) {
        this.status = status;
    }

    public void updateType(ChargingType type) {
        this.type = type;
    }

    public PriceCatalog getCurrentPrice() {
        if (this.location != null) {
            return this.location.getPriceCatalog();
        }
        return null;
    }

    // Getters
    public int getId() {
        return id;
    }

    public ChargingType getType() {
        return type;
    }

    public ChargingStatus getStatus() {
        return status;
    }

    public Location getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(Location location) {
        // If we are moving a station, we should remove it from the old location
        if (this.location != null) {
            this.location.rmStation(this);
        }
        this.location = location;
        // And add it to the new one
        if (location != null) {
            location.addStation(this);
        }
    }
}
