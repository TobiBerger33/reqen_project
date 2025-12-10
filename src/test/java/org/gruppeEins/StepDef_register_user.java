package org.gruppeEins;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;


public class StepDef_register_user {

    private Customer customer;
    private Customer newCustomer;
    private String currentName;
    private String currentEmail;
    private Exception exception; // Added for capturing exceptions
    private CustomerManager customerManager; // Added CustomerManager

    @Before
    public void setup() {
        customerManager = new CustomerManager();
        exception = null;
        customer = null;
        newCustomer = null;
    }

    @Given("I am a new user without an existing account")
    public void iAmANewUserWithoutAnExistingAccount()
    {
        assertNull(customer);
    }

    @When("I provide my name {string}, email {string}, and a secure password")
    public void iProvideMyNameEmailAndASecurePassword(String name, String email)
    {
        currentName = name;
        currentEmail = email;
    }

    @Then("a new customer account is created")
    public void aNewCustomerAccountIsCreated()
    {
        customer = new Customer(currentName, currentEmail);
        assertNotNull(customer);
    }

    @And("I receive a customer ID")
    public void iReceiveACustomerID()
    {
        assertNotNull(customer.getId());
    }

    @And("my account balance is initialized to {double}")
    public void myAccountBalanceIsInitializedTo(double credit)
    {
        assertEquals(customer.getCredit(), credit);
    }

    @Given("a customer account already exists with the email {string}")
    public void aCustomerAccountAlreadyExistsWithTheEmail(String email)
    {
        customer = new Customer("Alex", email);
        customerManager.addCustomer(customer);
    }

    @When("I try to register with the email {string}")
    public void iTryToRegisterWithTheEmail(String email)
    {
        currentName = "Ben";
        currentEmail = email;
        try {
            Customer tempCustomer = new Customer(currentName, currentEmail);
            customerManager.addCustomer(tempCustomer);
            newCustomer = tempCustomer; // Only assign if successful
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String expectedMessage)
    {

        String errorMessage = exception.getMessage();

        assertEquals(expectedMessage, errorMessage);
    }

    @And("no new account is created")
    public void noNewAccountIsCreated()
    {
        assertNull(newCustomer);
    }

    @Given("I am a new user")
    public void iAmANewUser()
    {
        assertNull(newCustomer);
    }

    @When("I provide only an email {string} and leave other required fields empty")
    public void iProvideOnlyAnEmailAndLeaveOtherRequiredFieldsEmpty(String arg0)
    {
        currentName = "";
        currentEmail = arg0;
        try {
            Customer tempCustomer = new Customer(currentName, currentEmail);
            customerManager.addCustomer(tempCustomer);
            newCustomer = tempCustomer; // Only assign if successful
        } catch (Exception e) {
            exception = e;
        }
    }

    @And("no customer account is created")
    public void noCustomerAccountIsCreated()
    {
        assertNull(newCustomer);
    }
}
