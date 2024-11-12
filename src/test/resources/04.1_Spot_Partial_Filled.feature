Feature: Spot Limit Partial Filled Order Placement

  @sell
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

  @partialBuy
  Scenario Outline: Partially Filled Buy Order
    Given the buyer navigates to the spot order placement page
    When the buyer selects the order type
    And the buyer enters a limit buy price of "<limit_buy_price>"
    And the buyer enters a quantity of "<quantity>"
    And the buyer submits the order
    And the buy order should be placed successfully
    And the buyer partially info notification should be displayed
    Then the buyer logout

    Examples:
      | limit_buy_price | quantity |
      | 5.00            | 5        |
