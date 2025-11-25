package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class StepDef_view_charging_station_status {

    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private final Location location = new Location(address, priceCat);
    private ChargingStatus givenStatus;
    private ChargingStatus actualStatus;
    private Station newStation;
    private String message;

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
