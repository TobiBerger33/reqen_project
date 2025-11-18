package org.gruppeEins;

import java.util.HashMap;

public class LocationManager
{
    private int id;
    private static int nextID = 1;
    private String name;
    private HashMap<String, Location> locations = new HashMap<>();

    protected LocationManager(String name)
    {
        id = nextID++;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void addLocation(Location location)
    {
        locations.put(location.getName(), location);
    }

    public Location getLocations(String name)
    {
        return locations.get(name);
    }
}
