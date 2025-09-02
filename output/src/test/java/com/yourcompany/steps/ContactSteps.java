package com.yourcompany.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import com.yourcompany.pages.ContactPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.LoggerUtil;
import com.yourcompany.utils.ExtentReportUtil;
import org.junit.Assert;

public class ContactSteps {
    private ContactPage contactPage = new ContactPage(DriverManager.getDriver());
    private String resultMessage;
    private String filePath = ConfigReader.getProperty("testdata.filepath");

    @Given("User is on the Contact page")
    public void user_is_on_contact_page() {
        DriverManager.getDriver().get(ConfigReader.getProperty("baseURL") + "/contact");
        LoggerUtil.info("Navigated to Contact page");
        ExtentReportUtil.createStep("Navigated to Contact page");
    }

    @When("User enters valid contact details")
    public void user_enters_valid_contact_details() {
        contactPage.enterName(ConfigReader.getProperty("contact.name"));
        contactPage.enterEmail(ConfigReader.getProperty("contact.email"));
        contactPage.enterMessage(ConfigReader.getProperty("contact.message"));
        LoggerUtil.info("Entered valid contact details");
        ExtentReportUtil.createStep("Entered valid contact details");
    }

    @When("User enters invalid contact details")
    public void user_enters_invalid_contact_details() {
        contactPage.enterName("");
        contactPage.enterEmail("invalidemail");
        contactPage.enterMessage("");
        LoggerUtil.info("Entered invalid contact details");
        ExtentReportUtil.createStep("Entered invalid contact details");
    }

    @And("User uploads a file")
    public void user_uploads_a_file() {
        contactPage.uploadFile(filePath);
        LoggerUtil.info("Uploaded file: " + filePath);
        ExtentReportUtil.createStep("Uploaded file: " + filePath);
    }

    @And("User submits the contact form")
    public void user_submits_the_contact_form() {
        contactPage.clickSubmit();
        LoggerUtil.info("Submitted the contact form");
        ExtentReportUtil.createStep("Submitted the contact form");
    }

    @And("User performs drag and drop to attach a document")
    public void user_performs_drag_and_drop_document() {
        contactPage.dragAndDropDocument(filePath);
        LoggerUtil.info("Performed drag & drop to attach document");
        ExtentReportUtil.createStep("Performed drag & drop to attach document");
    }

    @Then("A success message is displayed")
    public void a_success_message_is_displayed() {
        resultMessage = contactPage.getResultMessage();
        LoggerUtil.info("Result message: " + resultMessage);
        ExtentReportUtil.createStep("Success message displayed: " + resultMessage);
        Assert.assertTrue(resultMessage.toLowerCase().contains("thank you") ||
                          resultMessage.toLowerCase().contains("success"));
    }

    @Then("An error message is displayed")
    public void an_error_message_is_displayed() {
        resultMessage = contactPage.getResultMessage();
        LoggerUtil.info("Result message: " + resultMessage);
        ExtentReportUtil.createStep("Error message displayed: " + resultMessage);
        Assert.assertTrue(resultMessage.toLowerCase().contains("error") ||
                          resultMessage.toLowerCase().contains("invalid"));
    }

    @And("User clears the form")
    public void user_clears_the_form() {
        contactPage.clearForm();
        LoggerUtil.info("Cleared the contact form");
        ExtentReportUtil.createStep("Cleared the contact form");
    }
}
