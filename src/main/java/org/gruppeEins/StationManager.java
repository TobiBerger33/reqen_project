package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationManager {

    private final List<Station> stations = new ArrayList<>();

    public void addStation(Station station) {
        if (station != null) {
            stations.add(station);
        }
    }

    public Optional<Station> getStationById(int id) {
        return stations.stream()
                .filter(station -> station.getId() == id)
                .findFirst();
    }

    public void updateStation(Station updatedStation) {
        if (updatedStation == null) {
            return;
        }
        getStationById(updatedStation.getId()).ifPresent(existingStation -> {
            int index = stations.indexOf(existingStation);
            stations.set(index, updatedStation);
        });
    }

    public void removeStation(int id) {
        stations.removeIf(station -> station.getId() == id);
    }

    public void updateStationType(int stationId, ChargingType newType) {
        getStationById(stationId).ifPresent(station -> station.updateType(newType));
    }

    public void updateStationStatus(int stationId, ChargingStatus newStatus) {
        getStationById(stationId).ifPresent(station -> station.updateStatus(newStatus));
    }

    public void updateStationLocation(int stationId, Location newLocation) {
        getStationById(stationId).ifPresent(station -> station.setLocation(newLocation));
    }

    public List<Station> getAllStations() {
        return new ArrayList<>(stations);
    }
    
    @Override
    public String toString() {
        return "StationManager{" +
                "stations=" + stations +
                '}';
    }
}
