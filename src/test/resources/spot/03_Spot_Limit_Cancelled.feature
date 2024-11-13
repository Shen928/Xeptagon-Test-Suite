Feature: Seller Spot Limit Full Filled Order Placement and Cancellation

  In order to trade assets on the platform
  As a seller user
  I want to place spot sell limit orders, and then cancel them to verify account and carbon credit balance updates

  Scenario Outline: Check seller login is successful with valid credentials
    Given user is on login
    When user enter valid "<email>" and "<password>"
    And clicks on Login Button
    Then user is navigate to market page
#    And close the browser
    Examples:
      | email | password |
      | shehans+EX2@xeptagon.com | EX2@xeptagon.coM |

  Scenario: Validate account balance before executing spot limit sell order
    Given seller navigate to the account page
    When seller should retrieve the seller's account balance
    Then store the seller account balance values

  Scenario: Validate carbon credit balance before executing spot limit sell order
    Given seller retrieves the seller's total credit balance and available credit balance
    Then store the seller total credit balance and available credit balance

  Scenario Outline: Place a Sell Spot Limit Order
    Given the seller navigates to the spot order placement page
    When the seller selects the order type
    And the seller enters a limit sell price of "<limit_sell_price>"
    And the seller enters a quantity of "<quantity>"
    And the seller submits the order
    And the sell order should be placed successfully

    Examples:
      | limit_sell_price | quantity |
      | 5.00             | 1        |
      | 6.00             | 1        |
      | 7.00             | 1        |
#      | 8.00             | 1        |
#      | 9.00             | 1        |

  Scenario: Seller should able to cancel all spot limit sell orders
    Given seller cancels all open spot limit sell orders
    Then all orders should be canceled successfully

  Scenario: Validate seller credit balance after executing spot limit matching order
    Given the seller navigates to the account page
    When seller retrieve the new seller credit balances
    Then the available carbon credit balance should equal the initial credit balance

  Scenario: Validate seller account balance after executing a spot limit sell order
    Given the seller retrieves the new account balance
    Then the seller's available account balance should be unchanged from the initial available account balance

