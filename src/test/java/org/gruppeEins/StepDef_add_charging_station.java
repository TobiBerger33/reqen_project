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

    @Then("the charging point {int} is stored in the system")
    public void theChargingPointIsStoredInTheSystem(int currentID)
    {
        assertNotNull(location.getStationById(currentID));
    }

    @And("its initial state is {string}")
    public void itsInitialStateIs(String status)
    {
        ChargingStatus initStatus = ChargingStatus.valueOf(status);

        assertEquals(newStation.getStatus(), initStatus);
    }

    @And("it is visible under the location")
    public void itIsVisibleUnderTheLocation()
    {
        assertTrue(location.getStations().contains(newStation));
    }

    @When("I try to add another charging point with identifier {int} to the same location")
    public void iTryToAddAnotherChargingPointWithIdentifierToTheSameLocation(int id)
    {
        idStation = id;

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
    public void iSeeAnErrorMessage(String expectedMessage)
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

        assertEquals(message, expectedMessage);
    }

    @Then("I see the following error message {string}")
    public void iSeeTheErrorMessage(String expectedMessage)
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

        assertEquals(message, expectedMessage);
    }

    @And("the charging point is not created")
    public void theChargingPointIsNotCreated()
    {
        assertNull(newStation);
    }

    @Given("a location with identifier {int} exists in the system")
    public void aLocationWithIdentifierExistsInTheSystem(int idLocation)
    {
        location = new Location(idLocation, address, priceCat);
    }

    @Given("a location with identifier {int} exists with a station with identifier {int} in the system")
    public void aLocationWithIdentifierExistsWithAStationWithIdentifierInTheSystem(int idLocation, int idStation)
    {
        location = new Location(idLocation, address, priceCat);

        newStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }

    @When("I try to add a charging point with identifier {int} of type {string} to a location")
    public void iTryToAddAChargingPointWithIdentifierOfTypeToALocation(int idStation, String type)
    {
        ChargingType chargingType = ChargingType.valueOf(type);

        assertThrows(NullPointerException.class, () -> {
            newStation = new Station(idStation, chargingType, ChargingStatus.IN_OPERATION_FREE, location);});
    }

    @When("I add charging station with identifier {int} to that location")
    public void iAddChargingStationWithIdentifierToThatLocation(int idStation)
    {
        newStation = new Station(idStation, ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }
}
