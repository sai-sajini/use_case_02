Feature: Home Page Validation
  As a user
  I want to view the home page and navigate to key areas
  So that I can access information and features

  Background:
    Given I am on the Home page

  Scenario: Verify Home page elements are displayed
    Then the Home page logo should be visible
    And the main menu should contain About, Products, and Contact links
    And the search bar should be displayed

  Scenario: Search with valid keyword
    When I enter "Selenium" into the search bar
    And I click the search button
    Then search results for "Selenium" should be displayed

  Scenario: Search with invalid keyword
    When I enter "zzzzz" into the search bar
    And I click the search button
    Then an error message should be shown with text "No results found"

  Scenario: Navigate to About page via menu
    When I click the About link in the main menu
    Then I should be redirected to the About page

  Scenario: Navigate to Products page via menu
    When I click the Products link in the main menu
    Then I should be redirected to the Products page

  Scenario: Navigate to Contact page via menu
    When I click the Contact link in the main menu
    Then I should be redirected to the Contact page

  Scenario: Home page overlay popup blocking interactions
    Given the Home page overlay popup is visible
    When I close the overlay popup
    Then I should be able to interact with the Home page elements
