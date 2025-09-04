Feature: Home Page Functionality
  As a user
  I want to interact with the home page
  So that I can verify its UI components and flows

  Background:
    Given the browser is open
    And I am on the Home Page

  # Positive scenario: Home page loads and header is displayed
  Scenario: Verify Home page loads successfully
    Then the Home Page title should be "Home - DemoApp"
    And the Home header should be displayed
    And all main menu items should be visible

  # Negative scenario: Attempt to access non-existent section
  Scenario: Access invalid menu item
    When I click on menu item "InvalidMenu"
    Then I should see an error message "Page Not Found"

  # Positive scenario: Navigate to About page from Home
  Scenario: Navigate to About page from Home
    When I click on menu item "About"
    Then I should be redirected to About Page

  # Positive scenario: Drag and Drop component
  Scenario: Perform drag and drop on Home page
    When I drag the "SampleItem" element and drop it on the "DropZone"
    Then I should see message "Drag & Drop successful!"

  # Negative scenario: Form submission without required inputs
  Scenario: Try to submit newsletter without email
    When I click on Newsletter Subscribe button
    Then I should see error message "Email is required"

