package com.yourcompany.steps;

import com.yourcompany.pages.ContactPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.apache.logging.log4j.Logger;

public class ContactSteps {

    private ContactPage contactPage = new ContactPage();
    private Logger logger = LoggerUtil.getLogger(ContactSteps.class);
    private String enteredName;
    private String enteredEmail;
    private String enteredMessage;

    @When("user is on contact page")
    public void user_is_on_contact_page() {
        logger.info("Navigating to Contact page.");
        contactPage.goToContactPage();
        Assert.assertTrue(contactPage.isContactHeaderPresent());
        logger.info("Contact page loaded successfully.");
    }

    @And("user enters valid contact details")
    public void user_enters_valid_contact_details() {
        enteredName = ConfigReader.getProperty("contact.name");
        enteredEmail = ConfigReader.getProperty("contact.email");
        enteredMessage = ConfigReader.getProperty("contact.message");
        contactPage.enterName(enteredName);
        contactPage.enterEmail(enteredEmail);
        contactPage.enterMessage(enteredMessage);
        logger.info("Entered valid contact details.");
    }

    @And("user attaches a file for upload")
    public void user_attaches_a_file_for_upload() {
        String filePath = ConfigReader.getProperty("contact.uploadFile");
        contactPage.uploadFile(filePath);
        logger.info("Uploaded file: " + filePath);
    }

    @And("user submits the contact form")
    public void user_submits_the_contact_form() {
        contactPage.clickSubmit();
        logger.info("Submitted the contact form.");
    }

    @Then("success message is displayed")
    public void success_message_is_displayed() {
        Assert.assertTrue(contactPage.isSuccessMessageDisplayed());
        logger.info("Success message confirmed after submission.");
    }

    @And("user enters invalid email {string}")
    public void user_enters_invalid_email(String invalidEmail) {
        enteredName = ConfigReader.getProperty("contact.name");
        enteredMessage = ConfigReader.getProperty("contact.message");
        contactPage.enterName(enteredName);
        contactPage.enterEmail(invalidEmail);
        contactPage.enterMessage(enteredMessage);
        logger.info("Entered invalid email: " + invalidEmail);
    }

    @Then("an error message for email is displayed")
    public void an_error_message_for_email_is_displayed() {
        Assert.assertTrue(contactPage.isEmailErrorDisplayed());
        logger.info("Email error message displayed successfully.");
    }

    @And("user leaves required fields empty")
    public void user_leaves_required_fields_empty() {
        contactPage.clearName();
        contactPage.clearEmail();
        contactPage.clearMessage();
        logger.info("Required fields left empty.");
    }

    @Then("required field error messages are displayed")
    public void required_field_error_messages_are_displayed() {
        Assert.assertTrue(contactPage.isRequiredFieldErrorDisplayed());
        logger.info("Required field error messages displayed.");
    }

    @And("user attempts to upload unsupported file type {string}")
    public void user_attempts_to_upload_unsupported_file_type(String filePath) {
        contactPage.uploadFile(filePath);
        logger.info("Tried to upload an unsupported file type: " + filePath);
    }

    @Then("an error message for file upload is displayed")
    public void an_error_message_for_file_upload_is_displayed() {
        Assert.assertTrue(contactPage.isFileUploadErrorDisplayed());
        logger.info("File upload error message displayed.");
    }

    @And("user performs drag and drop file upload with {string}")
    public void user_performs_drag_and_drop_file_upload_with(String filePath) {
        contactPage.dragAndDropFile(filePath);
        logger.info("Performed drag and drop with file: " + filePath);
    }

    @Then("file is uploaded successfully using drag and drop")
    public void file_is_uploaded_successfully_using_drag_and_drop() {
        Assert.assertTrue(contactPage.isFileUploadSuccessDisplayed());
        logger.info("File uploaded successfully via drag and drop.");
    }
}
