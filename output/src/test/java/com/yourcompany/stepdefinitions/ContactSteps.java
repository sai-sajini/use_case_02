package com.yourcompany.stepdefinitions;

import com.yourcompany.pages.ContactPage;
import com.yourcompany.utils.DriverFactory;
import com.yourcompany.utils.TestUtil;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class ContactSteps {

    private WebDriver driver = DriverFactory.getDriver();
    private ContactPage contactPage = new ContactPage(driver);

    @Given("User is on the Contact page")
    public void user_is_on_the_contact_page() {
        contactPage.goToContactPage();
        Assert.assertTrue(contactPage.isContactHeaderDisplayed());
    }

    @When("User enters name as {string}, email as {string}, subject as {string}, and message as {string}")
    public void user_enters_all_fields(String name, String email, String subject, String message) {
        contactPage.enterName(name);
        contactPage.enterEmail(email);
        contactPage.enterSubject(subject);
        contactPage.enterMessage(message);
    }

    @When("User clicks the Submit button")
    public void user_clicks_the_submit_button() {
        contactPage.clickSubmit();
    }

    @Then("Contact success message {string} should be displayed")
    public void contact_success_message_should_be_displayed(String successMsg) {
        Assert.assertEquals(successMsg, contactPage.getSuccessMessage());
    }

    @Then("Contact error message {string} should be displayed")
    public void contact_error_message_should_be_displayed(String errMsg) {
        Assert.assertEquals(errMsg, contactPage.getErrorMessage());
    }

    @Given("User is on the Contact form and no fields are filled")
    public void user_is_on_the_contact_form_and_no_fields_are_filled() {
        contactPage.goToContactPage();
        contactPage.clearAllFields();
    }

    @When("User submits the form with missing fields")
    public void user_submits_form_with_missing_fields() {
        contactPage.clickSubmit();
    }

    @Then("Contact form error messages should be displayed for all required fields")
    public void contact_form_error_messages_for_required_fields() {
        Assert.assertTrue(contactPage.isNameErrorDisplayed());
        Assert.assertTrue(contactPage.isEmailErrorDisplayed());
        Assert.assertTrue(contactPage.isMessageErrorDisplayed());
    }

    @When("User enters invalid email {string}")
    public void user_enters_invalid_email(String invalidEmail) {
        contactPage.enterEmail(invalidEmail);
    }

    @Then("Email validation error message {string} should be displayed")
    public void email_validation_error_message_should_be_displayed(String emailErrorMsg) {
        Assert.assertEquals(emailErrorMsg, contactPage.getEmailErrorMessage());
    }

    @When("User fills in all fields except message")
    public void user_fills_all_fields_except_message() {
        contactPage.enterName(TestUtil.getRandomName());
        contactPage.enterEmail(TestUtil.getRandomEmail());
        contactPage.enterSubject(TestUtil.getRandomSubject());
        contactPage.clearMessage();
    }

    @Then("Message required error {string} should be displayed")
    public void message_required_error_should_be_displayed(String msgRequiredErr) {
        Assert.assertEquals(msgRequiredErr, contactPage.getMessageErrorMessage());
    }
}
