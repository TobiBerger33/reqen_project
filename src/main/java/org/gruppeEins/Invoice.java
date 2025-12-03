package org.gruppeEins;

import java.io.BufferedWriter;
import java.io.FileWriter;

import static java.lang.String.format;

public class Invoice {

    private static int nextID = 0;
    private final int id;
    private final ChargingSession chargingSession;
    private final double amount;
    private boolean wasExported = false;

    public Invoice(ChargingSession chargingSession) {

        if (chargingSession.getEndTime() == null) {
            throw new IllegalArgumentException("Charging session not completed");
        }
        else {
        this.id = ++nextID;
        this.chargingSession = chargingSession;
        this.amount = chargingSession.getTotalCost();

        this.writeCSV();
        }

        // Maybe the customer's credit should be reduced here?
        // The diagram does not specify this, so I will not implement it for now.
        // It's a "business logic" decision that should be handled at a higher level,
        // probably in the InvoiceManager or the main application logic.
    }

    public Invoice(int id, ChargingSession chargingSession) {
        this.id = id;
        this.chargingSession = chargingSession;
        this.amount = chargingSession.getTotalCost();

        this.writeCSV();
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

    public boolean getWasExported()
    {
        return wasExported;
    }

    private void writeCSV()
    {
        String path = "src/main/java/org/gruppeEins/resources/invoices/";
        String fileName = String.format("ECSN-INV-%d.csv", getId());

        String fullPath = path + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath)))
        {
            writer.write(format("%d, %s, %.2f\n",
                                getId(), chargingSession.getCustomer().getName(), getAmount()));
            wasExported = true;
        }
        catch (Exception e1)
        {
            System.out.println("File writing failed. No customers set!\n");
        }
    }
}
