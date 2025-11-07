package org.gruppeEins;

public class PriceSnapshot
{
    private int ID;
    private static  int nextID = 1;
    private double price;

    protected PriceSnapshot(double price)
    {
        ID = nextID++;
        this.price = price;
    }
}
