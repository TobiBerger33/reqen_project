package org.gruppeEins;

public class Station
{
    private int id;
    protected static int nextID = 1;
    private ChargingType type;
    private ChargingStatus status;
    private final Location location;

    protected Station(ChargingType type, ChargingStatus status, Location location)
    {
        this.id = nextID++;
        this.type = type;
        this.status = status;
        this.location = location;
    }

    protected void updateStatus(ChargingStatus status)
    {
        this.status = status;
    }

    protected void updateType(ChargingType type)
    {
        this.type = type;
    }

    protected Location getLocation()
    {
        return location;
    }
}
