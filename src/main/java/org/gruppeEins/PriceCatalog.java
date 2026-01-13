package org.gruppeEins;

import java.time.LocalDateTime;

public class PriceCatalog {

    private static int nextID = 0;
    private final int id;
    private LocalDateTime validFrom;
    private double kWhPriceAC;
    private double kWhPriceDC;
    private double minutePriceAC;
    private double minutePriceDC;

    public PriceCatalog(LocalDateTime validFrom, double kWhPriceAC, double kWhPriceDC, double minutePriceAC, double minutePriceDC) {
        this.id = ++nextID;
        this.validFrom = validFrom;
        this.kWhPriceAC = kWhPriceAC;
        this.kWhPriceDC = kWhPriceDC;
        this.minutePriceAC = minutePriceAC;
        this.minutePriceDC = minutePriceDC;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public double getKWhPriceAC() {
        return kWhPriceAC;
    }

    public double getKWhPriceDC() {
        return kWhPriceDC;
    }

    public double getMinutePriceAC() {
        return minutePriceAC;
    }

    public double getMinutePriceDC() {
        return minutePriceDC;
    }

    // It might be useful to have setters to update prices
    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setKWhPriceAC(double kWhPriceAC) {
        this.kWhPriceAC = kWhPriceAC;
    }

    public void setKWhPriceDC(double kWhPriceDC) {
        this.kWhPriceDC = kWhPriceDC;
    }

    public void setMinutePriceAC(double minutePriceAC) {
        this.minutePriceAC = minutePriceAC;
    }

    public void setMinutePriceDC(double minutePriceDC) {
        this.minutePriceDC = minutePriceDC;
    }

    @Override
    public String toString() {
        return "PriceCatalog{id=" + id +
                ", AC: " + kWhPriceAC + " EUR/kWh, " + minutePriceAC + " EUR/min" +
                ", DC: " + kWhPriceDC + " EUR/kWh, " + minutePriceDC + " EUR/min}";
    }
}
