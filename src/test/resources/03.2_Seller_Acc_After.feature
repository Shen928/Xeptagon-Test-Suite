Feature: Check Seller Account Balance After Limit Sell

  In order to validate the seller's account balance after placing a spot limit sell order
  As a seller
  I want to capture the account balance values after the order is placed

  Scenario: Validate seller account balance after executing spot limit matching order
    Given seller navigate to thes seller account page
    When seller retrieve the new seller account balance
    And seller validate that the gross balance has increased
    Then seller validate that the available balance has increased
