package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDef_view_charging_station_status
{

    LocationManager locationManager = new LocationManager("locManger");
    Address address = new Address("1234", "Street", "City", 123, "Country");
    PriceCatalog priceCat = new PriceCatalog(20.00);
    Location location = new Location(locationManager, address, priceCat);
    ChargingStatus givenStatus;
    ChargingStatus actualStatus;
    Station newStation;
    String message;

    @Given("a charging station {string} exists with status {string}")
    public void aChargingStationExistsWithStatus(String arg0, String arg1)
    {
        int id = Integer.parseInt(arg0);
        givenStatus = ChargingStatus.valueOf(arg1);

        newStation = new Station(id, ChargingType.DC, givenStatus, location);
    }

    @When("I view the status of charging stations")
    public void iViewTheStatusOfChargingStations()
    {
        actualStatus = newStation.getStatus();

        assertEquals(givenStatus, actualStatus);
    }

    @When("I change the status to {string}")
    public void iChangeTheStatusTo(String arg0)
    {
        givenStatus = ChargingStatus.valueOf(arg0);

        newStation.updateStatus(givenStatus);
    }

    @Then("I see the Message {string}")
    public void iSeeTheMessage(String arg0)
    {
        message = newStation.printStatus();

        assertEquals(message, arg0);
    }
}
