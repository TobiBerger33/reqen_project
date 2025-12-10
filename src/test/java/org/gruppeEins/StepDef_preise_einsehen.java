package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class StepDef_preise_einsehen {
    @Given("a location {string} exists with a price catalog")
    public void aLocationExistsWithAPriceCatalog(String locationName) {

    }

    @When("I request the price information for location {string}")
    public void iRequestPriceInformationForLocation(String locationName) {

    }

    @Then("I should see the kWh price AC is {double} EUR and the kWh price DC is {double} EUR")
    public void iShouldSeeTheKWhPriceACIsAndTheKWhPriceDCIs(double expectedPriceAC, double expectedPriceDC) {

    }

    @And("I should see the minute price AC is {double} EUR and the minute price DC is {double} EUR")
    public void iShouldSeeTheMinutePriceACIsAndTheMinutePriceDCIs(double expectedPriceAC, double expectedPriceDC) {

    }

    @Given("a location {string} exists without a price catalog")
    public void aLocationExistsWithoutAPriceCatalog(String locationName) {

    }

    @When("I try to request the price information for location {string}")
    public void iTryToRequestPriceInformationForLocation(String locationName) {

    }

    @Then("I should see an error that no price catalog is available")
    public void iShouldSeeAnErrorThatNoPriceCatalogIsAvailable() {

    }
}