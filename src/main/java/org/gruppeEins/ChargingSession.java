package org.gruppeEins;

public class ChargingSession
{
    private int id;
    private static int nextID = 1;
    private String name;
    private double cost;
    private Station station;
    private Customer customer;
    private PriceSnapshot priceSnapshot;

    protected ChargingSession(String name, double cost, Customer customer, Station station)
    {
        this.id = nextID++;
        this.name = name;
        this.cost = cost;
        this.customer = customer;
        this.priceSnapshot = new PriceSnapshot(station.getLocation().getPriceCatalog().getPrice());
    }

}
