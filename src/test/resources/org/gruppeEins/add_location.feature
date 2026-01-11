Feature: Add new locations

  As an operator
  I want to create new locations in the system
  In order to make new operating sites quickly available and visible in the network.

  Background:
    Given the "LocationManager" is initialized, contains no locations, therefore throws an error when retrieving all locations

  Scenario: Successfully adding a new location with a valid address
    Given I have a new location details with Street "Technikumplatz", Number 1, Zip "1200", City "Vienna", and Country "Austria"
    When I add this new location to the network
    Then the operation should be successful
    And the total number of locations should be 1
    And I should see the location "Vienna" in the location list

  Scenario: Adding multiple locations to the network
    Given the following locations exist to be added:
      | Street      | Number | Zip  | City    | Country |
      | Hauptstr.   | 10     | 1010 | Vienna  | Austria |
      | Linzerstr.  | 45     | 4020 | Linz    | Austria |
    When I add these locations to the network
    Then the total number of locations should be 2
    And I should be able to retrieve the location with City "Linz" via its ID

    #error
  Scenario: preventing the addition of a location without address data
    Given I attempt to add a location without an address
    When I execute the add command
    Then I should receive an error message "Address data is missing"
    And there are no locations
