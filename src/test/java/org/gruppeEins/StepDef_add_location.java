package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;


public class StepDef_add_location {
    private LocationManager locationManager;
    private Location tempLocation;
    private Exception lastException;

    @Given("the {string} is initialized, contains no locations and therefore throws an error when retrieving all locations")
    public void initializeManager(String managerName /*, int initialCount*/) {
        if (managerName.equals("LocationManager")) {
            locationManager = new LocationManager();
            assertThrows(RuntimeException.class, locationManager::getAllLocations);
        }
    }

    @Given("I have a new location details with Street {string}, Number {int}, Zip {string}, City {string}, and Country {string}")
    public void prepareLocationDetails(String street, int number, String zip, String city, String country) {
        Address address = new Address(zip, street, city, number, country);

        tempLocation = new Location(address, null);
    }

    @When("I add this new location to the network")
    public void addLocationToNetwork() {
        try {
            locationManager.addLocation(tempLocation);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Given("the following locations exist to be added:")
    public void prepareMultipleLocations(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            String street = columns.get("Street");
            int number = Integer.parseInt(columns.get("Number"));
            String zip = columns.get("Zip");
            String city = columns.get("City");
            String country = columns.get("Country");

            Address addr = new Address(zip, street, city, number, country);
            Location loc = new Location(addr, null);

            try {
                locationManager.addLocation(loc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @When("I add these locations to the network")
    public void addMultipleLocations() {

    }

    @Then("the total number of locations should be {int}")
    public void checkTotalLocations(int expectedCount) {
        assertEquals(expectedCount, locationManager.getAllLocations().size());
    }

    @Then("I should see the location {string} in the location list")
    public void checkLocationExists(String city) {
        boolean found = false;
        for (Location loc : locationManager.getAllLocations()) {
            if (loc.getAddress() != null && loc.getAddress().getCity().equals(city)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Location with city " + city + " not found");
    }

    @Given("I attempt to add a location without an address")
    public void prepareInvalidLocation() {
        tempLocation = new Location(null, null);
    }

    @When("I execute the add command")
    public void executeAddCommand() {
        try {
            locationManager.addLocation(tempLocation);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("I should receive an error message {string}")
    public void verifyErrorMessage(String expectedMessage) {
        assertNotNull(lastException, "Expected an exception but none was thrown");
        assertEquals(expectedMessage, lastException.getMessage());
    }

    @Then("the operation should be successful")
    public void checkOperationSuccess() {
        assertNull(lastException, "Operation failed with exception: " +
                (lastException != null ? lastException.getMessage() : ""));
    }

    @Then("I should be able to retrieve the location with City {string} via its ID")
    public void checkRetrievalById(String city) {
        int idToTest = -1;
        for (Location loc : locationManager.getAllLocations()) {
            if (loc.getAddress() != null && loc.getAddress().getCity().equals(city)) {
                idToTest = loc.getId();
                break;
            }
        }

        assertNotEquals(-1, idToTest, "City " + city + " not found in system, cannot test ID retrieval");

        java.util.Optional<Location> result = locationManager.getLocationById(idToTest);

        assertTrue(result.isPresent(), "Location lookup by ID failed for city: " + city);
        assertEquals(city, result.get().getAddress().getCity(), "Retrieved location does not match expected city");
    }

    @And("there are no locations")
    public void thereAreNoLocations()
    {
        assertThrows(RuntimeException.class, locationManager::getAllLocations);
    }
}
