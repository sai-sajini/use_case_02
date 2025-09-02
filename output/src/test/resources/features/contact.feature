Feature: Contact Us Form Submission
  As a user
  I want to contact the company through the contact page
  So that I can send my message and expect a response

  Background:
    Given I open the browser and launch the application
    And I navigate to the Contact page

  Scenario: Successfully submitting the contact form
    When I enter valid contact details
    And I click the Send button
    Then I should see a success message on the contact page
    And the form should be reset

  Scenario: Submitting the contact form with missing required fields
    When I enter incomplete contact details
    And I click the Send button
    Then I should see validation errors for required fields
    And the form should not be submitted

  Scenario: Submitting the contact form with invalid email address
    When I enter an invalid email in the contact details
    And I click the Send button
    Then I should see an invalid email error message
    And the form should not be submitted

  Scenario: Attempting to upload an unsupported file type
    When I attempt to upload an unsupported file type in the contact form
    Then I should see an unsupported file type error message
