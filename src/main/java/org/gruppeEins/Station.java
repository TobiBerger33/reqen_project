package org.gruppeEins;

public class Station
{
    private int ID;
    private static int nextID = 1;
    private ChargoingType type;
    private ChargingStatus status;
    private final Location location;

    protected Station(ChargoingType type, ChargingStatus status, Location location)
    {
        this.ID = nextID++;
        this.type = type;
        this.status = status;
        this.location = location;
    }

    protected void updateStatus(ChargingStatus status)
    {
        this.status = status;
    }

    protected void updateType(ChargoingType type)
    {
        this.type = type;
    }

    protected Location getLocation()
    {
        return location;
    }
}
