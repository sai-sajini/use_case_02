Feature: About Page Functionality
  As a user
  I want to learn about the company in the About page
  So that I can trust the organization and its mission

  Background:
    Given I open the browser and navigate to the home page
    And I click on the About link in the navigation bar

  Scenario: User views About page information successfully
    Then the About page should be displayed
    And the heading should be "About Us"
    And the company mission statement should be visible
    And the team section should be visible
    And the company logo should be present

  Scenario: User verifies broken image on About page
    When the company logo is displayed
    Then the logo image should load correctly

  Scenario: User clicks the Learn More button
    When I click the Learn More button on the About page
    Then additional information section should be expanded
    And the details should be visible

  Scenario: User tries accessing About page directly
    Given I navigate directly to the About page URL
    Then the About page should be displayed

  Scenario: Negative - Learn More button unavailable
    Given the Learn More button is not present
    Then an error message should be logged: "Learn More button not found"
