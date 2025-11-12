package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;

public class Location
{
    private int id;
    private static int nextID = 1;
    private LocationManager manager;
    private String name;
    private Address address;
    private PriceCatalog priceCatalog;
    private List<Station> stations = new ArrayList<>();

    public Location(LocationManager manager, String name, Address address, PriceCatalog priceCatalog)
    {
        this.id = nextID;
        this.manager = manager;
        this.name = name;
        this.address = address;
        this.priceCatalog = priceCatalog;
        manager.addLocation(this);
    }

    public String getName()
    {
        return name;
    }

    public PriceCatalog getPriceCatalog()
    {
        return priceCatalog;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }
}
