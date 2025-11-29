package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

public class StepDef_delete_customer {
    private CustomerManager customerManager;
    private Customer currentCustomer;
    private Exception lastException;

    @Given("the {string} is initialized")
    public void initializeManager(String name) {
        if (name.equals("CustomerManager")) {
            customerManager = new CustomerManager();
        }
    }

    @Given("a customer {string} exists with email {string} and {double} credit")
    public void createCustomer(String name, String email, double credit) {
        currentCustomer = new Customer(name, email, credit);
        customerManager.addCustomer(currentCustomer);
    }

    @Given("no customer exists with ID {int}")
    public void ensureNoCustomer(int id) {
        if (customerManager != null) {
            Optional<Customer> c = customerManager.getCustomerById(id);
            assertFalse(c.isPresent());
        }
    }

    @When("I delete the customer {string}")
    public void deleteCustomerByName(String name) {
        try {
            customerManager.removeCustomer(currentCustomer.getId());
        } catch (Exception e) {
            lastException = e;
        }
    }

    @When("I attempt to delete the customer {string}")
    public void attemptDeleteCustomer(String name) {
        deleteCustomerByName(name);
    }

    @When("I attempt to delete the customer with ID {int}")
    public void attemptDeleteById(int id) {
        try {
            customerManager.removeCustomer(id);
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Then("the customer {string} should no longer exist in the system")
    public void verifyCustomerGone(String name) {
        Optional<Customer> result = customerManager.getCustomerById(currentCustomer.getId());
        assertFalse(result.isPresent(), "Customer " + name + " was found but should be deleted");
    }

    @Then("the customer {string} should still exist in the system")
    public void verifyCustomerExists(String name) {
        Optional<Customer> result = customerManager.getCustomerById(currentCustomer.getId());
        assertTrue(result.isPresent(), "Customer " + name + " was deleted but should remain");
    }

    @Then("I should receive a deletion error message {string}")
    public void verifyErrorMessage(String expectedMessage) {
        assertNotNull(lastException, "Expected an exception but none was thrown");
        assertEquals(expectedMessage, lastException.getMessage());
    }

    @Then("the operation should complete without error")
    public void verifyNoError() {
        assertNull(lastException, "An error occurred but explicitly none was expected: " +
                (lastException != null ? lastException.getMessage() : ""));
    }

    @Then("the total number of customers should be {int}")
    @Then("the total number of customers should remain {int}")
    public void verifyCount(int expectedCount) {
        assertEquals(expectedCount, customerManager.getAllCustomers().size());
    }
}
