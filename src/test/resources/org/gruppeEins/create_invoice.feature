Feature: create invoice

  As an operator
  I want to be able to generate invoices for completed charging processes or billing periods
  in order to provide my customers with accurate and transparent billing.

  Scenario: Generate an invoice for a completed charging process
    Given a completed charging process with identifier 101 exists with a total cost of 15.20 EUR
    When I generate an invoice for that charging session
    Then an invoice is created with an amount of 15.20 EUR
    And the invoice is linked to charging session with identifier 101

    #error
  Scenario: Cannot generate invoice for an incomplete charging process
    Given a charging session with identifier 99 exists but is not completed
    When I try to generate an invoice for that charging session
    Then the invoice is not created
    And I see the error-message "Charging session not completed"

  Scenario: Generate a CSV file for an issued invoice
    When an issued invoice with identifier 5001 exists with customer with identifier 77, amount 18.40 EUR
    Then a CSV file named "ECSN-INV-5001.csv" is created
    And wasExported of the invoice is set to "true"