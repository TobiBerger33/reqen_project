Feature: Edit customer data
  As a customer, I want to edit my personal data to keep it up to date.

  Scenario: Edit customer's name and email
    Given a customer with id 1, name "Max Mustermann", and email "max.mustermann@example.com"
    When the customer with id 1 updates their name to "Maximilian Mustermann" and email to "maximilian.mustermann@example.com"
    Then the customer with id 1 should have the name "Maximilian Mustermann" and the email "maximilian.mustermann@example.com"

  Scenario: Attempt to edit a non-existent customer
    Given no customer with id 99 exists
    When an attempt is made to update the name of customer with id 99 to "John Doe"
    Then the system should not find a customer with id 99 to update

  Scenario: Attempt to update email to an invalid format
    Given a customer with id 2, name "Jane Doe", and email "jane.doe@example.com"
    When the customer with id 2 attempts to update their email to "jane.doe"
    Then the update should fail with an "Invalid email format" error

  Scenario: Attempt to update email to an already existing email
    Given a customer with id 3, name "Peter Jones", and email "peter.jones@example.com"
    And another customer with id 4, name "Mary Jane", and email "mary.jane@example.com"
    When the customer with id 3 attempts to update their email to "mary.jane@example.com"
    Then the update should fail with an "Email already registered" error

  Scenario: Attempt to update name to an empty string
    Given a customer with id 5, name "Initial Name", and email "initial.name@example.com"
    When the customer with id 5 attempts to update their name to ""
    Then the update should fail with an "Name cannot be empty" error
