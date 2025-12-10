Feature: View prices

  As an operator or customer
  I want to view current prices and tariff information
  So that I am informed about the costs before or during a charging session

  Scenario: View prices for a location
    Given a location "Downtown" exists with a price catalog
    When I request the price information for location "Downtown"
    Then I should see the kWh price AC is 0.45 EUR and the kWh price DC is 0.60 EUR
    And I should see the minute price AC is 0.10 EUR and the minute price DC is 0.20 EUR

    #error
  Scenario: Cannot view prices for a location without a price catalog
    Given a location "FH Technikum Wien" exists without a price catalog
    When I try to request the price information for location "FH Technikum Wien"
    Then I should see an error that no price catalog is available