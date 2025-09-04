Feature: Contact Us Page Functionality

  Background:
    Given user launches the browser and navigates to "${baseURL}"
    And user is on the Contact Us page

  Scenario Outline: Positive Contact Us Form Submission
    When user enters valid name "<name>"
    And user enters valid email "<email>"
    And user enters valid subject "<subject>"
    And user enters valid message "<message>"
    And user uploads a file "test_upload.pdf"
    And user clicks on Submit button
    Then user should see a success message "<success_message>"

    Examples:
      | name           | email                | subject         | message         | success_message                     |
      | John Doe       | john@example.com     | Inquiry         | Hello, I need help | Your message has been sent! 
      | Alice Smith    | alice@test.com       | Feedback        | Great website!    | Your message has been sent! |

  Scenario Outline: Negative Contact Us Form Submission with Invalid Email
    When user enters valid name "<name>"
    And user enters invalid email "<invalid_email>"
    And user enters valid subject "<subject>"
    And user enters valid message "<message>"
    And user clicks on Submit button
    Then user should see an error message "<error_message>"

    Examples:
      | name       | invalid_email    | subject   | message      | error_message                  |
      | Ravi Patel | not-an-email     | Support   | Test issue   | Please enter a valid email.    |
      | Lila Gupta | lila@com         | Feedback  | Thanks!      | Please enter a valid email.    |

  Scenario: Negative Contact Us Form Submission with Empty Required Fields
    When user leaves all fields empty
    And user clicks on Submit button
    Then user should see error messages for required fields
