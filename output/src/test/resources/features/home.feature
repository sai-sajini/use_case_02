Feature: Home page functionality
  As a user
  I want to interact with the Home page
  So that I can verify its major components and flows

  Background:
    Given I am on the Home page

  Scenario: Positive Home page login flow
    When I enter valid username and password
    And I click on the login button
    Then I should see the welcome message on the Home page
    And I should see the Navigation Bar displayed

  Scenario: Negative Home page login flow with invalid credentials
    When I enter invalid username and password
    And I click on the login button
    Then I should see an error message displayed
    And I should NOT see the welcome message on the Home page

  Scenario: Home page navigation to Products page through Navigation Bar
    When I click on the Products link in the Navigation Bar
    Then I should be redirected to the Products page
    And I should see the Products page title displayed

  Scenario: Home page navigation to About page through Navigation Bar
    When I click on the About link in the Navigation Bar
    Then I should be redirected to the About page
    And I should see the About page title displayed
