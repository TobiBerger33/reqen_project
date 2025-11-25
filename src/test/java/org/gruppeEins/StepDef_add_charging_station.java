package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class StepDef_add_charging_station
{
    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private Location location;
    private Station newStation;
    private Station newnewStation;
    private int idLocation;
    private int idStation;

    @Then("the charging point {string} is stored in the system")
    public void theChargingPointIsStoredInTheSystem(String arg0)
    {
        int currentID = Integer.parseInt(arg0);

        assertNotNull(location.getStationById(currentID));
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
        assertTrue(location.getStations().contains(newStation));
    }

    @When("I try to add another charging point with identifier {string} to the same location")
    public void iTryToAddAnotherChargingPointWithIdentifierToTheSameLocation(String arg0)
    {
        idStation = Integer.parseInt(arg0);
        assertThrows(IllegalArgumentException.class, () -> {
            newnewStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);});
    }

    @Then("the system rejects the request")
    public void theSystemRejectsTheRequest()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            newnewStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);});
    }

    @And("I see an error message {string}")
    public void iSeeAnErrorMessage(String arg0)
    {
        String message = "";

        try
        {
            newnewStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
        }
        catch (IllegalArgumentException e)
        {
            message = e.getMessage();
        }

        assertEquals(message, arg0);
    }

    @Then("I see the following error message {string}")
    public void iSeeTheErrorMessage(String arg0)
    {
        String message = "";

        try
        {
            newStation = new Station(300, ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, location);
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

    @Given("a location with identifier {string} exists in the system")
    public void aLocationWithIdentifierExistsInTheSystem(String arg0)
    {
        idLocation = Integer.parseInt(arg0);
        location = new Location(idLocation, address, priceCat);
    }

    @Given("a location with identifier {string} exists with a station with identifier {string} in the system")
    public void aLocationWithIdentifierExistsWithAStationWithIdentifierInTheSystem(String arg0, String arg1)
    {
        idLocation = Integer.parseInt(arg0);
        location = new Location(idLocation, address, priceCat);

        idStation = Integer.parseInt(arg1);
        newStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }

    @When("I try to add a charging point with identifier {string} of type {string} to a location")
    public void iTryToAddAChargingPointWithIdentifierOfTypeToALocation(String arg0, String arg1)
    {
        idStation = Integer.parseInt(arg0);
        ChargingType type = ChargingType.valueOf(arg1);

        assertThrows(NullPointerException.class, () -> {
            newStation = new Station(idStation, type, ChargingStatus.IN_OPERATION_FREE, location);});
    }

    @When("I add charging station with identifier {string} to that location")
    public void iAddChargingStationWithIdentifierToThatLocation(String arg0)
    {
        idStation = Integer.parseInt(arg0);
        newStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }
}
