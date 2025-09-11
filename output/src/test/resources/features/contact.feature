Feature: Contact Page Functionality
  As a user
  I want to contact the company using the contact form
  So that I can send feedback or inquiries

  Background:
    Given I am on the Contact page

  Scenario: Successful Message Submission
    When I enter valid name "John Doe"
    And I enter valid email "john.doe@example.com"
    And I enter valid subject "Product Inquiry"
    And I enter valid message "I would like to know more about your products."
    And I click on the Submit button
    Then I should see a success message "Thank you for contacting us!"

  Scenario: Submission with Missing Name
    When I leave the name field empty
    And I enter valid email "jane.doe@example.com"
    And I enter valid subject "Support Request"
    And I enter valid message "Help needed with the login process."
    And I click on the Submit button
    Then I should see error message "Name is required."

  Scenario: Submission with Invalid Email
    When I enter valid name "Jane Doe"
    And I enter invalid email "jane.doe@.com"
    And I enter valid subject "Report Issue"
    And I enter valid message "There is a bug on the homepage."
    And I click on the Submit button
    Then I should see error message "Please enter a valid email address."

  Scenario: Submission with Empty Message
    When I enter valid name "Michael Smith"
    And I enter valid email "michael.smith@example.com"
    And I enter valid subject "Feedback"
    And I leave the message field empty
    And I click on the Submit button
    Then I should see error message "Message cannot be empty."
