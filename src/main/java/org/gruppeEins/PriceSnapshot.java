package org.gruppeEins;

import java.time.LocalDateTime;

public class PriceSnapshot {

    private static int nextID = 0;
    private final int id;
    private final double kWhPriceAC;
    private final double kWhPriceDC;
    private final double minutePriceAC;
    private final double minutePriceDC;
    private final LocalDateTime capturedAt;

    public PriceSnapshot(PriceCatalog priceCatalog) {
        this.id = ++nextID;
        this.kWhPriceAC = priceCatalog.getKWhPriceAC();
        this.kWhPriceDC = priceCatalog.getKWhPriceDC();
        this.minutePriceAC = priceCatalog.getMinutePriceAC();
        this.minutePriceDC = priceCatalog.getMinutePriceDC();
        this.capturedAt = LocalDateTime.now();
    }

    // A constructor that takes values directly might also be useful for testing or other scenarios.
    public PriceSnapshot(double kWhPriceAC, double kWhPriceDC, double minutePriceAC, double minutePriceDC, LocalDateTime capturedAt) {
        this.id = ++nextID;
        this.kWhPriceAC = kWhPriceAC;
        this.kWhPriceDC = kWhPriceDC;
        this.minutePriceAC = minutePriceAC;
        this.minutePriceDC = minutePriceDC;
        this.capturedAt = capturedAt;
    }


    // Getters
    public int getId() {
        return id;
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

    public LocalDateTime getCapturedAt() {
        return capturedAt;
    }
}
