Feature: About Page Functionality
  As a user
  I want to view details about the company
  So that I can learn more about its history and team

  Background:
    Given I am on the Home page
    When I click on the About link
    Then I should be navigated to the About page

  Scenario: Verify About page loads successfully
    Given I am on the About page
    Then I should see the company description
    And I should see the team members section
    And I should see the company mission statement

  Scenario: Verify navigation back to Home page
    Given I am on the About page
    When I click on the Home link
    Then I should be navigated to the Home page

  Scenario: Verify About page handles missing company description error
    Given the company description is not loaded due to backend error
    When I visit the About page
    Then I should see an error message "Company description could not be loaded"

  Scenario: Verify contact link on About page works
    Given I am on the About page
    When I click on the Contact Us link
    Then I should be navigated to the Contact page
