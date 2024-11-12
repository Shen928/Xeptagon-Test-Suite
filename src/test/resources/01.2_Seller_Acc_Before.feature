Feature: Check Seller Account Balance Before Limit Sell

  In order to validate the seller's account balance before placing a spot limit sell order
  As a seller
  I want to capture the initial account balance values before the order is placed

  Scenario: Validate account balance before executing spot limit sell order
    Given seller navigate to the account page
    When seller should retrieve the seller's account balance
    Then store the seller account balance values

  Scenario: Validate carbon credit balance before executing spot limit sell order
    Given seller retrieves the seller's total credit balance and available credit balance
    Then store the seller total credit balance and available credit balance