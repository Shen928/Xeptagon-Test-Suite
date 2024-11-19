
Feature: Seller Spot Limit Partially Filled Order Placement and Cancelled

  In order to trade assets on the platform,
  both sellers and buyers can place spot limit sell and buy orders after logging in,
  which may be partially canceled.

  Scenario Outline: Check seller login is successful with valid credentials
    Given user is on login
    When user enter valid "<email>" and "<password>"
    And clicks on Login Button
    Then user is navigate to market page
#    And close the browser
    Examples:
      | email | password |
      | shehans+EX2@xeptagon.com | EX2@xeptagon.coM |

  Scenario: Validate seller account balance before executing spot limit sell order
    Given seller navigate to the account page
    When seller should retrieve the seller's account balance
    Then store the seller account balance values

  Scenario: Validate seller carbon credit balance before executing spot limit sell order
    Given seller retrieves the seller's total credit balance and available credit balance
    Then store the seller total credit balance and available credit balance

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
      | 5.00             | 2        |
#      | 5.00             | 2        |

  Scenario Outline: Check buyer login is successful with valid credentials
    Given user is on login
    When user enter valid "<email>" and "<password>"
    And clicks on Login Button
    Then user is navigate to market page

    Examples:
      | email                    | password            |
      | shehans+EX1@xeptagon.com | EX1@xeptagon.coM    |

  Scenario: Validate account balance before executing spot limit buy order
    Given buyer navigates to the account page
    When buyer retrieves the buyer's account balance
    Then store the buyer account balance values

  Scenario: Validate carbon credit balance before executing spot limit buy order
    Given buyer retrieves the buyer's total credit balance and available credit balance
    Then store the buyer total credit balance and available credit balance

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
      | 5.00            | 7        |

  Scenario: Validate buyer account balance after executing spot limit matching order
    Given buyer retrieve the new buyer account balance
    When buyer validate that the gross balance has decreased
    Then buyer validate that the available balance has decreased
    And buyer validate that the block amount has changed
#    And buyer logout from application

  Scenario: Validate buyer credit balance after executing spot limit matching order
    Given buyer retrieve the new buyer credit balances
    When buyer validate that the total credit balance has increased
    Then buyer validate that the available credit balance has increased


  Scenario: Buyer cancels partially filled open spot limit buy orders
    Given buyer navigates to the account page
    When buyer retrieve the new buyer account balance
    Then the buyer navigates to the spot order placement page
    And the buyer cancels the partially filled open spot limit buy order
    And the partially filled spot order should be canceled successfully
    When buyer retrieve the new buyer account balance after cancelling
    Then the fiat currency available balance and block amount should be changed
    And buyer logout from application

  Scenario: Validate seller account balance after executing a spot limit sell order
    Given the seller logs into their account
    When the seller navigates to the account page
    And the seller retrieves the new account balance
    Then the seller validates that the gross balance has increased
    And the seller validates that the available balance has increased

  Scenario: Validate seller credit balance after executing spot limit matching order
    Given seller retrieve the new seller credit balances
    When seller validate that the total credit balance has decreased
    Then seller validate that the available credit balance has decreased







