package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Location {

    private static int nextID = 0;
    private final int id;
    private Address address;
    private String name;
    private PriceCatalog priceCatalog;
    private final List<Station> stations = new ArrayList<>();

    public Location(Address address, PriceCatalog priceCatalog) {
        this.id = ++nextID;
        this.address = address;
        this.priceCatalog = priceCatalog;
    }

    public Location(Address address, String name, PriceCatalog priceCatalog) {
        this.id = ++nextID;
        this.address = address;
        this.name = name;
        this.priceCatalog = priceCatalog;
    }

    public Location(int id, Address address, PriceCatalog priceCatalog) {
        this.id = id;
        this.address = address;
        this.priceCatalog = priceCatalog;
    }

    public void addStation(Station station) {
        if (station != null && !stations.contains(station)) {
            stations.add(station);
        }
    }

    public void rmStation(Station station) {
        stations.remove(station);
    }

    // The diagram shows updateLocation(), which could mean updating the address or price catalog
    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updatePriceCatalog(PriceCatalog priceCatalog) {

        if (priceCatalog == null) {
            throw new IllegalArgumentException("Please use a valid price catalog");
        }

            if (validPrices(priceCatalog)) {
                this.priceCatalog = priceCatalog;
            }
        }

    // Getters
    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public String getName()
    {
        return name;
    }

    public PriceCatalog getPriceCatalog() {
        return priceCatalog;
    }

    public List<Station> getStations() {
        // Return a copy to prevent external modification
        return new ArrayList<>(stations);
    }

    public Optional<Station> getStationById(int id) {
        return stations.stream()
                .filter(station -> station.getId() == id)
                .findFirst();
    }

    // Setters
    public void setAddress(Address address) {
        if(address == null) {
            throw new IllegalArgumentException("Please use a valid address");
        } else {
            this.address = address;
        }
    }

    private boolean validPrices(PriceCatalog priceCatalog) {

        if (priceCatalog.getKWhPriceAC() == 0)
            throw new IllegalArgumentException("Price for kWh AC must not be 0");
        else if (priceCatalog.getKWhPriceDC() == 0)
            throw new IllegalArgumentException("Price for kWh DC must not be 0");
        else if (priceCatalog.getMinutePriceAC() == 0)
            throw new IllegalArgumentException("Price for minute AC must not be 0");
        else if (priceCatalog.getMinutePriceDC() == 0)
            throw new IllegalArgumentException("Price for minute DC must not be 0");

        return true;
    }
}
