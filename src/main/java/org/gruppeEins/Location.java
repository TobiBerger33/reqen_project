package org.gruppeEins;

public class Location
{
    private int ID;
    private static int nextID = 1;
    private LocationManager manager;
    private Address address;
    private PriceCatalog priceCatalog;

    public Location(int ID, LocationManager manager, Address address, PriceCatalog priceCatalog)
    {
        this.ID = ID;
        this.manager = manager;
        this.address = address;
        this.priceCatalog = priceCatalog;
    }

    public PriceCatalog getPriceCatalog()
    {
        return priceCatalog;
    }
}
