Feature: charging tariffs

  As an operator
  I want to be able to define rates for charging
  in order to offer customers consistent and transparent prices

  Scenario: Create a price catalog with AC and DC charging rates
    Given I want to create a new price catalog and define rates for charging
    When I create a new price catalog with 0.35 EUR per kWh AC and 0.45 EUR per kWh DC
    And I set the minute prices to 0.05 EUR for AC and 0.08 EUR for DC
    And I set the validity date to 2025-01-01
    Then the price catalog should be saved successfully
    And the price catalog should be added to the PriceCatalogManager

  Scenario: Assign price catalog to a location with stations
    Given a price catalog exists with 0.40 EUR per kWh AC and 0.50 EUR per kWh DC
    And the price catalog has minute prices of 0.06 EUR for AC and 0.10 EUR for DC
    And a location with 3 stations exists
    When I assign the price catalog to the location
    Then the location should reference the price catalog
    And all stations at the location should inherit this price catalog