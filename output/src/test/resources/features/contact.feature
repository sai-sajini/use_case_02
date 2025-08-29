Feature: Contact Page - Form Submission & Validation

  Background:
    Given user navigates to Contact page

  Scenario Outline: Successful contact form submission
    When user enters name "<name>", email "<email>", subject "<subject>", and message "<message>"
    And user uploads file "<file>"
    And user submits the contact form
    Then "Thank you" message should be displayed
    And form fields should be cleared

    Examples:
      | name      | email                 | subject      | message           | file         |
      | Alice Doe | alice.doe@email.com   | Inquiry      | Need more info    | valid.png    |
      | Bob Ray   | bob.ray@test.com      | Feedback     | Great website!    | upload.docx  |

  Scenario Outline: Invalid form submission - missing required field
    When user enters name "<name>", email "<email>", subject "<subject>", and message "<message>"
    And user uploads file "<file>"
    And user submits the contact form
    Then error message "<error_msg>" should be displayed

    Examples:
      | name      | email                 | subject | message                 | file       | error_msg                     |
      |           | john@email.com        | Support | Please help             | test.png   | Name is required              |
      | Ann Lee   |                       | Help    | I need assistance       | img.jpg    | Email is required             |
      | Mark Z    | mark@email.com        |         | How do I reset password | file.pdf   | Subject is required           |
      | Eva B     | eva@email.com         | Update  |                         | valid.png  | Message is required           |

  Scenario: Upload unsupported file type
    When user enters name "Test User", email "test@domain.com", subject "Bug", and message "Bug report"
    And user uploads file "unsupported.exe"
    And user submits the contact form
    Then error message "Unsupported file type" should be displayed

  Scenario: Form validations - invalid email format
    When user enters name "Example", email "invalid_email", subject "Test", and message "Testing invalid email"
    And user submits the contact form
    Then error message "Please enter a valid email" should be displayed

  Scenario: Drag and drop file upload
    When user drags and drops file "dragdrop.pdf" to the upload area
    And user enters name "Elena", email "elena@sample.com", subject "Proposal", and message "Proposal attached"
    And user submits the contact form
    Then "Thank you" message should be displayed
