Feature: Check Seller Account Balance After Limit Sell

  In order to validate the seller's account balance after placing a spot limit sell order
  As a seller
  I want to capture the account balance values after the order is placed

  Scenario: Validate seller account balance after executing a spot limit sell order
    Given the seller logs into their account
    When the seller navigates to the account page
    And the seller retrieves the new account balance
    Then the seller validates that the gross balance has increased
    And the seller validates that the available balance has increased
