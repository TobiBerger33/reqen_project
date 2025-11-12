package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;

public class LocationManager {

    private final List<Location> locations = new ArrayList<>();
    private HashMap<String, Location> locations = new HashMap<>();

    public void addLocation(Location location) {
        if (location != null) {
            locations.add(location);
        }
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

//    public String getName()
//    {
//        return name;
//    }
//
//    public void addLocation(Location location)
//    {
//        locations.put(location.getName(), location);
//    }
//
//    public Location getLocations(String name)
//    {
//        return locations.get(name);
//    }
}
