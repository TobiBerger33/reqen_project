package org.gruppeEins;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDef_edit_customer {

    private CustomerManager customerManager;
    private Exception exception;

    @Before
    public void setup() {
        customerManager = new CustomerManager();
        exception = null;
    }

    @Given("a customer with id {int}, name {string}, and email {string}")
    public void a_customer_with_id_name_and_email(Integer id, String name, String email) {
        Customer customer = new Customer(id, name, email);
        customerManager.addCustomer(customer);
    }

    @When("the customer with id {int} updates their name to {string} and email to {string}")
    public void the_customer_with_id_updates_their_name_to_and_email_to(Integer id, String newName, String newEmail) {
        try {
            customerManager.updateCustomer(id, newName, newEmail);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("the customer with id {int} should have the name {string} and the email {string}")
    public void the_customer_with_id_should_have_the_name_and_the_email(Integer id, String expectedName, String expectedEmail) {
        Customer updatedCustomer = customerManager.getCustomerById(id).orElse(null);
        assertNotNull(updatedCustomer);
        assertEquals(expectedName, updatedCustomer.getName());
        assertEquals(expectedEmail, updatedCustomer.getEmail());
    }

    @Given("no customer with id {int} exists")
    public void no_customer_with_id_exists(Integer id) {
        assertTrue(customerManager.getCustomerById(id).isEmpty());
    }

    @When("an attempt is made to update the name of customer with id {int} to {string}")
    public void an_attempt_is_made_to_update_the_name_of_customer_with_id_to(Integer id, String newName) {
        customerManager.updateCustomer(id, newName, null);
    }

    @Then("the system should not find a customer with id {int} to update")
    public void the_system_should_not_find_a_customer_with_id_to_update(Integer id) {
        assertTrue(customerManager.getCustomerById(id).isEmpty());
    }

    @When("the customer with id {int} attempts to update their email to {string}")
    public void the_customer_with_id_attempts_to_update_their_email_to(Integer id, String newEmail) {
        try {
            customerManager.updateCustomer(id, null, newEmail);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("the update should fail with an {string} error")
    public void the_update_should_fail_with_an_error(String errorMessage) {
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @And("another customer with id {int}, name {string}, and email {string}")
    public void another_customer_with_id_name_and_email(Integer id, String name, String email) {
        Customer anotherCustomer = new Customer(id, name, email);
        customerManager.addCustomer(anotherCustomer);
    }
    
    @When("the customer with id {int} attempts to update their name to {string}")
    public void the_customer_with_id_attempts_to_update_their_name_to(Integer id, String newName) {
        try {
            customerManager.updateCustomer(id, newName, null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }
}
