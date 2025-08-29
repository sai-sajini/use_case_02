Feature: About Page - UI Flows
  The About page is accessible, displays correct information, and all UI components operate as expected.

  Background:
    Given user launches the browser and navigates to the base URL
    And user navigates to the About page via navigation bar

  Scenario: Verify About page loads and main sections are visible
    Then the About page title should be "About Us"
    And the company mission section should be displayed
    And the team section should be displayed
    And the "Learn More" button should be visible

  Scenario: Positive - Click 'Learn More' and verify modal content
    When user clicks on the "Learn More" button
    Then the about modal should open with header "Our Story"
    And the about modal content should not be empty
    When user closes the about modal
    Then the about modal should no longer be displayed

  Scenario: Negative - Attempt to interact with disabled button
    Given the "Learn More" button is disabled
    When user tries to click the "Learn More" button
    Then an error message "Button is disabled" should be displayed

  Scenario: Accessibility - Tab through major components
    When user presses Tab to navigate to the mission section
    Then focus should be on the mission section
    When user presses Tab to navigate to the team section
    Then focus should be on the team section
