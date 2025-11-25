package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationManager {

    private final List<Location> locations = new ArrayList<>();

    public void addLocation(Location location) throws Exception{
        if (location == null) {
            return;
        }

        if (location.getAddress() == null) {
            throw new Exception("Address data is missing");
        }

        locations.add(location);
    }

    public Optional<Location> getLocationById(int id) {
        return locations.stream()
                .filter(location -> location.getId() == id)
                .findFirst();
    }

    public void updateLocation(Location updatedLocation) {
        if (updatedLocation == null) {
            return;
        }
        getLocationById(updatedLocation.getId()).ifPresent(existingLocation -> {
            int index = locations.indexOf(existingLocation);
            locations.set(index, updatedLocation);
        });
    }

    public void removeLocation(int id) {
        locations.removeIf(location -> location.getId() == id);
    }

    public List<Location> getAllLocations() {
        return new ArrayList<>(locations);
    }

    @Override
    public String toString() {
        return "LocationManager{" +
                "locations=" + locations +
                '}';
    }
}
