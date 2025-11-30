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
    public void theSystemContainsTheLocationsAnd(String arg0, String arg1, String arg2)
    {
        try {
            locationManager.addLocation(new Location(address, arg0, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            locationManager.addLocation(new Location(address, arg1, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            locationManager.addLocation(new Location(address, arg2, priceCat));
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
    public void iSeeAListContainingAnd(String arg0, String arg1, String arg2)
    {
        List<String> expectedNames = new ArrayList<>();

        expectedNames.add(arg0);
        expectedNames.add(arg1);
        expectedNames.add(arg2);

        for (Location loc : allLocations) {
            String currentName = loc.getName();

            assertTrue(expectedNames.contains(currentName), "Location " + currentName + " not found in list");
        }
    }

    @Given("the system contains location {string} with available charging points")
    public void theSystemContainsLocationWithAvailableChargingPoints(String arg0)
    {
        try {
            locationManager.addLocation(new Location(address, arg0, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<Location> currentLocation = locationManager.getLocationByName(arg0);

        station1 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
        station2 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, currentLocation.orElse(null));
        station3 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_OCCUPIED, currentLocation.orElse(null));
    }

    @And("the system contains location {string} with no available charging points")
    public void theSystemContainsLocationWithNoAvailableChargingPoints(String arg0)
    {
        try {
            locationManager.addLocation(new Location(address, arg0, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    }

    @And("the system contains another location {string} with available charging points")
    public void theSystemContainsAnotherLocationWithAvailableChargingPoints(String arg0)
    {
        try {
            locationManager.addLocation(new Location(address, arg0, priceCat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<Location> currentLocation = locationManager.getLocationByName(arg0);

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
    public void iSeeAnd(String arg0, String arg1)
    {
        assertTrue(availableLocations.contains(locationManager.getLocationByName(arg0).orElse(null)));
        assertTrue(availableLocations.contains(locationManager.getLocationByName(arg1).orElse(null)));
    }

    @And("I do not see {string}")
    public void iDoNotSee(String arg0)
    {
        assertFalse(availableLocations.contains(locationManager.getLocationByName(arg0).orElse(null)));
    }

    @Given("the system contains no locations")
    public void theSystemContainsNoLocations()
    {
        assertThrows(RuntimeException.class, locationManager::getAllLocations);
    }

    @Then("I see a message {string}")
    public void iSeeAMessage(String arg0)
    {
        String message = "";
        
        try {
            locationManager.getAllLocations();
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }
        
        assertEquals(arg0, message);
    }
}
