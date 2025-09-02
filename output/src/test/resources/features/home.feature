Feature: Home page functionalities

  Background:
    Given the user launches the application
    And the user is on the Home page

  Scenario: Successful login with valid credentials
    When the user enters valid username and password
    And clicks the login button
    Then the user should be navigated to the Dashboard

  Scenario: Attempt login with invalid credentials
    When the user enters invalid username and password
    And clicks the login button
    Then an error message should be displayed

  Scenario: Navigate to About page from Home
    When the user clicks on the About link
    Then the About page should be displayed

  Scenario: Navigate to Products page from Home
    When the user clicks on the Products menu
    Then the Products page should be displayed

  Scenario: Logout from home page
    When the user clicks the logout button
    Then the user should be navigated to the Login screen
