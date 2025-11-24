package org.gruppeEins;

public class Station
{
    private int id;
    private static int nextID = 1;
    private ChargingType type;
    private ChargingStatus status;
    private final Location location;

    public Station(ChargingType type, ChargingStatus status, Location location)
    {
        this.id = nextID++;
        this.type = type;
        this.status = status;
        this.location = location;
    }

    public Station(int id, ChargingType type, ChargingStatus status, Location location)
    {
        this.id = id;
        this.type = type;
        this.status = status;
        this.location = location;
    }

    public ChargingStatus getStatus()
    {
        return status;
    }

    public void updateStatus(ChargingStatus status)
    {
        this.status = status;
    }

    public void updateType(ChargingType type)
    {
        this.type = type;
    }

    public Location getLocation()
    {
        return location;
    }

    public String printStatus()
    {
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
}
