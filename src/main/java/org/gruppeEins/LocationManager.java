package org.gruppeEins;

public class LocationManager
{
    private int ID;
    private static int nextID = 1;
    private String name;

    protected LocationManager(String name)
    {
        ID = nextID++;
        this.name = name;
    }
}
