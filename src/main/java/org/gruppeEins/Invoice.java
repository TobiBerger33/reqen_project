package org.gruppeEins;

public class Invoice
{
    private int ID;
    private static int nextID = 1;
    private ChargingSession chargingSession;
    private String name;
    private double amount;

    protected Invoice(ChargingSession chargingSession, String name, double amount)
    {
        this.ID = nextID++;
        this.chargingSession = chargingSession;
        this.name = name;
        this.amount = amount;
    }


}
