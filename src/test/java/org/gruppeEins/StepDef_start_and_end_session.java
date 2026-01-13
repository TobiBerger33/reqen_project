package org.gruppeEins;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;

import io.cucumber.java.en.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class StepDef_start_and_end_session
{
    private final CustomerManager customerManager = new CustomerManager();
    private final ChargingSessionManager chargingSessionManager = new ChargingSessionManager();
    private final LocationManager locationManager = new LocationManager();

    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private final Location location = new Location(address, priceCat);

    private Customer customer;
    private Station station;
    private ChargingSession chargingSession;
    private ChargingSession session;
    private String errorMessage = "";


    @Given("A user with name {string} and email {string} exists in the system")
    public void aUserWithNameAndEmailExistsInTheSystem(String name, String email)
    {
        customer = new Customer(name, email);
        customerManager.addCustomer(customer);
    }

    @And("a station exists in the system")
    public void aStationExistsInTheSystem()
    {
        station = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    }

    @When("the user starts a charging session with start time {string}")
    public void theUserStartsAChargingSessionWithStartTime(String startTime)
    {
        session = customer.startSession(station, LocalDateTime.parse(startTime));
        chargingSessionManager.addSession(session);
    }

    @Then("a charging session gets created with the station and start time {string}")
    public void aCharginSessionGetsCreatedWithTheStationAndStartTime(String expectedStartTime)
    {
        assertNotNull(session);
        assertEquals(LocalDateTime.parse(expectedStartTime), session.getStartTime());
    }

    @And("the user has an active charging session with start time {string}")
    public void theUserHasAnActiveChargingSession(String startTime)
    {
        session = new ChargingSession(station, customer);
        chargingSessionManager.addSession(session);

        session.start(LocalDateTime.parse(startTime));

        assertNotNull(chargingSessionManager.getSessionByCustomerId(customer.getId()));
    }

    @When("the user ends the session with his ID with end time {string} and total energy consumed {double}")
    public void theUsersEndsTheSessionWithHisIDUndTheStationsID(String endTime, double energyConsumed)
    {
        try {
            customer.endSession(LocalDateTime.parse(endTime), energyConsumed, chargingSessionManager);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the session is ended with end time {string}")
    public void theSessionIsEndedWithCurrentTime(String expectedEndTime)
    {
        assertEquals(LocalDateTime.parse(expectedEndTime), session.getEndTime());
    }

    @And("the sessions total price is calculated")
    public void theSessionsTotalPriceIsCalculated()
    {
        assertNotNull(session.getTotalCost());
        assertNotEquals(0, session.getTotalCost());
    }

    @But("no charging session exists for the user")
    public void noChargingSessionExistsForTheUser()
    {
        assertThrows(IllegalStateException.class, () -> chargingSessionManager.getSessionByCustomerId(customer.getId()));
    }

    @When("the user trys to end the session with his ID with end time {string} and total energy consumed {double}")
    public void theUserTrysToEndTheSessionWithHisIDWithEndTimeAndTotalEnergyConsumed(String endTime, double energyConsumed)
    {
        try {
            customer.endSession(LocalDateTime.parse(endTime), energyConsumed, chargingSessionManager);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @And("the user has started a charging session with start time {string}")
    public void theUserHasStartedAChargingSessionWithStartTime(String startTime)
    {
        session = customer.startSession(station, LocalDateTime.parse(startTime));
        chargingSessionManager.addSession(session);
    }

    @Then("the user receives the notification {string}")
    public void theUserReceivesTheNotification(String expectedNotification)
    {
        assertEquals(expectedNotification, errorMessage);
    }
}
