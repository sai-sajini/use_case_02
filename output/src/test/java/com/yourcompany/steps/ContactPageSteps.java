package com.yourcompany.steps;

import com.yourcompany.pages.ContactPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import com.yourcompany.utils.TestDataReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class ContactPageSteps {
    private WebDriver driver;
    private ContactPage contactPage;
    private String testName;
    private LoggerUtil logger;
    private TestDataReader testDataReader;

    public ContactPageSteps() {
        driver = hooks.Hooks.getDriver(); // Assuming Hooks exposes static WebDriver
        contactPage = new ContactPage(driver);
        logger = LoggerUtil.getInstance();
        testDataReader = new TestDataReader("src/test/resources/testdata/contact_testdata.xlsx");
        testName = "ContactPageScenario";
    }

    @When("^user navigates to the contact page$")
    public void userNavigatesToContactPage() {
        driver.get(ConfigReader.getProperty("baseURL") + "/contact");
        logger.info(testName + ": Navigated to contact page");
        Assert.assertTrue(contactPage.isContactFormPresent());
    }

    @And("^user enters valid contact details from config$")
    public void userEntersValidContactDetailsFromConfig() {
        String name = ConfigReader.getProperty("contactName");
        String email = ConfigReader.getProperty("contactEmail");
        String message = ConfigReader.getProperty("contactMessage");
        contactPage.enterName(name);
        contactPage.enterEmail(email);
        contactPage.enterMessage(message);
        logger.info(testName + ": Entered valid contact details from config");
    }

    @And("^user enters contact details from excel row (\d+)$")
    public void userEntersContactDetailsFromExcelRow(int row) {
        String name = testDataReader.getCellData("Contact", row, 0);
        String email = testDataReader.getCellData("Contact", row, 1);
        String message = testDataReader.getCellData("Contact", row, 2);
        contactPage.enterName(name);
        contactPage.enterEmail(email);
        contactPage.enterMessage(message);
        logger.info(testName + ": Entered contact details from Excel row " + row);
    }

    @And("^user clicks submit button on contact page$")
    public void userClicksSubmitButton() {
        contactPage.clickSubmit();
        logger.info(testName + ": Clicked submit button");
    }

    @Then("^user should see contact success message$")
    public void userShouldSeeContactSuccessMessage() {
        Assert.assertTrue(contactPage.isSuccessMessageDisplayed());
        logger.info(testName + ": Contact success message displayed");
    }

    @Then("^user should see error message for missing fields$")
    public void userShouldSeeErrorMessageForMissingFields() {
        Assert.assertTrue(contactPage.isErrorMessageDisplayed());
        logger.info(testName + ": Error message for missing fields displayed");
    }

    @And("^user uploads a file on contact page$")
    public void userUploadsAFileOnContactPage() {
        String filePath = ConfigReader.getProperty("contactUploadPath");
        contactPage.uploadFile(filePath);
        logger.info(testName + ": File uploaded on contact page");
        Assert.assertTrue(contactPage.isFileAttached());
    }

    @And("^user performs drag and drop in contact section$")
    public void userPerformsDragAndDropInContactSection() {
        contactPage.performDragAndDrop();
        logger.info(testName + ": Drag and drop action performed in contact section");
        Assert.assertTrue(contactPage.isDragAndDropSuccessful());
    }

    @And("^user clears the contact form$")
    public void userClearsTheContactForm() {
        contactPage.clearForm();
        logger.info(testName + ": Contact form cleared");
        Assert.assertTrue(contactPage.isFormCleared());
    }
}
