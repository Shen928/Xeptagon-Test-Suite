
Feature: Buyer Spot Limit Order Placement

  In order to trade assets on the platform
  As a buyer user
  I want to place spot buy limit orders after logging in

  Scenario Outline: Place a Buy Spot Limit Order
    Given the buyer navigates to the spot order placement page
    When the buyer selects the order type
    And the buyer enters a limit buy price of "<limit_buy_price>"
    And the buyer enters a quantity of "<quantity>"
    And the buyer submits the order
    And the buy order should be placed successfully
    And the buyer info notification should be displayed
    Then the buyer logout

    Examples:
      | limit_buy_price | quantity |
      | 5.00            | 1        |

#  Scenario Outline: Partially Filled Buy Order
#    Given the buyer navigates to the spot order placement page
#    When the buyer selects the order type
#    And the buyer enters a limit buy price of "<limit_buy_price>"
#    And the buyer enters a quantity of "<quantity>"
#    And the buyer submits the order
#    And the buy order should be placed successfully
#    And the buyer partially info notification should be displayed
#    Then the buyer logout
#
#    Examples:
#      | limit_buy_price | quantity |
#      | 50.00           | 8        |
