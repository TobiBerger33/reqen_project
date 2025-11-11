package org.gruppeEins;

public class PriceSnapshot
{
    private int id;
    private static  int nextID = 1;
    private double price;

    protected PriceSnapshot(double price)
    {
        id = nextID++;
        this.price = price;
    }
}
