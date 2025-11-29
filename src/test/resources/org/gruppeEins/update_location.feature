Feature: edit location

  As an operator
  I want the ability to edit existing location information
  in order to keep details such as address, contact information, and operational status accurate and up to date.

  Scenario: Update location with valid new address
    Given a location with ID 101 and address with street "some street" and city "this city" exists in the system
    When I update the location with ID 101 and change the address to a new address with street "another street" in "new town city"
    Then the new address is saved as the locations address
    And the locations address is "another street" in "new town city"

  Scenario: Update location with invalid new address
    Given a location with ID 202 and address with street "that street" and city "this city" exists in the system
    When I try to change the address to a non existing one
    Then the address is not changed
    And the locations address is "that street" in "this city"
    And I see the error "Address not available"

  Scenario: Update location with valid new price catalog
    Given a location with a price catalog with a KW price AC of 1.25 exists in the system
    When I update the locations price catalog to have a KW price AC of 1.50
    Then the price catalog is updated
    And the locations KW price AC shows 1.50

  Scenario: Add station to location
    Given a location and a station with ID 101 exist in the system
    When I add the station to the location
    Then the station with ID 101 is save to the location
    And the station with ID 101 is listed under the location's stations

  Scenario: Add station to location
    Given a location and a station with ID 101 exist in the system
    And the station is added to the location
    When I remove the station to the location
    Then the station with ID 101 is removed from the location
    And the station with ID 101 is no longer listed under the location's stations
