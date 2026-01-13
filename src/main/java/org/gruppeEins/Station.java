package org.gruppeEins;

import java.util.ArrayList;

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

    public Station(int id, ChargingType type, ChargingStatus status, Location location)
    {
        if(location != null)
        {
            if(ableToAdd(id, location))
            {
                this.id = id;
                this.type = type;
                this.status = status;
                this.location = location;
                location.addStation(this);
            }
            else
            {
                throw new IllegalArgumentException("Charging point identifier already exists at this location");
            }
        }
        else
        {
            throw new NullPointerException("Location not found");
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

    private boolean ableToAdd(int id, Location location)
    {
        ArrayList<Station> stations = (ArrayList<Station>) location.getStations();

        for(Station station : stations)
        {
            if(station.getId() == id)
            {
                return false;
            }
        }

        return true;
    }

    public Location getLocation() {
        return location;
    }

    public String printStatus() {
        String statusTxt;

        switch (status) {
            case IN_OPERATION_FREE:
                statusTxt = "IN OPERATION and FREE";
                break;
            case IN_OPERATION_OCCUPIED:
                statusTxt = "IN OPERATION but OCCUPIED";
                break;
            default:
                statusTxt = "OUT OF ORDER";
        }

        String message = "Station " + id + " is " + statusTxt;

        return message;
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

    @Override
    public String toString() {
        String locationName = (location != null) ? location.getName() : "No location";
        return "Station{id=" + id + ", type=" + type + ", status=" + status + ", location='" + locationName + "'}";
    }
}
