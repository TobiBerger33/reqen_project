package org.gruppeEins;

import java.time.LocalDateTime;
import java.util.Optional;

public class ElectricChargingStationNetwork {
    public static void main(String[] args) throws Exception {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     ELECTRIC CHARGING STATION NETWORK - FULL DEMO           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // Initialize all managers
        CustomerManager customerManager = new CustomerManager();
        PriceCatalogManager priceCatalogManager = new PriceCatalogManager();
        LocationManager locationManager = new LocationManager();
        StationManager stationManager = new StationManager();
        ChargingSessionManager sessionManager = new ChargingSessionManager();
        InvoiceManager invoiceManager = new InvoiceManager();

        // ═══════════════════════════════════════════════════════════════
        // SECTION 1: PRICE CATALOG MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 1: PRICE CATALOG MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 1.1 Add Price Catalogs
        System.out.println(">>> Adding Price Catalogs...");
        PriceCatalog standardPrices = new PriceCatalog(LocalDateTime.now(), 0.40, 0.60, 0.10, 0.15);
        PriceCatalog premiumPrices = new PriceCatalog(LocalDateTime.now(), 0.35, 0.50, 0.08, 0.12);
        priceCatalogManager.addPriceCatalog(standardPrices);
        priceCatalogManager.addPriceCatalog(premiumPrices);
        System.out.println("Created Standard Price Catalog (ID: " + standardPrices.getId() + "):");
        System.out.println("  - AC: " + standardPrices.getKWhPriceAC() + " EUR/kWh, " + standardPrices.getMinutePriceAC() + " EUR/min");
        System.out.println("  - DC: " + standardPrices.getKWhPriceDC() + " EUR/kWh, " + standardPrices.getMinutePriceDC() + " EUR/min");
        System.out.println("Created Premium Price Catalog (ID: " + premiumPrices.getId() + ")");

        // 1.2 Update Price Catalog
        System.out.println("\n>>> Updating Premium Price Catalog...");
        premiumPrices.setKWhPriceAC(0.32);
        premiumPrices.setMinutePriceAC(0.07);
        priceCatalogManager.updatePriceCatalog(premiumPrices);
        System.out.println("Updated Premium prices - New AC rate: " + premiumPrices.getKWhPriceAC() + " EUR/kWh");

        // 1.3 Get Price Catalog by ID
        System.out.println("\n>>> Retrieving Price Catalog by ID...");
        Optional<PriceCatalog> foundCatalog = priceCatalogManager.getPriceCatalogById(standardPrices.getId());
        foundCatalog.ifPresent(pc -> System.out.println("Found Price Catalog ID " + pc.getId() + " - Valid from: " + pc.getValidFrom()));

        // 1.4 Get All Price Catalogs
        System.out.println("\n>>> All Price Catalogs:");
        System.out.println(priceCatalogManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 2: LOCATION MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 2: LOCATION MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 2.1 Add Locations
        System.out.println(">>> Adding Locations...");
        Address address1 = new Address("10115", "Alexanderplatz", "Berlin", 1, "Germany");
        Address address2 = new Address("80331", "Marienplatz", "Munich", 5, "Germany");
        Address address3 = new Address("20095", "Jungfernstieg", "Hamburg", 10, "Germany");

        Location berlinLocation = new Location(address1, "Berlin Central", standardPrices);
        Location munichLocation = new Location(address2, "Munich Downtown", premiumPrices);
        Location hamburgLocation = new Location(address3, "Hamburg Port", standardPrices);

        locationManager.addLocation(berlinLocation);
        locationManager.addLocation(munichLocation);
        locationManager.addLocation(hamburgLocation);

        System.out.println("Created Location: " + berlinLocation.getName() + " (ID: " + berlinLocation.getId() + ")");
        System.out.println("  Address: " + address1.getStreet() + " " + address1.getHouseNumber() + ", " + address1.getCity());
        System.out.println("Created Location: " + munichLocation.getName() + " (ID: " + munichLocation.getId() + ")");
        System.out.println("Created Location: " + hamburgLocation.getName() + " (ID: " + hamburgLocation.getId() + ")");

        // 2.2 Update Location Address
        System.out.println("\n>>> Updating Hamburg Location Address...");
        Address newHamburgAddress = new Address("20457", "Hafencity", "Hamburg", 25, "Germany");
        hamburgLocation.updateAddress(newHamburgAddress);
        locationManager.updateLocation(hamburgLocation);
        System.out.println("Updated Hamburg address to: " + hamburgLocation.getAddress().getStreet());

        // 2.3 Update Location Price Catalog
        System.out.println("\n>>> Updating Berlin Location to Premium Prices...");
        berlinLocation.updatePriceCatalog(premiumPrices);
        locationManager.updateLocation(berlinLocation);
        System.out.println("Berlin now uses Premium pricing");

        // 2.4 Get Location by Name
        System.out.println("\n>>> Finding Location by Name...");
        Optional<Location> foundLocation = locationManager.getLocationByName("Munich Downtown");
        foundLocation.ifPresent(loc -> System.out.println("Found: " + loc.getName() + " at " + loc.getAddress().getCity()));

        // 2.5 Get All Locations
        System.out.println("\n>>> All Locations:");
        System.out.println(locationManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 3: STATION MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 3: STATION MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 3.1 Add Stations
        System.out.println(">>> Adding Charging Stations...");
        Station station1 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, berlinLocation);
        Station station2 = new Station(ChargingType.DC, ChargingStatus.IN_OPERATION_FREE, berlinLocation);
        Station station3 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, munichLocation);
        Station station4 = new Station(ChargingType.DC, ChargingStatus.OUT_OF_ORDER, munichLocation);
        Station station5 = new Station(ChargingType.AC, ChargingStatus.IN_OPERATION_FREE, hamburgLocation);

        stationManager.addStation(station1);
        stationManager.addStation(station2);
        stationManager.addStation(station3);
        stationManager.addStation(station4);
        stationManager.addStation(station5);

        System.out.println("Station " + station1.getId() + ": " + station1.getType() + " at " + station1.getLocation().getName() + " - " + station1.getStatus());
        System.out.println("Station " + station2.getId() + ": " + station2.getType() + " at " + station2.getLocation().getName() + " - " + station2.getStatus());
        System.out.println("Station " + station3.getId() + ": " + station3.getType() + " at " + station3.getLocation().getName() + " - " + station3.getStatus());
        System.out.println("Station " + station4.getId() + ": " + station4.getType() + " at " + station4.getLocation().getName() + " - " + station4.getStatus());
        System.out.println("Station " + station5.getId() + ": " + station5.getType() + " at " + station5.getLocation().getName() + " - " + station5.getStatus());

        // 3.2 Update Station Type
        System.out.println("\n>>> Updating Station Type...");
        stationManager.updateStationType(station1.getId(), ChargingType.DC);
        System.out.println("Station " + station1.getId() + " type changed to: " + station1.getType());

        // 3.3 Update Station Status
        System.out.println("\n>>> Updating Station Status...");
        stationManager.updateStationStatus(station4.getId(), ChargingStatus.IN_OPERATION_FREE);
        System.out.println("Station " + station4.getId() + " status changed to: " + station4.getStatus());

        // 3.4 Update Station Location
        System.out.println("\n>>> Relocating Station...");
        stationManager.updateStationLocation(station5.getId(), berlinLocation);
        System.out.println("Station " + station5.getId() + " moved to: " + station5.getLocation().getName());

        // 3.5 View Station Status
        System.out.println("\n>>> Station Status Report:");
        System.out.println(station1.printStatus());
        System.out.println(station2.printStatus());

        // 3.6 Get All Stations
        System.out.println("\n>>> All Stations:");
        System.out.println(stationManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 4: CUSTOMER MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 4: CUSTOMER MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 4.1 Add Customers
        System.out.println(">>> Registering Customers...");
        Customer customer1 = new Customer("Anna Schmidt", "anna.schmidt@email.de", 100.0);
        Customer customer2 = new Customer("Max Mueller", "max.mueller@email.de", 50.0);
        Customer customer3 = new Customer("Lisa Weber", "lisa.weber@email.de", 75.0);

        customerManager.addCustomer(customer1);
        customerManager.addCustomer(customer2);
        customerManager.addCustomer(customer3);

        System.out.println("Registered: " + customer1.getName() + " (ID: " + customer1.getId() + ") - Credit: " + customer1.getCredit() + " EUR");
        System.out.println("Registered: " + customer2.getName() + " (ID: " + customer2.getId() + ") - Credit: " + customer2.getCredit() + " EUR");
        System.out.println("Registered: " + customer3.getName() + " (ID: " + customer3.getId() + ") - Credit: " + customer3.getCredit() + " EUR");

        // 4.2 Update Customer Information
        System.out.println("\n>>> Updating Customer Information...");
        customerManager.updateCustomer(customer2.getId(), "Maximilian Mueller", "maximilian.mueller@email.de");
        System.out.println("Updated customer " + customer2.getId() + " - New name: " + customer2.getName() + ", New email: " + customer2.getEmail());

        // 4.3 View Customer Balance
        System.out.println("\n>>> Viewing Customer Balances...");
        System.out.println(customer1.getName() + " balance: " + customer1.getCredit() + " EUR");
        System.out.println(customer2.getName() + " balance: " + customer2.getCredit() + " EUR");
        System.out.println(customer3.getName() + " balance: " + customer3.getCredit() + " EUR");

        // 4.4 Top Up Customer Balance
        System.out.println("\n>>> Topping Up Customer Balance...");
        double topUpAmount = 25.0;
        customer2.increaseCredit(topUpAmount);
        System.out.println(customer2.getName() + " topped up " + topUpAmount + " EUR - New balance: " + customer2.getCredit() + " EUR");

        // 4.5 Get Customer by ID
        System.out.println("\n>>> Retrieving Customer by ID...");
        Optional<Customer> foundCustomer = customerManager.getCustomerById(customer1.getId());
        foundCustomer.ifPresent(c -> System.out.println("Found: " + c.getName() + " (" + c.getEmail() + ")"));

        // 4.6 Get All Customers
        System.out.println("\n>>> All Customers:");
        System.out.println(customerManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 5: CHARGING SESSION MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 5: CHARGING SESSION MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Reset station type for demo
        stationManager.updateStationType(station1.getId(), ChargingType.AC);

        // 5.1 Start a Charging Session
        System.out.println(">>> Starting Charging Session for " + customer1.getName() + "...");
        ChargingSession session1 = customer1.startSession(station2, LocalDateTime.now().minusMinutes(45));
        sessionManager.addSession(session1);
        System.out.println("Session " + session1.getId() + " started at " + session1.getStartTime());
        System.out.println("  Station: " + station2.getId() + " (" + station2.getType() + ")");
        System.out.println("  Station Status: " + station2.getStatus());
        System.out.println("  Price Snapshot captured: " + session1.getPriceSnapshot().getKWhPriceDC() + " EUR/kWh (DC)");

        // 5.2 End the Charging Session
        System.out.println("\n>>> Ending Charging Session...");
        double energyConsumed1 = 25.0;
        customer1.endSession(LocalDateTime.now(), energyConsumed1, sessionManager);
        System.out.println("Session " + session1.getId() + " ended at " + session1.getEndTime());
        System.out.println("  Duration: " + session1.getDuration() + " minutes");
        System.out.println("  Energy consumed: " + session1.getEnergy() + " kWh");
        System.out.println("  Station Status: " + station2.getStatus());

        // 5.3 Calculate Session Cost
        System.out.println("\n>>> Calculating Session Cost...");
        double cost1 = session1.calculateCost();
        System.out.println("Session " + session1.getId() + " total cost: " + String.format("%.2f", cost1) + " EUR");
        System.out.println("  Breakdown: (" + session1.getDuration() + " min × " + session1.getPriceSnapshot().getMinutePriceDC() + " EUR/min) + (" + session1.getEnergy() + " kWh × " + session1.getPriceSnapshot().getKWhPriceDC() + " EUR/kWh)");

        // 5.4 Create Another Session (AC)
        System.out.println("\n>>> Starting AC Charging Session for " + customer2.getName() + "...");
        ChargingSession session2 = customer2.startSession(station3, LocalDateTime.now().minusMinutes(30));
        sessionManager.addSession(session2);
        double energyConsumed2 = 15.0;
        customer2.endSession(LocalDateTime.now(), energyConsumed2, sessionManager);
        double cost2 = session2.calculateCost();
        System.out.println("Session " + session2.getId() + ": " + session2.getDuration() + " min, " + session2.getEnergy() + " kWh = " + String.format("%.2f", cost2) + " EUR");

        // 5.5 Get Session by ID
        System.out.println("\n>>> Retrieving Session by ID...");
        Optional<ChargingSession> foundSession = sessionManager.getSessionById(session1.getId());
        foundSession.ifPresent(s -> System.out.println("Found Session " + s.getId() + " for customer " + s.getCustomer().getName()));

        // 5.6 Get All Sessions
        System.out.println("\n>>> All Charging Sessions:");
        System.out.println(sessionManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 6: INVOICE MANAGEMENT
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 6: INVOICE MANAGEMENT");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 6.1 Create Invoices
        System.out.println(">>> Creating Invoices...");
        System.out.println(customer1.getName() + " credit before invoice: " + customer1.getCredit() + " EUR");
        Invoice invoice1 = new Invoice(session1);
        invoiceManager.addInvoice(invoice1);
        System.out.println("Invoice " + invoice1.getId() + " created for Session " + session1.getId());
        System.out.println("  Amount: " + String.format("%.2f", invoice1.getAmount()) + " EUR");
        System.out.println("  Exported to CSV: " + invoice1.getWasExported());
        System.out.println(customer1.getName() + " credit after invoice: " + customer1.getCredit() + " EUR");

        System.out.println("\n>>> Creating second invoice...");
        System.out.println(customer2.getName() + " credit before invoice: " + customer2.getCredit() + " EUR");
        Invoice invoice2 = new Invoice(session2);
        invoiceManager.addInvoice(invoice2);
        System.out.println("Invoice " + invoice2.getId() + " created for Session " + session2.getId());
        System.out.println("  Amount: " + String.format("%.2f", invoice2.getAmount()) + " EUR");
        System.out.println(customer2.getName() + " credit after invoice: " + customer2.getCredit() + " EUR");

        // 6.2 Get Invoice by ID
        System.out.println("\n>>> Retrieving Invoice by ID...");
        Optional<Invoice> foundInvoice = invoiceManager.getInvoiceById(invoice1.getId());
        foundInvoice.ifPresent(inv -> System.out.println("Found Invoice " + inv.getId() + " - Amount: " + String.format("%.2f", inv.getAmount()) + " EUR"));

        // 6.3 Get All Invoices
        System.out.println("\n>>> All Invoices:");
        System.out.println(invoiceManager);

        // ═══════════════════════════════════════════════════════════════
        // SECTION 7: DELETE OPERATIONS
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("SECTION 7: DELETE OPERATIONS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // 7.1 Remove Invoice
        System.out.println(">>> Removing Invoice " + invoice2.getId() + "...");
        invoiceManager.removeInvoice(invoice2.getId());
        System.out.println("Invoice removed. Remaining invoices: " + invoiceManager.getAllInvoices().size());

        // 7.2 Remove Session
        System.out.println("\n>>> Removing Session " + session2.getId() + "...");
        sessionManager.removeSession(session2.getId());
        System.out.println("Session removed. Remaining sessions: " + sessionManager.getAllSessions().size());

        // 7.3 Remove Station
        System.out.println("\n>>> Removing Station " + station5.getId() + "...");
        stationManager.removeStation(station5.getId());
        System.out.println("Station removed. Remaining stations: " + stationManager.getAllStations().size());

        // 7.4 Remove Location
        System.out.println("\n>>> Removing Hamburg Location...");
        locationManager.removeLocation(hamburgLocation.getId());
        System.out.println("Location removed. Remaining locations: " + locationManager.getAllLocations().size());

        // 7.5 Remove Price Catalog
        System.out.println("\n>>> Removing Standard Price Catalog...");
        priceCatalogManager.removePriceCatalog(standardPrices.getId());
        System.out.println("Price catalog removed. Remaining catalogs: " + priceCatalogManager.getAllPriceCatalogs().size());

        // 7.6 Remove Customer (with no debt)
        System.out.println("\n>>> Removing Customer " + customer3.getName() + "...");
        customerManager.removeCustomer(customer3.getId());
        System.out.println("Customer removed. Remaining customers: " + customerManager.getAllCustomers().size());

        // 7.7 Demonstrate debt validation
        System.out.println("\n>>> Attempting to remove customer with potential debt...");
        System.out.println(customer1.getName() + " current credit: " + customer1.getCredit() + " EUR");
        if (customer1.getCredit() >= 0) {
            System.out.println("Customer has no debt - can be removed");
        } else {
            System.out.println("Customer has debt - cannot be removed until balance is positive");
        }

        // ═══════════════════════════════════════════════════════════════
        // FINAL SYSTEM STATE
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    FINAL SYSTEM STATE                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("--- Price Catalogs ---");
        System.out.println(priceCatalogManager);

        System.out.println("--- Locations ---");
        System.out.println(locationManager);

        System.out.println("--- Stations ---");
        System.out.println(stationManager);

        System.out.println("--- Customers ---");
        System.out.println(customerManager);

        System.out.println("--- Charging Sessions ---");
        System.out.println(sessionManager);

        System.out.println("--- Invoices ---");
        System.out.println(invoiceManager);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("Demo completed successfully! All ECSN features demonstrated.");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}
