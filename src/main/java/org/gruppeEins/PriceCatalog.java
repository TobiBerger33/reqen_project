package org.gruppeEins;

public class PriceCatalog
{
    private int ID;
    private static int nextID = 1;
    private double price;

    protected PriceCatalog(double price)
    {
        this.ID = nextID++;
        this.price = price;
    }

    protected double getPrice()
    {
        return price;
    }
}
