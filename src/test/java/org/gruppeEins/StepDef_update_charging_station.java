package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.Optional;

public class StepDef_update_charging_station {

    private StationManager stationManager = new StationManager();
    private LocationManager locationManager = new LocationManager();
    private Location testLocation;
    private Location newLocationForUpdate;
    private Exception thrownException;

    @Given("a station manager with a station with ID {int}, type {string}, status {string}, and location {string}")
    public void a_station_manager_with_a_station_with_id_type_status_and_location(
            int id, String type, String status, String locationName) {
        Address address = new Address("12345", "Test Street", "Test City", 1, "Test Country");
        PriceCatalog priceCatalog = new PriceCatalog(LocalDateTime.now(), 0.5, 0.75, 0.1, 0.2);
        testLocation = new Location(address, locationName, priceCatalog);
        try {
            locationManager.addLocation(testLocation);
        } catch (Exception e) {
            // Handle exception, e.g., log it or rethrow as a RuntimeException
            throw new RuntimeException("Failed to add location: " + e.getMessage(), e);
        }
        Location managedLocation = locationManager.getLocationByName(locationName)
                .orElseThrow(() -> new AssertionError("Location not found in manager after adding"));
        ChargingType chargingType = ChargingType.valueOf(type.toUpperCase());
        ChargingStatus chargingStatus = ChargingStatus.valueOf(status.toUpperCase());
        Station station = new Station(id, chargingType, chargingStatus, managedLocation);
        stationManager.addStation(station);
    }

    @When("I update the station with ID {int} to type {string}")
    public void i_update_the_station_with_id_to_type(int id, String newType) {
        ChargingType chargingType = ChargingType.valueOf(newType.toUpperCase());
        stationManager.updateStationType(id, chargingType);
    }

    @Then("the station with ID {int} should have type {string}")
    public void the_station_with_id_should_have_type(int id, String expectedType) {
        Station station = stationManager.getStationById(id)
                .orElseThrow(() -> new AssertionError("Station not found"));
        Assertions.assertEquals(ChargingType.valueOf(expectedType.toUpperCase()), station.getType());
    }

    @When("I update the station with ID {int} to status {string}")
    public void i_update_the_station_with_id_to_status(int id, String newStatus) {
        ChargingStatus chargingStatus = ChargingStatus.valueOf(newStatus.toUpperCase());
        stationManager.updateStationStatus(id, chargingStatus);
    }

    @Then("the station with ID {int} should have status {string}")
    public void the_station_with_id_should_have_status(int id, String expectedStatus) {
        Station station = stationManager.getStationById(id)
                .orElseThrow(() -> new AssertionError("Station not found"));
        Assertions.assertEquals(ChargingStatus.valueOf(expectedStatus.toUpperCase()), station.getStatus());
    }

    @Given("a new location {string}")
    public void a_new_location(String locationName) {
        Address address = new Address("54321", "New Street", "New City", 2, "New Country");
        PriceCatalog priceCatalog = new PriceCatalog(LocalDateTime.now(), 0.6, 0.85, 0.15, 0.25);
        newLocationForUpdate = new Location(address, locationName, priceCatalog);
        try {
            locationManager.addLocation(newLocationForUpdate);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add new location for update: " + e.getMessage(), e);
        }
    }

    @When("I update the station with ID {int} to location {string}")
    public void i_update_the_station_with_id_to_location(int id, String newLocationName) {
        Location location = locationManager.getLocationByName(newLocationName)
                .orElseThrow(() -> new AssertionError("New location not found"));
        stationManager.updateStationLocation(id, location);
    }

    @Then("the station with ID {int} should have location {string}")
    public void the_station_with_id_should_have_location(int id, String expectedLocationName) {
        Station station = stationManager.getStationById(id)
                .orElseThrow(() -> new AssertionError("Station not found"));
        Assertions.assertEquals(expectedLocationName, station.getLocation().getName());
    }
    @Given("a station manager with no stations")
    public void a_station_manager_with_no_stations() {
        stationManager = new StationManager();
    }

    @When("I attempt to update the station with ID {int} to type {string}")
    public void i_attempt_to_update_the_station_with_id_to_type(int id, String newType) {
        try {
            ChargingType chargingType = ChargingType.valueOf(newType.toUpperCase());
            stationManager.updateStationType(id, chargingType);
        } catch (IllegalArgumentException e) {
            thrownException = e;
        }
    }

    @Then("no station with ID {int} should exist")
    public void no_station_with_id_should_exist(int id) {
        Assertions.assertFalse(stationManager.getStationById(id).isPresent());
    }

    @Then("an error should occur")
    public void an_error_should_occur() {
        Assertions.assertNotNull(thrownException);
    }

    @When("I attempt to update the station with ID {int} to a non-existent location {string}")
    public void i_attempt_to_update_the_station_with_id_to_a_non_existent_location(int id, String nonExistentLocationName) {
        Optional<Location> nonExistentLocation = locationManager.getLocationByName(nonExistentLocationName);
        if (!nonExistentLocation.isPresent()) {
            // This is the expected case, so we don't update and let the then step verify
        } else {
            stationManager.updateStationLocation(id, nonExistentLocation.get());
        }
    }

    @Then("the location of station with ID {int} should remain {string}")
    public void the_location_of_station_with_id_should_remain(int id, String expectedLocationName) {
        Station station = stationManager.getStationById(id)
                .orElseThrow(() -> new AssertionError("Station not found"));
        Assertions.assertEquals(expectedLocationName, station.getLocation().getName());
    }
}
