package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;


public class StepDef_view_and_top_up_balance {

    private Customer customer;
    private double retrievedBalance;

    @Given("a customer with an initial balance of {double}")
    public void aCustomerWithAnInitialBalanceOf(double initialBalance) {
        this.customer = new Customer("Test Customer", "test@example.com", initialBalance);
    }

    @When("the customer checks their balance")
    public void theCustomerChecksTheirBalance() {
        this.retrievedBalance = this.customer.getCredit();
    }

    @Then("the balance should be {double}")
    public void theBalanceShouldBe(double expectedBalance) {
        assertEquals(expectedBalance, this.retrievedBalance);
    }

    @When("the customer tops up their balance with {double}")
    public void theCustomerTopsUpTheirBalanceWith(double amount) {
        this.customer.increaseCredit(amount);
    }

    @Then("the new balance should be {double}")
    public void theNewBalanceShouldBe(double newBalance) {
        assertEquals(newBalance, this.customer.getCredit());
    }
}
