
Feature: Login Page Automation of Xeptagon

  Scenario Outline: Check buyer login is successful with valid credentials
    Given user is on login
    When user enter valid "<email>" and "<password>"
    And clicks on Login Button
    Then user is navigate to market page

    Examples:
      | email                    | password            |
      | shehans+EX1@xeptagon.com | EX1@xeptagon.coM |

