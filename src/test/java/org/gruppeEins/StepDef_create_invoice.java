package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class StepDef_create_invoice {

    private final Address address = new Address("1234", "Street", "City", 123, "Country");
    private final PriceCatalog priceCat = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
    private final Location location = new Location(address, priceCat);
    private final Station station = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
    private final Customer customer = new Customer("Ben", "ben@email.com", 0.00);
    private ChargingSession session;
    private Invoice invoice;
    private Customer newCustomer;
    private int sessionID;
    private int invoiceID;
    private double totalCost;
    private int customerID;


    @Given("a completed charging process with identifier {int} exists with a total cost of {double} EUR")
    public void aCompletedChargingProcessWithIdentifierExistsWithATotalCostOfEUR(int sessionID, double totalCost)
    {
        session = new ChargingSession(sessionID, station, customer);

        session.end(LocalDateTime.now(), 15.0);
        session.setTotalCost(totalCost);
    }

    @When("I generate an invoice for that charging session")
    public void iGenerateAnInvoiceForChargingSession()
    {
        invoice = new Invoice(session);
    }

    @Then("an invoice is created with an amount of {double} EUR")
    public void anInvoiceIsCreatedWithAnAmountOfEUR(double amount)
    {
        assertEquals(amount, invoice.getAmount());
    }

    @And("the invoice is linked to charging session with identifier {int}")
    public void theInvoiceIsLinkedToChargingProcess(int id)
    {
        assertEquals(id, invoice.getChargingSession().getId());
        assertEquals(session, invoice.getChargingSession());
    }

    @Given("a charging session with identifier {int} exists but is not completed")
    public void aChargingProcessExistsButIsNotCompleted(int sessionID)
    {
        session = new ChargingSession(sessionID, station, customer);

        assertNull(session.getEndTime());
    }

    @When("I try to generate an invoice for that charging session")
    public void iTryToGenerateAnInvoiceForThatChargingSession()
    {
        assertThrows(IllegalArgumentException.class, () -> {invoice = new Invoice(session);});
    }

    @Then("the invoice is not created")
    public void theInvoiceIsNotCreated()
    {
        assertNull(invoice);
    }

    @And("I see the error-message {string}")
    public void iSeeTheErrorMessage(String arg0)
    {
        String message = "";

        try
        {
            invoice = new Invoice(session);
        }
        catch (IllegalArgumentException e)
        {
            message = e.getMessage();
        }

        assertEquals(message, arg0);

    }

    @Given("an issued invoice with identifier {int} exists with customer with identifier {int}, amount {double} EUR")
    public void anIssuedInvoiceExistsWithCustomerAmountEURBillingPeriodAndChargingProcesses(int invoiceID, int customerID, double totalCost)
    {
        newCustomer = new Customer(customerID, "Max", "max@email.com");
        session = new ChargingSession(station, newCustomer);
        session.setTotalCost(totalCost);
        session.end(LocalDateTime.now(), 15.0);

        invoice = new Invoice(invoiceID, session);
    }

    @Then("a CSV file named {string} is created")
    public void aCSVFileNamedIsCreated(String fileName)
    {
        String fullPath = String.format("src/main/java/org/gruppeEins/resources/invoices/%s", fileName);

        assertTrue(new java.io.File(fullPath).exists());
    }

    @And("wasExported of the invoice is set to {string}")
    public void wasexportedOfTheInvoiceIsSetTo(String arg0)
    {
        Boolean wasExported = Boolean.parseBoolean(arg0);

        assertEquals(wasExported, invoice.getWasExported());
    }
}
