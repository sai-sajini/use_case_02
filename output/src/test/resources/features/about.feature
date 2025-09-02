Feature: About Page Validation
  As a user of the demo application
  I want to verify the About page functionality
  So that I can view company information and use contact options

  Background:
    Given User is on the Home page

  @AboutPositive
  Scenario: Navigate to About page and view company info
    When User clicks on the About link
    Then User should be navigated to the About page
    And Company mission statement should be displayed

  @AboutNegative
  Scenario: Attempt to open a non-existent About section
    When User clicks on the About link
    And User tries to access an invalid About tab "NonExistentTab"
    Then An error message should be shown for invalid tab
