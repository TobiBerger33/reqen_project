package org.gruppeEins;

public class Location
{
    private int id;
    private static int nextID = 1;
    private LocationManager manager;
    private Address address;
    private PriceCatalog priceCatalog;

    public Location(LocationManager manager, Address address, PriceCatalog priceCatalog)
    {
        this.id = nextID;
        this.manager = manager;
        this.address = address;
        this.priceCatalog = priceCatalog;
    }

    public PriceCatalog getPriceCatalog()
    {
        return priceCatalog;
    }
}
