package org.gruppeEins;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDef_add_charging_station
{
    private LocationManager locationManager;
    private Address address;
    private PriceCatalog priceCat;
    private Location cityCenter;
    private Station newStation;

    @Given("a location {string} exists in the system")
    public void aLocationExistsInTheSystem(String arg0)
    {
        locationManager = new LocationManager("locManger");
        address = new Address("1234", "Street", "City", 123, "Country");
        priceCat = new PriceCatalog(20.00);
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


    @Given("a location {string} exists with a charging point {string}")
    public void aLocationExistsWithAChargingPoint(String arg0, String arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I try to add another charging point with identifier {string} to {string}")
    public void iTryToAddAnotherChargingPointWithIdentifierTo(String arg0, String arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the system rejects the request")
    public void theSystemRejectsTheRequest()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("I see an error message {string}")
    public void iSeeAnErrorMessage(String arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }



    @When("I try to add a charging point with identifier {string} of type {string} to the location {string}")
    public void iTryToAddAChargingPointWithIdentifierOfTypeToTheLocation(String arg0, String arg1, String arg2)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the charging point is not created")
    public void theChargingPointIsNotCreated()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
