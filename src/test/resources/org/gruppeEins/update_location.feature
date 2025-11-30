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
    When I try to change the address of location with ID 202 to a non existing one
    Then the address is not changed
    And the locations address is still "that street" in "this city"
    And I see the error "Please use a valid address"

  Scenario: Update location with valid new price catalog
    Given a location with ID 303 and price catalog with a KW price AC of 1.25 exists in the system
    When I update the price catalog of the locations with ID 303 to have a KW price AC of 1.50
    Then the price catalog is updated
    And the locations KW price AC shows 1.50

  Scenario: Add station to location
    Given a location with ID 404 and a station with ID 1001 exist in the system
    When I add the station with ID 1001 to the location with ID 404
    Then the station with ID 1001 is saved to the location
    And the station with ID 1001 is listed under the location's stations

  Scenario: Remove station from location
    Given a location with ID 505 and a station with ID 2002 exist in the system
    And the station with ID 2002 is added to the location with ID 505
    When I remove the station to the location
    Then the station with ID 2002 is no longer listed under the location's stations