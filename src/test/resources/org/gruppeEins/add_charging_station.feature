Feature: add charging point

  As operator
  I want to add charging points to locations
  in order to quickly put charging points into operation and see them in the system.

  Background: I am logged in as an operator

  Scenario: Add a new charging point to an existing location
    Given a location "City Center" exists in the system
    When I add a charging point with identifier "101" of type "AC" to "City Center"
    Then the charging point "101" is stored in the system
    And its initial state is "IN_OPERATION_FREE"
    And it is visible under the location

  Scenario: Attempt to add a charging point with a duplicate identifier
    Given a location "Airport Station" exists with a charging point "200"
    When I try to add another charging point with identifier "200" to "Airport Station"
    Then the system rejects the request
    And I see an error message "Charging point identifier already exists at this location"

  Scenario: Add charging point to a non-existent location
    When I try to add a charging point with identifier "300" of type "DC" to the location "Unknown Plaza"
    Then the system rejects the request
    And I see an error message "Location not found"
    And the charging point is not created
