package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class StepDefs_create_invoice
{
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


    @Given("a completed charging process with identifier {string} exists with a total cost of {string} EUR")
    public void aCompletedChargingProcessWithIdentifierExistsWithATotalCostOfEUR(String arg0, String arg1)
    {
        sessionID = Integer.parseInt(arg0);
        totalCost = Double.parseDouble(arg1);

        session = new ChargingSession(sessionID, station, customer);

        session.end(LocalDateTime.now(), 15.0);
        session.setTotalCost(totalCost);
    }

    @When("I generate an invoice for that charging session")
    public void iGenerateAnInvoiceForChargingSession()
    {
        invoice = new Invoice(session);
    }

    @Then("an invoice is created with an amount of {string} EUR")
    public void anInvoiceIsCreatedWithAnAmountOfEUR(String arg0)
    {
        double amount = Double.parseDouble(arg0);

        assertEquals(amount, invoice.getAmount());
    }

    @And("the invoice is linked to charging session with identifier {string}")
    public void theInvoiceIsLinkedToChargingProcess(String arg0)
    {
        int id = Integer.parseInt(arg0);

        assertEquals(id, invoice.getChargingSession().getId());
        assertEquals(session, invoice.getChargingSession());
    }

    @Given("a charging session with identifier {string} exists but is not completed")
    public void aChargingProcessExistsButIsNotCompleted(String arg0)
    {
        sessionID = Integer.parseInt(arg0);

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

    @Given("an issued invoice with identifier {string} exists with customer with identifier {string}, amount {string} EUR")
    public void anIssuedInvoiceExistsWithCustomerAmountEURBillingPeriodAndChargingProcesses(String arg0, String arg1, String arg2)
    {
        invoiceID = Integer.parseInt(arg0);
        customerID = Integer.parseInt(arg1);
        totalCost = Double.parseDouble(arg2);

        newCustomer = new Customer(customerID, "Max", "max@email.com");
        session = new ChargingSession(station, newCustomer);
        session.setTotalCost(totalCost);
        session.end(LocalDateTime.now(), 15.0);

        invoice = new Invoice(invoiceID, session);
    }

    @Then("a CSV file named {string} is created")
    public void aCSVFileNamedIsCreated(String arg0)
    {
        String fullPath = String.format("src/main/java/org/gruppeEins/resources/invoices/%s", arg0);

        assertTrue(new java.io.File(fullPath).exists());
    }

    @And("wasExported of the invoice is set to {string}")
    public void wasexportedOfTheInvoiceIsSetTo(String arg0)
    {
        Boolean wasExported = Boolean.parseBoolean(arg0);

        assertEquals(wasExported, invoice.getWasExported());
    }
}
