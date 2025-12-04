package org.gruppeEins;

import io.cucumber.java.PendingException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;


public class StepDef_update_location
{

    private LocationManager locationManager = new LocationManager();
    private Location location;
    private Optional<Location> currentLocation;
    private Address address;
    private Address newAddress;
    private String currentStreet;
    private String currentCity;
    private PriceCatalog priceCat;
    private PriceCatalog newPriceCat;
    private PriceCatalog currentPriceCat;
    private Station station;
    private Optional<Station> currentStation;

    @Given("a location with ID {int} and address with street {string} and city {string} exists in the system")
    public void aLocationWithAnAddressWithStreetAndCityExistsInTheSystem(int id, String street, String city)
    {
        address = new Address("1200", street, city, 6, "Austria");
        priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);

        location = new Location(id, address, priceCat);

        try {
            locationManager.addLocation(location);
        } catch (Exception ignore)
        {
        }
    }

    @When("I update the location with ID {int} and change the address to a new address with street {string} in {string}")
    public void iUpdateTheLocationsAddressToANewAddressWithStreetIn(int id, String street, String city)
    {
        newAddress = new Address("1200", street, city, 25, "Austria");

        currentLocation = locationManager.getLocationById(id);

        currentLocation.ifPresent(location -> location.setAddress(newAddress));
    }

    @Then("the new address is saved as the locations address")
    public void theNewAddressIsSavedAsTheLocationsAddress()
    {
        currentStreet = currentLocation.orElse(null).getAddress().getStreet();
        currentCity = currentLocation.orElse(null).getAddress().getCity();
    }

    @And("the locations address is {string} in {string}")
    public void theLocationsAddressIsIn(String street, String city)
    {
        assertEquals(currentStreet, street);
        assertEquals(currentCity, city);
    }

    @When("I try to change the address of location with ID {int} to a non existing one")
    public void iTryToChangeTheAddressToANonExistingOne(int id)
    {
        newAddress = null;

        currentLocation = locationManager.getLocationById(id);

        try
        {
            currentLocation.ifPresent(location -> location.setAddress(newAddress));
        } catch (Exception ignored)
        {}
    }

    @Then("the address is not changed")
    public void theAddressIsNotChanged()
    {
        assertEquals(address, currentLocation.orElse(null).getAddress());
    }

    @And("the locations address is still {string} in {string}")
    public void theLocationsAddressIsStillIn(String arg0, String arg1)
    {
        assertEquals(address.getStreet(), currentLocation.orElse(null).getAddress().getStreet());
        assertEquals(address.getCity(), currentLocation.orElse(null).getAddress().getCity());
    }

    @And("I see the error {string}")
    public void iSeeTheError(String arg0)
    {
        String message = "";

        try {
            currentLocation.orElse(null).setAddress(newAddress);
        } catch (Exception e) {
            message = e.getMessage();
        }

        assertEquals(message, arg0);
    }

    @Given("a location with ID {int} and price catalog with a KW price AC of {double} exists in the system")
    public void aLocationWithAPriceCatalogWithAKWPriceACOfExistsInTheSystem(int id, double price)
    {
        priceCat = new PriceCatalog(LocalDateTime.now(), price, 0.14, 0.1, 0.3);
        address = new Address("1200", "Hoechstaedtplatz", "Vienna", 6, "Austria");

        try {
            locationManager.addLocation(new Location(id, address, priceCat));
        } catch (Exception ignored)
        {}
    }

    @When("I update the price catalog of the locations with ID {int} to have a KW price AC of {double}")
    public void iUpdateTheLocationsPriceCatalogToHaveAKWPriceACOf(int id, double price)
    {
        newPriceCat = new PriceCatalog(LocalDateTime.now(), price, 0.15, 0.1, 0.3);
        currentLocation = locationManager.getLocationById(id);

        currentLocation.ifPresent(location -> location.updatePriceCatalog(newPriceCat));
    }

    @Then("the price catalog is updated")
    public void thePriceCatalogIsUpdated()
    {
        assertEquals(newPriceCat, currentLocation.orElse(null).getPriceCatalog());
    }

    @And("the locations KW price AC shows {double}")
    public void theLocationsKWPriceACShows(double price)
    {
        currentPriceCat = currentLocation.orElse(null).getPriceCatalog();

        assertEquals(price, currentPriceCat.getKWhPriceAC());
    }

    @Given("a location with ID {int} and a station with ID {int} exist in the system")
    public void aLocationAndAStationWithIDExistInTheSystem(int locationID, int stationID)
    {
        address = new Address("1200", "Hoechstaedtplatz", "Vienna", 6, "Austria");
        priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);

        location = new Location(locationID, address, priceCat);

        try {
            locationManager.addLocation(location);
        } catch (Exception ignored)
        {}

        station = new Station(stationID, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }

    @When("I add the station with ID {int} to the location with ID {int}")
    public void iAddTheStationToTheLocation(int stationID, int locationID)
    {
        currentLocation = locationManager.getLocationById(locationID);

        currentLocation.ifPresent(location ->
        {
            location.addStation(station);
        });

        currentStation = currentLocation.orElse(null).getStationById(stationID);
    }

    @Then("the station with ID {int} is saved to the location")
    public void theStationWithIDIsSaveToTheLocation(int stationID)
    {
        assertTrue(currentLocation.orElse(null).getStations().contains (currentStation.orElse (null)));
    }

    @And("the station with ID {int} is listed under the location's stations")
    public void theStationWithIDIsListedUnderTheLocationSStations(int stationID)
    {
        assertTrue(currentLocation.orElse(null).getStations().contains(currentStation.orElse(null)));
    }

    @And("the station with ID {int} is added to the location with ID {int}")
    public void theStationIsAddedToTheLocation(int stationID, int locationID)
    {
        currentLocation = locationManager.getLocationById(locationID);

        currentLocation.ifPresent(location ->
        {
            location.addStation(station);
        });

        currentStation = currentLocation.orElse(null).getStationById(stationID);
    }

    @When("I remove the station to the location")
    public void iRemoveTheStationToTheLocation()
    {
        currentLocation.orElse(null).rmStation(currentStation.orElse(null));
    }

    @Then("the station with ID {int} is no longer listed under the location's stations")
    public void theStationWithIDIsNoLongerListedUnderTheLocationSStations(int arg0)
    {
        assertFalse(currentLocation.orElse(null).getStations().contains(currentStation.orElse(null)));
    }
}
