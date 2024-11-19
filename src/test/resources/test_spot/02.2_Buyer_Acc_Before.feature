Feature: Check Buyer Account Balance Before Limit Buy

  In order to validate the buyer's account balance before placing a spot limit buy order
  As a buyer
  I want to capture the initial account balance values before the order is placed

  Scenario: Validate account balance before executing spot limit buy order
    Given buyer navigates to the account page
    When buyer retrieves the buyer's account balance
    Then store the buyer account balance values


  Scenario: Validate carbon credit balance before executing spot limit buy order
    Given buyer retrieves the buyer's total credit balance and available credit balance
    Then store the buyer total credit balance and available credit balance
