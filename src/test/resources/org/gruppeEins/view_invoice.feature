Feature: View Invoice Details
  As a customer or operator
  I want to retrieve invoice details
  In order to understand payments, billing periods, and tax-relevant information.

  Background:
    Given first the "InvoiceManager" is initialized

  Scenario: Successfully retrieving a valid invoice
    Given a charging session exists with:
      | Duration (min) | Energy (kWh) | Total Cost |
      | 45.0           | 22.5         | 15.50      |
    And an invoice is generated for this session with ID 101
    When I retrieve the invoice with ID 101
    Then I should see the invoice details
    And the invoice amount should be 15.50
    And the invoice should be linked to the charging session with 22.5 kWh

  Scenario: Attempting to retrieve a non-existent invoice
    Given no invoice exists with ID 999
    When I attempt to retrieve the invoice with ID 999
    Then I should receive an error or empty result indicating "Invoice not found"