package org.gruppeEins;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StepDefs_view_location
{
    private final LocationManager locationManager = new LocationManager();
    private final StationManager stationManger = new StationManager();
    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private List<Location> allLocations;
    private List<Location> availableLocations = new ArrayList<>();
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;
    private Station station5;


    @Given("the system contains the locations {string}, {string}, and {string}")
    public void theSystemContainsTheLocationsAnd(String locName1, String locName2, String locName3)
    {
        try {
            locationManager.addLocation(new Location(address, locName1, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            locationManager.addLocation(new Location(address, locName2, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            locationManager.addLocation(new Location(address, locName3, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I request the overview of all locations")
    public void iRequestTheOverviewOfAllLocations()
    {
        try
        {
            allLocations = locationManager.getAllLocations();
        } catch (Exception ignored) {

        }
    }

    @Then("I see a list containing {string}, {string}, and {string}")
    public void iSeeAListContainingAnd(String locName1, String locName2, String locName3)
    {
        List<String> expectedNames = new ArrayList<>();

        expectedNames.add(locName1);
        expectedNames.add(locName2);
        expectedNames.add(locName3);

        for (Location loc : allLocations) {
            String currentName = loc.getName();

            assertTrue(expectedNames.contains(currentName), "Location " + currentName + " not found in list");
        }
    }

    @Given("the system contains location {string} with available charging points")
    public void theSystemContainsLocationWithAvailableChargingPoints(String locName)
    {
        try {
            locationManager.addLocation(new Location(address, locName, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<Location> currentLocation = locationManager.getLocationByName(locName);

        station1 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
        station2 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
        station3 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_OCCUPIED, currentLocation.orElse(null));
    }

    @And("the system contains location {string} with no available charging points")
    public void theSystemContainsLocationWithNoAvailableChargingPoints(String locName)
    {
        try {
            locationManager.addLocation(new Location(address, locName, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    }

    @And("the system contains another location {string} with available charging points")
    public void theSystemContainsAnotherLocationWithAvailableChargingPoints(String locName)
    {
        try {
            locationManager.addLocation(new Location(address, locName, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<Location> currentLocation = locationManager.getLocationByName(locName);

        station4 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
        station5 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
    }

    @When("I filter locations by availability")
    public void iFilterLocationsByAvailability()
    {
       allLocations = locationManager.getAllLocations();

       for (Location loc : allLocations) {
           if(!loc.getStations().isEmpty())
           {
               availableLocations.add(loc);
           }
       }
    }

    @Then("I see {string} and {string}")
    public void iSeeAnd(String locName1, String locName2)
    {
        assertTrue(availableLocations.contains(locationManager.getLocationByName(locName1).orElse(null)));
        assertTrue(availableLocations.contains(locationManager.getLocationByName(locName2).orElse(null)));
    }

    @And("I do not see {string}")
    public void iDoNotSee(String locName3)
    {
        assertFalse(availableLocations.contains(locationManager.getLocationByName(locName3).orElse(null)));
    }

    @Given("the system contains no locations")
    public void theSystemContainsNoLocations()
    {
        assertThrows(RuntimeException.class, locationManager::getAllLocations);
    }

    @Then("I see a message {string}")
    public void iSeeAMessage(String expectedMessage)
    {
        String message = "";
        
        try {
            locationManager.getAllLocations();
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }
        
        assertEquals(expectedMessage, message);
    }
}
