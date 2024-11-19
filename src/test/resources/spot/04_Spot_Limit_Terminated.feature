
Feature: Seller Spot Limit Terminated Order Placement

  In order to trade assets on the platform,
  users can place spot limit sell and buy orders,
  which will be terminated (self-matching) after logging in.

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
#    Then the seller logout

    Examples:
      | limit_sell_price | quantity |
      | 5.00             | 1        |
#      | 5.00             | 2        |

  #In here seller and buyer will be same user because seller login system and placed sell order and buy order for system terminated (self matching)
  Scenario Outline: Place a Buy Spot Limit Order
    Given the buyer navigates to the spot order placement page
    When the buyer selects the order type
    And the buyer enters a limit buy price of "<limit_buy_price>"
    And the buyer enters a quantity of "<quantity>"
    And the buyer submits the order
    And the buy order should be placed successfully
    And the buyer info notification should be displayed
#    Then the buyer logout

    Examples:
      | limit_buy_price | quantity |
      | 5.00            | 3        |


  Scenario: Validate seller credit balance after executing spot limit matching order
    Given the seller navigates to the account page
    When seller retrieve the new seller credit balances
    Then the available carbon credit balance should equal the initial credit balance

  Scenario: Validate seller account balance after executing a spot limit sell order
    Given the seller retrieves the new account balance
    Then the seller validates that the block amount has increased
    And the seller validates that the available balance has decreased

  Scenario: Buyer cancel all open orders
    Given the buyer navigates to the spot order placement page
    When buyer cancels all open spot limit sell orders
    Then buyer all orders should be canceled successfully