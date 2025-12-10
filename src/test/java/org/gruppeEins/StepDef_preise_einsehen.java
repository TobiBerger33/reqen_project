package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StepDef_preise_einsehen {

    private final LocationManager locationManager = new LocationManager();
    private Location location;

    private final Map<String, Location> locationsByName = new HashMap<>();

    private PriceCatalog returnedPriceCatalog;
    private String errorMessage;

    @Given("a location {string} exists with a price catalog")
    public void aLocationExistsWithAPriceCatalog(String locationName) {
        Address address = new Address("1200", "Hoechstaedtplatz", "Vienna", 6, "Austria");
        PriceCatalog priceCatalog = new PriceCatalog(LocalDateTime.now(), 0.45, 0.60, 0.10, 0.20);
        location = new Location(address, locationName, priceCatalog);

        try {
            locationManager.addLocation(location);
            locationsByName.put(locationName, location);
        } catch (Exception e) {
            fail("Failed to create location: " + e.getMessage());
        }
    }

    @When("I request the price information for location {string}")
    public void iRequestPriceInformationForLocation(String locationName) {
        location = locationsByName.get(locationName);
        assertNotNull(location, "Location not found: " + locationName);

        returnedPriceCatalog = location.getPriceCatalog();
    }

    @Then("I should see the kWh price AC is {double} EUR and the kWh price DC is {double} EUR")
    public void iShouldSeeTheKWhPriceACIsAndTheKWhPriceDCIs(double expectedPriceAC, double expectedPriceDC) {
        assertNotNull(returnedPriceCatalog, "No price catalog retrieved");
        assertEquals(expectedPriceAC, returnedPriceCatalog.getKWhPriceAC());

        assertNotNull(returnedPriceCatalog, "No price catalog retrieved");
        assertEquals(expectedPriceDC, returnedPriceCatalog.getKWhPriceDC());
    }

    @And("I should see the minute price AC is {double} EUR and the minute price DC is {double} EUR")
    public void iShouldSeeTheMinutePriceACIsAndTheMinutePriceDCIs(double expectedPriceAC, double expectedPriceDC) {
        assertNotNull(returnedPriceCatalog, "No price catalog retrieved");
        assertEquals(expectedPriceAC, returnedPriceCatalog.getMinutePriceAC());

        assertNotNull(returnedPriceCatalog, "No price catalog retrieved");
        assertEquals(expectedPriceDC, returnedPriceCatalog.getMinutePriceDC());
    }

    @Given("a location {string} exists without a price catalog")
    public void aLocationExistsWithoutAPriceCatalog(String locationName) {
        Address address = new Address("1200", "Hoechstaedtplatz", "Vienna", 6, "Austria");
        Location location = new Location(address, locationName, null);

        try {
            locationManager.addLocation(location);
            locationsByName.put(locationName, location);
        } catch (Exception e) {
            fail("Failed to create location: " + e.getMessage());
        }
    }

    @When("I try to request the price information for location {string}")
    public void iTryToRequestPriceInformationForLocation(String locationName) {
        Location location = locationsByName.get(locationName);
        assertNotNull(location, "Location not found: " + locationName);

        if (returnedPriceCatalog == null) {
            errorMessage = "No price catalog available for this location";
        }
    }

    @Then("I should see an error that no price catalog is available")
    public void iShouldSeeAnErrorThatNoPriceCatalogIsAvailable() {
        assertNull(returnedPriceCatalog, "Expected no price catalog but found one");
        assertEquals("No price catalog available for this location", errorMessage);
    }
}