package org.gruppeEins;

public class Station
{
    private int id;
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
        this.id = id;
        this.type = type;
        this.status = status;
        this.location = location;
        location.addStation(this);
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
}
