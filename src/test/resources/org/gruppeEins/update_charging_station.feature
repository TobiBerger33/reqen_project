Feature: Update Charging Station

  Scenario: Update charging station type
    Given a station manager with a station with ID 1, type "AC", status "IN_OPERATION_FREE", and location "Test Location"
    When I update the station with ID 1 to type "DC"
    Then the station with ID 1 should have type "DC"

  Scenario: Update charging station status
    Given a station manager with a station with ID 2, type "DC", status "IN_OPERATION_OCCUPIED", and location "Another Location"
    When I update the station with ID 2 to status "OUT_OF_ORDER"
    Then the station with ID 2 should have status "OUT_OF_ORDER"

  Scenario: Update charging station location
    Given a station manager with a station with ID 3, type "AC", status "IN_OPERATION_FREE", and location "Old Location"
    And a new location "New Location"
    When I update the station with ID 3 to location "New Location"
    Then the station with ID 3 should have location "New Location"

  Scenario: Attempt to update a non-existent station
    Given a station manager with no stations
    When I attempt to update the station with ID 99 to type "DC"
    Then no station with ID 99 should exist

  Scenario: Attempt to update station with invalid type
    Given a station manager with a station with ID 4, type "AC", status "IN_OPERATION_FREE", and location "Valid Location"
    When I attempt to update the station with ID 4 to type "INVALID_TYPE"
    Then an error should occur

  Scenario: Attempt to update station location to a non-existent location
    Given a station manager with a station with ID 5, type "AC", status "IN_OPERATION_FREE", and location "Initial Location"
    When I attempt to update the station with ID 5 to a non-existent location "NonExistentLocation"
    Then the location of station with ID 5 should remain "Initial Location"