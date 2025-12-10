package org.gruppeEins;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class StepDef_view_charging_station_status {

    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private final Location location = new Location(address, priceCat);

    private Location newLocation;
    private ChargingStatus givenStatus;
    private ChargingStatus actualStatus;
    private Station newStation;
    private String errorMessage;

    @Given("a charging station {int} exists with status {string}")
    public void aChargingStationExistsWithStatus(int id, String Status)
    {
        givenStatus = ChargingStatus.valueOf(Status);

        newStation = new Station(id, ChargingType.DC, givenStatus, location);
    }

    @When("I try to view the status of charging stations")
    public void iViewTheStatusOfChargingStations()
    {
        actualStatus = newStation.getStatus();
    }

    @When("I change the status to {string}")
    public void iChangeTheStatusTo(String newStatus)
    {
        newStation.updateStatus(ChargingStatus.valueOf(newStatus));
    }

    @Then("I see the Message {string}")
    public void iSeeTheMessage(String expectedMessage)
    {
        assertEquals(newStation.printStatus(), expectedMessage);
    }

    @And("the station's status is {string}")
    public void theStationSStatusIs(String expectedStatus)
    {
        assertEquals(actualStatus, ChargingStatus.valueOf(expectedStatus));
    }

    @Given("no charging station exists in the system")
    public void noChargingStationExistsInTheSystem()
    {
        assertNull(newStation);
    }

    @When("I try to view the status of the new charging stations")
    public void iTryToViewTheStatusOfTheNewChargingStations()
    {
        try {
            actualStatus = newStation.getStatus();
        } catch (Exception e) {
            errorMessage = "Please select an existing station";
        }
    }

    @Then("I get shown the error message {string}")
    public void iGetShownTheErrorMessage(String expectedErrorMessage)
    {
        assertEquals(expectedErrorMessage, errorMessage);
    }
}
