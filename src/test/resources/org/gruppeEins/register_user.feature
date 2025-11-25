Feature: register user

  As a customer
  I want to create my own account
  in order to use the charging services, manage my charging processes, and receive invoices.

  Scenario: Successful account creation
    Given I am a new user without an existing account
    When I provide my name "Alex Meyer", email "alex@example.com", and a secure password
    Then a new customer account is created
    And I receive a customer ID
    And my account balance is initialized to "0.00"

  Scenario: Registration with an already registered email
    Given a customer account already exists with the email "benjamin@example.com"
    When I try to register with the email "benjamin@example.com"
    Then I see the error message "Email already registered"
    And no new account is created

  Scenario: Registration with missing required fields
    Given I am a new user
    When I provide only an email "patrick@example.com" and leave other required fields empty
    Then I see the error message "Required information missing"
    And no customer account is created

  Scenario: Registration with invalid email address
    Given I am a new user
    When I try to register with the email "alex-example.com"
    Then I see the error message "Invalid email format"
    And no customer account is created
