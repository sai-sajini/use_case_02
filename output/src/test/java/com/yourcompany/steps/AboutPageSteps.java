package com.yourcompany.steps;

import com.yourcompany.pages.AboutPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import com.yourcompany.utils.TestDataReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;

public class AboutPageSteps {
    private WebDriver driver = LoggerUtil.getLogger().getDriver();
    private AboutPage aboutPage = new AboutPage(driver);
    private String baseUrl = ConfigReader.getProperty("baseURL");

    @Given("User is on the About page")
    public void user_is_on_about_page() {
        driver.get(baseUrl + "/about");
        Assert.assertTrue(aboutPage.isAt());
        LoggerUtil.getLogger().info("Navigated to About page");
    }

    @When("User checks the company information section")
    public void user_checks_company_info_section() {
        Assert.assertTrue(aboutPage.isCompanyInfoSectionDisplayed());
        LoggerUtil.getLogger().info("Company Info Section is displayed");
    }

    @Then("User should see the company mission statement")
    public void user_should_see_company_mission() {
        String expectedMission = ConfigReader.getProperty("companyMission");
        Assert.assertEquals(expectedMission, aboutPage.getMissionStatement());
        LoggerUtil.getLogger().info("Company mission statement matches expected value");
    }

    @When("User clicks on the team tab")
    public void user_clicks_team_tab() {
        aboutPage.clickTeamTab();
        LoggerUtil.getLogger().info("Clicked on Team Tab");
    }

    @Then("User should see all key team members displayed")
    public void user_should_see_all_team_members_displayed() {
        Assert.assertTrue(aboutPage.areAllTeamMembersDisplayed(TestDataReader.getTeamMembers()));
        LoggerUtil.getLogger().info("All key team members displayed");
    }

    @When("User submits the feedback form with valid details")
    public void user_submits_feedback_form_with_valid_details() {
        String name = ConfigReader.getProperty("validFeedbackName");
        String email = ConfigReader.getProperty("validFeedbackEmail");
        String message = ConfigReader.getProperty("validFeedbackMessage");
        aboutPage.submitFeedbackForm(name, email, message);
        LoggerUtil.getLogger().info("Submitted feedback form with valid details");
    }

    @Then("User should see a successful feedback submitted message")
    public void user_should_see_success_message() {
        Assert.assertTrue(aboutPage.isFeedbackSuccessMessageDisplayed());
        LoggerUtil.getLogger().info("Displayed feedback success message");
    }

    @When("User submits the feedback form with invalid email")
    public void user_submits_feedback_form_with_invalid_email() {
        String name = ConfigReader.getProperty("validFeedbackName");
        String email = ConfigReader.getProperty("invalidFeedbackEmail");
        String message = ConfigReader.getProperty("validFeedbackMessage");
        aboutPage.submitFeedbackForm(name, email, message);
        LoggerUtil.getLogger().info("Submitted feedback form with invalid email");
    }

    @Then("User should see an error message for invalid email")
    public void user_should_see_error_message_for_invalid_email() {
        Assert.assertTrue(aboutPage.isInvalidEmailErrorDisplayed());
        LoggerUtil.getLogger().info("Displayed invalid email error message");
    }
}