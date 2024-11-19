
Feature: Login Page Automation of Xeptagon

  Scenario Outline: Check seller login is successful with valid credentials
    Given user is on login
    When user enter valid "<email>" and "<password>"
    And clicks on Login Button
    Then user is navigate to market page
#    And close the browser
    Examples:
      | email | password |
      | shehans+EX2@xeptagon.com | EX2@xeptagon.coM |
#      | shehans+exuser2@xeptagon.com | shehans+exuser2@xeptagon.coM |
#      | shehans+exuser2@xeptagon.com| shehans+exuser2@xeptagon.co |
