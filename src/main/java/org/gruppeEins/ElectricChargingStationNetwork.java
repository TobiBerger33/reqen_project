package org.gruppeEins;

import java.time.LocalDateTime;

public class ElectricChargingStationNetwork {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Setting up Electric Charging Station Network ---");

        // 1. Initialize all managers
        CustomerManager customerManager = new CustomerManager();
        PriceCatalogManager priceCatalogManager = new PriceCatalogManager();
        LocationManager locationManager = new LocationManager();
        StationManager stationManager = new StationManager();
        ChargingSessionManager sessionManager = new ChargingSessionManager();
        InvoiceManager invoiceManager = new InvoiceManager();

        // 2. Create a price catalog
        PriceCatalog prices = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.10);
        priceCatalogManager.addPriceCatalog(prices);
        System.out.println("Created Price Catalog: " + prices.getId());

        // 3. Create a location with an address
        Address address = new Address("12345", "Main St", "Anytown", 1, "Germany");
        Location location = new Location(address, prices);
        locationManager.addLocation(location);
        System.out.println("Created Location: " + location.getId() + " at " + address.getStreet());

        // 4. Create a station
        Station station = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, location);
        stationManager.addStation(station);
        System.out.println("Created Station: " + station.getId() + " of type " + station.getType());

        // 5. Create a customer
        Customer customer = new Customer("John Doe", "john.doe@example.com", 50.0);
        customerManager.addCustomer(customer);
        System.out.println("Created Customer: " + customer.getName() + " with credit " + customer.getCredit());
        
        System.out.println("\n--- Simulating a Charging Session ---");

        // 6. Simulate a charging session
        ChargingSession session = new ChargingSession(station, customer);
        sessionManager.addSession(session);

        // Start the session
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(30);
        session.start(startTime);
        System.out.println("Session " + session.getId() + " started at " + startTime);
        System.out.println("Station status: " + station.getStatus());


        // End the session after 30 minutes, consuming 15 kWh
        LocalDateTime endTime = LocalDateTime.now();
        double energyConsumed = 15.0; // kWh
        session.end(endTime, energyConsumed);
        System.out.println("Session " + session.getId() + " ended at " + endTime);
        System.out.println("Energy consumed: " + energyConsumed + " kWh");
        System.out.println("Session cost: " + session.getTotalCost());
        System.out.println("Station status: " + station.getStatus());


        // 7. Create an invoice
        Invoice invoice = new Invoice(session);
        invoiceManager.addInvoice(invoice);
        System.out.println("\n--- Finalizing Invoice ---");
        System.out.println("Created Invoice: " + invoice.getId() + " for amount " + invoice.getAmount());
        System.out.println("Customer " + customer.getName() + " credit before invoice: 50.0" );
        System.out.println("Customer " + customer.getName() + " new credit: " + customer.getCredit());
        
        System.out.println("\n--- System State ---");
        System.out.println(customerManager);
        System.out.println(invoiceManager);

    }
}