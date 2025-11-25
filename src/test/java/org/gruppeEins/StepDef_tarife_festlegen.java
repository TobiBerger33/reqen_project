package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StepDef_tarife_festlegen {

    private PriceCatalogManager priceCatalogManager;
    private LocationManager locationManager;
    private StationManager stationManager;

    private PriceCatalog currentPriceCatalog;
    private Location currentLocation;

    private double tempKWhPriceAC;
    private double tempKWhPriceDC;
    private double tempMinutePriceAC;
    private double tempMinutePriceDC;
    private LocalDateTime tempValidFrom;

    public StepDef_tarife_festlegen() {
        this.priceCatalogManager = new PriceCatalogManager();
        this.locationManager = new LocationManager();
        this.stationManager = new StationManager();
    }

    @Given("I want to create a new price catalog and define rates for charging")
    public void iWantToCreateANewPriceCatalogAndDefineRatesForCharging() {
        //this sentence exists just for the "Given" keyword, it doesn't implement any logic
    }

    @When("I create a new price catalog with {double} EUR per kWh AC and {double} EUR per kWh DC")
    public void i_create_a_new_price_catalog_with_eur_per_kwh_ac_and_eur_per_kwh_dc(Double kWhPriceAC, Double kWhPriceDC) {
        this.tempKWhPriceAC = kWhPriceAC;
        this.tempKWhPriceDC = kWhPriceDC;
    }

    @And("I set the minute prices to {double} EUR for AC and {double} EUR for DC")
    public void i_set_the_minute_prices_to_eur_for_ac_and_eur_for_dc(Double minutePriceAC, Double minutePriceDC) {
        this.tempMinutePriceAC = minutePriceAC;
        this.tempMinutePriceDC = minutePriceDC;
    }

    @And("I set the validity date to {int}-{int}-{int}")
    public void i_set_the_validity_date_to(Integer year, Integer month, Integer day) {
        this.tempValidFrom = LocalDateTime.of(year, month, day, 0, 0, 0);
        currentPriceCatalog = new PriceCatalog(tempValidFrom, tempKWhPriceAC, tempKWhPriceDC, tempMinutePriceAC, tempMinutePriceDC);
    }

    @Then("the price catalog should be saved successfully")
    public void the_price_catalog_should_be_saved_successfully() {
        assertTrue(currentPriceCatalog.getId() > 0, "Price catalog should have a valid ID");
        assertEquals(tempKWhPriceAC, currentPriceCatalog.getKWhPriceAC());
        assertEquals(tempKWhPriceDC, currentPriceCatalog.getKWhPriceDC());
        assertEquals(tempMinutePriceAC, currentPriceCatalog.getMinutePriceAC());
        assertEquals(tempMinutePriceDC, currentPriceCatalog.getMinutePriceDC());
        assertEquals(tempValidFrom, currentPriceCatalog.getValidFrom());
    }

    @And("the price catalog should be added to the PriceCatalogManager")
    public void the_price_catalog_should_be_added_to_the_price_catalog_manager() {
        priceCatalogManager.addPriceCatalog(currentPriceCatalog);

        PriceCatalog retrieved = priceCatalogManager.getPriceCatalogById(currentPriceCatalog.getId()).orElse(null);
        assertEquals(currentPriceCatalog.getId(), retrieved.getId());
    }

    @Given("a price catalog exists with {double} EUR per kWh AC and {double} EUR per kWh DC")
    public void a_price_catalog_exists_with_eur_per_kwh_ac_and_eur_per_kwh_dc(Double kWhPriceAC, Double kWhPriceDC) {
        this.tempKWhPriceAC = kWhPriceAC;
        this.tempKWhPriceDC = kWhPriceDC;
    }

    @And("the price catalog has minute prices of {double} EUR for AC and {double} EUR for DC")
    public void the_price_catalog_has_minute_prices_of_eur_for_ac_and_eur_for_dc(Double minutePriceAC, Double minutePriceDC) {
        this.tempMinutePriceAC = minutePriceAC;
        this.tempMinutePriceDC = minutePriceDC;

        currentPriceCatalog = new PriceCatalog(LocalDateTime.now(), tempKWhPriceAC, tempKWhPriceDC,
                tempMinutePriceAC, tempMinutePriceDC);
        priceCatalogManager.addPriceCatalog(currentPriceCatalog);
    }

    @And("a location with {int} stations exists")
    public void a_location_with_stations_exists(Integer stationCount) {
        Address address = new Address("1200", "Hoechstaedtplatz", "Vienna", 6, "Austria");

        currentLocation = new Location(address, currentPriceCatalog);
        locationManager.addLocation(currentLocation);

        for (int i = 0; i < stationCount; i++) {
            Station station = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, currentLocation);
            stationManager.addStation(station);
        }
    }

    @When("I assign the price catalog to the location")
    public void i_assign_the_price_catalog_to_the_location() {
        currentLocation.setPriceCatalog(currentPriceCatalog);
        locationManager.updateLocation(currentLocation);
    }

    @Then("the location should reference the price catalog")
    public void the_location_should_reference_the_price_catalog() {
        PriceCatalog assignedCatalog = currentLocation.getPriceCatalog();
        assertEquals(currentPriceCatalog.getId(), assignedCatalog.getId());
    }

    @And("all stations at the location should inherit this price catalog")
    public void all_stations_at_the_location_should_inherit_this_price_catalog() {
        for (Station station : currentLocation.getStations()) {
            PriceCatalog stationCatalog = station.getCurrentPrice();
            assertEquals(currentPriceCatalog.getId(), stationCatalog.getId(),
                    "Station's catalog should match location's catalog");
        }
    }
}