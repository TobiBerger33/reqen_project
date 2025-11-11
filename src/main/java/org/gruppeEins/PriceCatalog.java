package org.gruppeEins;

public class PriceCatalog
{
    private int id;
    private static int nextID = 1;
    private double price;

    protected PriceCatalog(double price)
    {
        this.id = nextID++;
        this.price = price;
    }

    protected double getPrice()
    {
        return price;
    }
}
