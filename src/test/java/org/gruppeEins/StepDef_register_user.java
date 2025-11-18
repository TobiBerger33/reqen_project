package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;


public class StepDef_register_user
{
    Customer customer;
    Customer newCustomer;
    String currentName;
    String currentEmail;
    String message;


    @Given("I am a new user without an existing account")
    public void iAmANewUserWithoutAnExistingAccount()
    {
        assertNull(customer);
    }

    @When("I provide my name {string}, email {string}, and a secure password")
    public void iProvideMyNameEmailAndASecurePassword(String arg0, String arg1)
    {
        customer = new Customer(arg0, arg1);
    }

    @Then("a new customer account is created")
    public void aNewCustomerAccountIsCreated()
    {
        assertNotNull(customer);
    }

    @And("I receive a customer ID")
    public void iReceiveACustomerID()
    {
        assertNotNull(customer.getId());
    }

    @And("my account balance is initialized to {string}")
    public void myAccountBalanceIsInitializedTo(String arg0)
    {
        double credit = Double.parseDouble(arg0);

        assertEquals(customer.getCredit(), credit);
    }

    @Given("a customer account already exists with the email {string}")
    public void aCustomerAccountAlreadyExistsWithTheEmail(String arg0)
    {
        customer = new Customer("Alex", arg0);
    }

    @When("I try to register with the email {string}")
    public void iTryToRegisterWithTheEmail(String arg0)
    {
        currentName = "Ben";
        currentEmail = arg0;
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String arg0)
    {
        try
        {
            newCustomer = new Customer(currentName, currentEmail );
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }

        assertEquals(message, arg0);
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
    }

    @And("no customer account is created")
    public void noCustomerAccountIsCreated()
    {
        assertNull(newCustomer);
    }
}
