package org.gruppeEins;

public class LocationManager
{
    private int id;
    private static int nextID = 1;
    private String name;

    protected LocationManager(String name)
    {
        id = nextID++;
        this.name = name;
    }
}
