package org.gruppeEins;

import io.cucumber.java.PendingException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;


public class StepDef_update_location
{
    @Given("a location with an address with street {string} and city {string} exists in the system")
    public void aLocationWithAnAddressWithStreetAndCityExistsInTheSystem(String arg0, String arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I update the locations address to a new address with street {string} in {string}")
    public void iUpdateTheLocationsAddressToANewAddressWithStreetIn(String arg0, String arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the new address is saved as the locations address")
    public void theNewAddressIsSavedAsTheLocationsAddress()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the locations address is {string} in {string}")
    public void theLocationsAddressIsIn(String arg0, String arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I try to change the address to a non existing one")
    public void iTryToChangeTheAddressToANonExistingOne()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the address is not changed")
    public void theAddressIsNotChanged()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("a location with a price catalog with a KW price AC of {double} exists in the system")
    public void aLocationWithAPriceCatalogWithAKWPriceACOfExistsInTheSystem(int arg0, int arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I update the locations price catalog to have a KW price AC of {double}")
    public void iUpdateTheLocationsPriceCatalogToHaveAKWPriceACOf(int arg0, int arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the price catalog is updated")
    public void thePriceCatalogIsUpdated()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the locations KW price AC shows {double}")
    public void theLocationsKWPriceACShows(int arg0, int arg1)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("a location and a station with ID {int} exist in the system")
    public void aLocationAndAStationWithIDExistInTheSystem(int arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I add the station to the location")
    public void iAddTheStationToTheLocation()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the station with ID {int} is save to the location")
    public void theStationWithIDIsSaveToTheLocation(int arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the station with ID {int} is listed under the location's stations")
    public void theStationWithIDIsListedUnderTheLocationSStations(int arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the station is added to the location")
    public void theStationIsAddedToTheLocation()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I remove the station to the location")
    public void iRemoveTheStationToTheLocation()
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("the station with ID {int} is removed from the location")
    public void theStationWithIDIsRemovedFromTheLocation(int arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("the station with ID {int} is no longer listed under the location's stations")
    public void theStationWithIDIsNoLongerListedUnderTheLocationSStations(int arg0)
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
