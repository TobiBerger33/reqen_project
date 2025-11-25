package org.gruppeEins;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ChargingSession {

    private static int nextID = 0;
    private final int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private final Station station;
    private final Customer customer;
    private final PriceSnapshot priceSnapshot;
    private final ChargingType mode;
    private double duration; // in minutes
    private double energy; // in kWh

    public ChargingSession(Station station, Customer customer) {
        this.id = ++nextID;
        this.station = station;
        this.customer = customer;
        this.mode = station.getType();
        // Create a snapshot of the prices at the beginning of the session
        this.priceSnapshot = new PriceSnapshot(station.getCurrentPrice());
    }

    public void start(LocalDateTime startTime) {
        this.startTime = startTime;
        this.station.updateStatus(ChargingStatus.IN_OPERATION_OCCUPIED);
    }

    public void end(LocalDateTime endTime, double energyConsumed) {
        this.endTime = endTime;
        this.energy = energyConsumed;
        this.station.updateStatus(ChargingStatus.IN_OPERATION_FREE);

        if (startTime != null) {
            this.duration = ChronoUnit.MINUTES.between(startTime, endTime);
        }

        calculateCost();
    }

    public double calculateCost() {
        if (priceSnapshot == null) {
            this.totalCost = 0;
            return this.totalCost;
        }

        double pricePerMinute;
        double pricePerKWh;

        if (mode == ChargingType.AC) {
            pricePerMinute = priceSnapshot.getMinutePriceAC();
            pricePerKWh = priceSnapshot.getKWhPriceAC();
        } else { // DC
            pricePerMinute = priceSnapshot.getMinutePriceDC();
            pricePerKWh = priceSnapshot.getKWhPriceDC();
        }

        this.totalCost = (duration * pricePerMinute) + (energy * pricePerKWh);
        return this.totalCost;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Station getStation() {
        return station;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PriceSnapshot getPriceSnapshot() {
        return priceSnapshot;
    }

    public ChargingType getMode() {
        return mode;
    }

    public double getDuration() {
        return duration;
    }

    public double getEnergy() {
        return energy;
    }
}
