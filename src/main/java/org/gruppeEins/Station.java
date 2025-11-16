package org.gruppeEins;

import java.util.ArrayList;

public class Station
{
    private final int id;
    protected static int nextID = 1;
    private ChargingType type;
    private ChargingStatus status;
    private final Location location;

    public Station(ChargingType type, ChargingStatus status, Location location)
    {
        this.id = nextID++;
        this.type = type;
        this.status = status;
        this.location = location;
        location.addStation(this);
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

    public void updateStatus(ChargingStatus status)
    {
        this.status = status;
    }

    public void updateType(ChargingType type)
    {
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public Location getLocation()
    {
        return location;
    }

    public ChargingStatus getStatus()
    {
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
}
