package org.gruppeEins;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDef_add_charging_station
{
    private final LocationManager locationManager = new LocationManager("locManger");
    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(20.00);
    private Location cityCenter;
    private Location airport;
    private Location unknownPlaza;
    private Station newStation;
    private Station newnewStation;

    @Given("a location {string} exists in the system")
    public void aLocationExistsInTheSystem(String arg0)
    {
        cityCenter = new Location(locationManager, arg0, address, priceCat);
    }

    @When("I add a charging point with identifier {string} of type {string} to {string}")
    public void iAddAChargingPointWithIdentifierOfTypeTo(String arg0, String arg1, String arg2)
    {
        int id = Integer.parseInt(arg0);
        ChargingType type = ChargingType.valueOf(arg1);
        Location currentLocation = locationManager.getLocations(arg2);

        newStation = new Station(id, type, ChargingStatus.IN_OPERATION_FREE, currentLocation);
    }

    @Then("the charging point {string} is stored in the system")
    public void theChargingPointIsStoredInTheSystem(String arg0)
    {
        int id = Integer.parseInt(arg0);

        assertTrue(cityCenter.getStations().stream().anyMatch(station -> station.getId() == id));
    }

    @And("its initial state is {string}")
    public void itsInitialStateIs(String arg0)
    {
        ChargingStatus initStatus = ChargingStatus.valueOf(arg0);

        assertEquals(newStation.getStatus(), initStatus);
    }

    @And("it is visible under the location")
    public void itIsVisibleUnderTheLocation()
    {
        assertTrue(cityCenter.getStations().contains(newStation));
    }


    @Given("a location {string} exists with a charging point with identifier {string}")
    public void aLocationExistsWithAChargingPoint(String arg0, String arg1)
    {
        airport = new Location(locationManager, arg0, address, priceCat);

        int id = Integer.parseInt(arg1);
        newStation = new Station(id, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, airport);
    }

    @When("I try to add another charging point with identifier {string} to the same location")
    public void iTryToAddAnotherChargingPointWithIdentifierToTheSameLocation(String arg0)
    {
        int id = Integer.parseInt(arg0);
        assertThrows(IllegalArgumentException.class, () -> {newnewStation = new Station(id, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, airport);});

    }

    @Then("the system rejects the request")
    public void theSystemRejectsTheRequest()
    {
        int id = 200;
        assertThrows(IllegalArgumentException.class, () -> {newnewStation = new Station(id, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, airport);});
    }

    @And("I see an error message {string}")
    public void iSeeAnErrorMessage(String arg0)
    {
        int id = 200;
        String message = "";

        try
        {
            newnewStation = new Station(id, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, airport);
        }
        catch (IllegalArgumentException e)
        {
            message = e.getMessage();
        }

        assertEquals(message, arg0);
    }



    @When("I try to add a charging point with identifier {string} of type {string} to the location {string}")
    public void iTryToAddAChargingPointWithIdentifierOfTypeToTheLocation(String arg0, String arg1, String arg2)
    {
        int id = Integer.parseInt(arg0);
        ChargingType type = ChargingType.valueOf(arg1);

        assertThrows(NullPointerException.class, () -> {newStation = new Station(id, type, ChargingStatus.IN_OPERATION_FREE, unknownPlaza);});
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String arg0)
    {
        String message = "";

        try
        {
            newStation = new Station(300, ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, unknownPlaza);
        }
        catch (NullPointerException e)
        {
            message = e.getMessage();
        }

        assertEquals(message, arg0);
    }

    @And("the charging point is not created")
    public void theChargingPointIsNotCreated()
    {
        assertNull(newStation);
    }
}
