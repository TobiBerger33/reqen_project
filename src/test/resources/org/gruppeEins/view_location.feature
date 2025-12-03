Feature: view all location

  As an operator or customer
  I want to see an overview of all locations
  in order to quickly find or manage available charging locations.

  Scenario: View an overview of all charging locations
    Given the system contains the locations "City Center", "Airport", and "University Campus"
    When I request the overview of all locations
    Then I see a list containing "City Center", "Airport", and "University Campus"

    #error
  Scenario: View only locations that currently have available charging points
    Given the system contains location "Mall" with available charging points
    And the system contains location "Train Station" with no available charging points
    And the system contains another location "Harbor" with available charging points
    When I filter locations by availability
    Then I see "Mall" and "Harbor"
    And I do not see "Train Station"

    #error
  Scenario: No locations found
    Given the system contains no locations
    When I request the overview of all locations
    Then I see a message "No locations available"