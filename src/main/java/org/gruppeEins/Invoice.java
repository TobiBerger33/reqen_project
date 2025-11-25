package org.gruppeEins;

public class Invoice {

    private static int nextID = 0;
    private final int id;
    private final ChargingSession chargingSession;
    private final double amount;

    public Invoice(ChargingSession chargingSession) {
        this.id = ++nextID;
        this.chargingSession = chargingSession;
        this.amount = chargingSession.getTotalCost();

        // Maybe the customer's credit should be reduced here?
        // The diagram does not specify this, so I will not implement it for now.
        // It's a "business logic" decision that should be handled at a higher level,
        // probably in the InvoiceManager or the main application logic.
    }

    // Getters
    public int getId() {
        return id;
    }

    public ChargingSession getChargingSession() {
        return chargingSession;
    }

    public double getAmount() {
        return amount;
    }
}
