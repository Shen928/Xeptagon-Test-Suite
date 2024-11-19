
Feature: Seller Spot Limit Order Placement

  In order to trade assets on the platform
  As a seller user
  I want to place spot sell limit orders after logging in

  Scenario Outline: Place a Sell Spot Limit Order
    Given the seller navigates to the spot order placement page
    When the seller selects the order type
    And the seller enters a limit sell price of "<limit_sell_price>"
    And the seller enters a quantity of "<quantity>"
    And the seller submits the order
    And the sell order should be placed successfully
    Then the seller logout

    Examples:
      | limit_sell_price | quantity |
      | 5.00             | 1        |
      | 5.00             | 2        |
