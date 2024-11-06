Feature: Check Seller Account Balance Before Limit Sell

  In order to validate the seller's account balance before placing a spot limit sell order
  As a seller
  I want to capture the initial account balance values before the order is placed

  Scenario: Validate account balance before executing spot limit sell order
    Given seller navigate to the account page
    When seller should retrieve the seller's account balance
    Then store the seller account balance values

