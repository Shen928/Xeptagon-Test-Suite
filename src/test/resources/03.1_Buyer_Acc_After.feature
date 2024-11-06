Feature: Check Buyer Account Balance After Limit Buy

  In order to validate the buyer's account balance after placing a spot limit buy order
  As a buyer
  I want to capture the account balance values after the order is placed

  Scenario: Validate buyer account balance after executing spot limit matching order
    Given buyer retrieve the new buyer account balance
    When buyer validate that the gross balance has decreased
    Then buyer validate that the available balance has decreased
