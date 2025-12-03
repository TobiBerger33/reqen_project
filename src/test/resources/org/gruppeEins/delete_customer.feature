Feature: Delete Customer Account
  As a customer
  I want to delete or deactivate my account
  In order to ensure my data is removed after I stop using the service.

  Background:
    Given the "CustomerManager" is initialized

  Scenario: Successfully deleting a customer account with no debts
    Given a customer "John Doe" exists with email "john@example.com" and 0.0 credit
    When I delete the customer "John Doe"
    Then the customer "John Doe" should no longer exist in the system
    And the total number of customers should be 0

    #edge
  Scenario: Preventing deletion of a customer with outstanding debts
    Given a customer "Debtor Dan" exists with email "dan@example.com" and -50.0 credit
    When I attempt to delete the customer "Debtor Dan"
    Then I should receive a deletion error message "Cannot delete customer with outstanding debt"
    And the customer "Debtor Dan" should still exist in the system

    #error
  Scenario: Attempting to delete a non-existent customer
    Given no customer exists with ID 999
    When I attempt to delete the customer with ID 999
    Then the operation should complete without error
    And the total number of customers should remain 0