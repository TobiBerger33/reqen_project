Feature: charging tariffs
  As an operator
  I want to be able to define general tariffs for charging
  in order to offer my customers consistent and transparent prices

  Scenario: Create a price catalog with AC and DC charging rates
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

  Scenario: Calculate charging cost based on price catalog
    Given a location has a price catalog with 0.40 EUR per kWh AC
    And the price catalog has a minute price of 0.05 EUR for AC
    And a customer starts a charging session at an AC station
    When the customer charges 25 kWh over 60 minutes
    And the charging session ends
    Then the total cost should be calculated as 13.00 EUR
    And an invoice should be created referencing the price catalog