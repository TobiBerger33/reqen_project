package org.gruppeEins;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.datatable.DataTable;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StepDef_view_invoice {
    private InvoiceManager invoiceManager;
    private ChargingSession tempSession;
    private Invoice retrievedInvoice;

    private Station dummyStation;
    private Customer dummyCustomer;

    @Given("the {string} is initialized")
    public void initializeManager(String name) {
        if (name.equals("InvoiceManager")) {
            invoiceManager = new InvoiceManager();
        }
    }

    @Given("a charging session exists with:")
    public void createChargingSession(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);

        double durationMin = Double.parseDouble(row.get("Duration (min)"));
        double energy = Double.parseDouble(row.get("Energy (kWh)"));
        double cost = Double.parseDouble(row.get("Total Cost"));

        setupDependencies();

        tempSession = new ChargingSession(dummyStation, dummyCustomer);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minusMinutes((long) durationMin);

        tempSession.start(startTime);
        tempSession.end(now, energy);
        tempSession.setTotalCost(cost);
    }

    @Given("an invoice is generated for this session with ID {int}")
    public void createInvoice(int id) {
        Invoice invoice = new Invoice(id, tempSession);
        invoiceManager.addInvoice(invoice);
    }

    @When("I retrieve the invoice with ID {int}")
    public void retrieveInvoice(int id) {
        Optional<Invoice> result = invoiceManager.getInvoiceById(id);
        retrievedInvoice = result.orElse(null);
    }

    @When("I attempt to retrieve the invoice with ID {int}")
    public void attemptRetrieveInvalid(int id) {
        retrieveInvoice(id);
    }

    @Given("no invoice exists with ID {int}")
    public void ensureNoInvoice(int id) {
    }

    @Then("I should see the invoice details")
    public void verifyInvoicePresent() {
        assertNotNull(retrievedInvoice, "No invoice was retrieved but one was expected.");
    }

    @Then("the invoice amount should be {double}")
    public void verifyAmount(double expectedAmount) {
        assertEquals(expectedAmount, retrievedInvoice.getAmount(), 0.01);
    }

    @Then("the invoice should be linked to the charging session with {double} kWh")
    public void verifySessionLink(double expectedEnergy) {
        assertNotNull(retrievedInvoice.getChargingSession(), "Invoice is not linked to a session");
        assertEquals(expectedEnergy, retrievedInvoice.getChargingSession().getEnergy(), 0.01);
    }

    @Then("I should receive an error or empty result indicating {string}")
    public void verifyNotFound(String msg) {
        assertNull(retrievedInvoice, "Invoice was found but should not exist.");
    }

    private void setupDependencies() {
        Address dummyAddress = new Address("1010", "Hauptstraße", "Wien", 1, "Austria");

        PriceCatalog dummyCatalog = new PriceCatalog(
                LocalDateTime.now().minusDays(1), // Gültig seit gestern
                0.50, // kWh AC
                0.60, // kWh DC
                0.10, // Min AC
                0.20  // Min DC
        );

        Location dummyLocation = new Location(dummyAddress, dummyCatalog);
        dummyStation = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, dummyLocation);
        dummyCustomer = new Customer("Max Mustermann", "max@example.com", 1000.00);
    }
}
