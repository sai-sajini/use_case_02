package com.yourcompany.steps;

import com.yourcompany.pages.AboutPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;

public class AboutSteps {
    private WebDriver driver;
    private AboutPage aboutPage;
    private LoggerUtil logger = LoggerUtil.getInstance();

    public AboutSteps() {
        this.driver = LoggerUtil.getDriver();
        this.aboutPage = new AboutPage(driver);
    }

    @Given("User navigates to the About page")
    public void user_navigates_to_about_page() {
        String aboutUrl = ConfigReader.getProperty("aboutURL");
        driver.get(aboutUrl);
        logger.info("Navigated to About page: " + aboutUrl);
    }

    @Then("The About header should be displayed")
    public void about_header_should_be_displayed() {
        Assert.assertTrue("About header is not displayed", aboutPage.isAboutHeaderDisplayed());
        logger.info("Verified About header is displayed");
    }

    @Then("The company description should be correct")
    public void company_description_should_be_correct() {
        String expectedDescription = ConfigReader.getProperty("aboutDescription");
        Assert.assertEquals("Company description mismatch", expectedDescription, aboutPage.getCompanyDescription());
        logger.info("Company description matched expected value");
    }

    @When("User clicks on the Team section")
    public void user_clicks_on_team_section() {
        aboutPage.clickTeamSection();
        logger.info("Clicked on Team section");
    }

    @Then("The Team section should be visible")
    public void team_section_should_be_visible() {
        Assert.assertTrue("Team section is not visible", aboutPage.isTeamSectionDisplayed());
        logger.info("Team section is visible");
    }

    @When("User attempts to access About with invalid URL")
    public void user_accesses_about_with_invalid_url() {
        String invalidUrl = ConfigReader.getProperty("invalidAboutURL");
        driver.get(invalidUrl);
        logger.info("Navigated to invalid About URL: " + invalidUrl);
    }

    @Then("An error message should be shown on About page")
    public void error_message_should_be_shown_on_about_page() {
        Assert.assertTrue("Error message not displayed for invalid About page", aboutPage.isErrorMessageDisplayed());
        logger.info("Error message is shown on About page for negative scenario");
    }

}
