package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StepDef_standortspezifische_preise_definieren {

    private LocationManager locationManager = new LocationManager();
    private PriceCatalogManager priceCatalogManager = new PriceCatalogManager();
    private StationManager stationManager = new StationManager();

    private Map<String, Location> locationsByName = new HashMap<>();
    private Map<Integer, Station> stationsById = new HashMap<>();

    private PriceCatalog returnedPriceCatalog;

    @Given("I create a location {string} with address {string} in {string}")
    public void iCreateALocationWithAddress(String locationName, String street, String city) {
        Address address = new Address("1200", street, city, 6, "Austria");
        Location location = new Location(address, locationName, null);

        try {
            locationManager.addLocation(location);
            locationsByName.put(locationName, location);
        } catch (Exception e) {
            fail("Failed to create location: " + e.getMessage());
        }
    }

    @When("I assign a price catalog with {double} EUR per kWh AC and {double} EUR per kWh DC to location {string}")
    public void iAssignAPriceCatalogToLocation(double acPrice, double dcPrice, String locationName) {
        PriceCatalog priceCatalog = new PriceCatalog(LocalDateTime.now(), acPrice, dcPrice, 0.10, 0.10);

        priceCatalogManager.addPriceCatalog(priceCatalog);

        Location location = locationsByName.get(locationName);
        location.setPriceCatalog(priceCatalog);
    }

    @Then("location {string} should have {double} EUR per kWh AC")
    public void locationShouldHaveACPrice(String locationName, double expectedPrice) {
        Location location = locationsByName.get(locationName);
        assertNotNull(location, "Location not found: " + locationName);

        PriceCatalog catalog = location.getPriceCatalog();
        assertNotNull(catalog, "PriceCatalog not found");

        assertEquals(expectedPrice, catalog.getKWhPriceAC(), "AC price does not match for location " + locationName);
    }

    @And("location {string} should have {double} EUR per kWh DC")
    public void locationShouldHaveDCPrice(String locationName, double expectedPrice) {
        Location location = locationsByName.get(locationName);
        assertNotNull(location, "Location not found: " + locationName);

        PriceCatalog catalog = location.getPriceCatalog();
        assertNotNull(catalog, "PriceCatalog not found");

        assertEquals(expectedPrice, catalog.getKWhPriceDC(), "DC price does not match for location " + locationName);
    }

    //Given is already implemented (same as Scenario 1)

    @When("a station with ID {int} is added to location {string}")
    public void stationIsAddedToLocation(int stationId, String locationName) {
        Location location = locationsByName.get(locationName);
        assertNotNull(location, "Location not found: " + locationName);

        Station station = new Station(stationId, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);

        stationManager.addStation(station);
        stationsById.put(stationId, station);
    }

    @And("I try to get the current prices from station {int}")
    public void iTryToGetTheCurrentPricesFromStation(int stationId) {
        Station station = stationsById.get(stationId);
        assertNotNull(station, "Station not found: " + stationId);

        returnedPriceCatalog = station.getCurrentPrice();
    }

    @Then("the station should return an error")
    public void theStationShouldReturnNoPriceCatalog() {
        assertNull(returnedPriceCatalog, "Price catalog found although not set yet: Add PriceCatalog!!!");
    }
}
