Feature: view charging station status

  As an operator or customer
  I want to see the current status of each charging station
  in order to see whether a station is available, in use, or out of service.

  Scenario: Viewing an available charging station
    Given a charging station 101 exists with status "IN_OPERATION_FREE"
    When I try to view the status of charging stations
    Then I see the Message "Station 101 is IN OPERATION and FREE"
    And the station's status is "IN_OPERATION_FREE"

  Scenario: Viewing a charging station that is in use
    Given a charging station 102 exists with status "IN_OPERATION_OCCUPIED"
    When I try to view the status of charging stations
    Then I see the Message "Station 102 is IN OPERATION but OCCUPIED"
    And the station's status is "IN_OPERATION_OCCUPIED"

  Scenario: Viewing an out-of-service charging station
    Given a charging station 103 exists with status "OUT_OF_ORDER"
    When I try to view the status of charging stations
    Then I see the Message "Station 103 is OUT OF ORDER"
    And the station's status is "OUT_OF_ORDER"


  Scenario: Viewing an out-of-service charging station
    Given a charging station 104 exists with status "IN_OPERATION_FREE"
    When I change the status to "OUT_OF_ORDER"
    And I try to view the status of charging stations
    Then I see the Message "Station 104 is OUT OF ORDER"
    And the station's status is "OUT_OF_ORDER"

    #error
  Scenario: viewing the status of non-existing station
    Given no charging station exists in the system
    When I try to view the status of the new charging stations
    Then I get shown the error message "Please select an existing station"