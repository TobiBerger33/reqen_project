Feature: View and top up balance

  As a customer, I want to be able to see and top up my balance,
  so that I always have enough funds to carry out charging processes.

  Scenario: A customer wants to see their balance
    Given a customer with an initial balance of 100.0
    When the customer checks their balance
    Then the balance should be 100.0

  Scenario: A customer tops up their balance
    Given a customer with an initial balance of 100.0
    When the customer trys to top up their balance with 50.0
    Then the new balance should be 150.0

    #error
  Scenario: add negative amount
    Given a customer with an initial balance of 100.0
    When the customer trys to top up their balance with -30.0
    And I receive the error message "Please select a positive amount greater than 0"

    #edge
  Scenario: A customer tops up their balance by 0
    Given a customer with an initial balance of 85.6
    When the customer trys to top up their balance with 0.0
    And the customer checks their balance
    Then the balance should be 85.6
    And I receive the error message "Please select a positive amount greater than 0"
