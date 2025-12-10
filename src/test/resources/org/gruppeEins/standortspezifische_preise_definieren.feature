Feature: Location-specific pricing

  As an operator
  I want to define individual prices for specific locations
  So that regional differences or local conditions (e.g. electricity costs, demand) can be considered

  Scenario: Set individual prices for a specific location
    Given I create a location "FH Technikum Wien" with address "Hoechstaedtplatz" in "Vienna"
    When I assign a price catalog with 0.5 EUR per kWh AC and 0.6 EUR per kWh DC to location "FH Technikum Wien"
    Then location "FH Technikum Wien" should have 0.5 EUR per kWh AC
    And location "FH Technikum Wien" should have 0.6 EUR per kWh DC

  Scenario: Cannot get prices from a station at a location without a price catalog
    Given I create a location "FH Technikum Wien" with address "Hoechstaedtplatz" in "Vienna"
    When a station with ID 3 is added to location "FH Technikum Wien"
    And I try to get the current prices from station 3
    Then the station should return an error