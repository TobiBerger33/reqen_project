Feature: start and end charging session

  As a user
  I want to be able to start and end my charging session
  in order to charge my vehicle and receive the correct invoice

  Scenario: starting new charging session
    Given A user with name "Ben" and email "ben@email.com" exists in the system
    And a station exists in the system
    When the user starts a charging session with start time "2025-12-03T11:50:55"
    Then a charging session gets created with the station and start time "2025-12-03T11:50:55"
          #LocalDateTime dateTime = LocalDateTime.parse("2025-12-03T11:50:55");

  Scenario: ending current charging session
    Given A user with name "Otto" and email "otto@email.com" exists in the system
    And a station exists in the system
    And the user has an active charging session with start time "2025-12-03T12:34:19"
    When the user ends the session with his ID with end time "2025-12-03T12:59:07" and total energy consumed 39.7
    Then the session is ended with end time "2025-12-03T12:59:07"
    And the sessions total price is calculated

    #error
  Scenario: trying to end non-existing charging session
    Given A user with name "Frank" and email "frankenstein@email.com" exists in the system
    And a station exists in the system
    But no charging session exists for the user
    When the user ends the session with his ID with end time "2025-12-03T10:11:12" and total energy consumed 59.3
    Then the user receives the notification "There is no current charging session for this user"

      #edge
  Scenario: ending session with 0 minutes duration
    Given A user with name "Karl" and email "karl@email.com" exists in the system
    And a station exists in the system
    And the user has started a charging session with start time "2025-12-03T11:50:55"
    When the user trys to end the session with his ID with end time "2025-12-03T11:50:55" and total energy consumed 89.7
    Then the user receives the notification "Charging session failed. Please try again"

     #edge
  Scenario: ending session with 0 kW energy consumed
    Given A user with name "John" and email "john@email.com" exists in the system
    And a station exists in the system
    And the user has started a charging session with start time "2025-12-03T10:50:45"
    When the user trys to end the session with his ID with end time "2025-12-03T12:59:23" and total energy consumed 0.0
    Then the user receives the notification "Charging session failed. Please try again"

    #error
  Scenario: ending session with end time before start time
    Given A user with name "Julie" and email "julie@email.com" exists in the system
    And a station exists in the system
    And the user has started a charging session with start time "2025-12-03T10:50:45"
    When the user trys to end the session with his ID with end time "2025-12-03T10:29:23" and total energy consumed 23.7
    Then the user receives the notification "End time cannot be before start time"