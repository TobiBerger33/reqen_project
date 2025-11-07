package org.gruppeEins;

public class Customer
{
    private int ID;
    private static int nextID = 1;
    private String name;
    private double credit;

    protected Customer(String name, double credit)
    {
        this.ID = nextID++;
        this.name = name;
        this.credit = credit;
    }
}
