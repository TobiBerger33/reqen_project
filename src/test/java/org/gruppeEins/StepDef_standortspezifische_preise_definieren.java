package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class StepDef_standortspezifische_preise_definieren {
    @Given("I create a location {string} with address {string} in {string}")
    public void iCreateALocationWithAddress(String locationName, String street, String city) {

    }

    @When("I assign a price catalog with {double} EUR per kWh AC and {double} EUR per kWh DC to location {string}")
    public void iAssignAPriceCatalogToLocation(double acPrice, double dcPrice, String locationName) {

    }

    @Then("location {string} should have {double} EUR per kWh AC")
    public void locationShouldHaveACPrice(String locationName, double expectedPrice) {
    }

    @And("location {string} should have {double} EUR per kWh DC")
    public void locationShouldHaveDCPrice(String locationName, double expectedPrice) {

    }

    //Given is already implemented (same as Scenario 1)

    @When("a station with ID {int} is added to location {string}")
    public void stationIsAddedToLocation(int stationId, String locationName) {

    }

    @And("I try to get the current prices from station {int}")
    public void iTryToGetTheCurrentPricesFromStation(int stationId) {

    }

    @Then("the station should return an error")
    public void theStationShouldReturnNoPriceCatalog() {

    }
}
