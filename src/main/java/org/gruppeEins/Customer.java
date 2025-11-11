package org.gruppeEins;

public class Customer
{
    private int id;
    private static int nextID = 1;
    private String name;
    private double credit;

    protected Customer(String name, double credit)
    {
        this.id = nextID++;
        this.name = name;
        this.credit = credit;
    }
}
