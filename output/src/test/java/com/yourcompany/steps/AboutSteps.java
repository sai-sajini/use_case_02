package com.yourcompany.steps;

import com.yourcompany.pages.AboutPage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.apache.logging.log4j.Logger;

public class AboutSteps {
    private AboutPage aboutPage = new AboutPage();
    private static final Logger logger = LoggerUtil.getLogger(AboutSteps.class);

    @Given("User is on the About page")
    public void user_is_on_about_page() {
        String aboutUrl = ConfigReader.getProperty("aboutPageUrl");
        aboutPage.navigateToAboutPage(aboutUrl);
        logger.info("Navigated to About page: " + aboutUrl);
    }

    @When("User verifies the main header is displayed")
    public void user_verifies_main_header_displayed() {
        Assert.assertTrue("Main header is not displayed", aboutPage.isHeaderDisplayed());
        logger.info("Verified main header is displayed on About page.");
    }

    @Then("Company information should be visible")
    public void company_information_should_be_visible() {
        Assert.assertTrue("Company info section not visible", aboutPage.isCompanyInfoVisible());
        logger.info("Verified company information is visible.");
    }

    @When("User clicks on the team section")
    public void user_clicks_team_section() {
        aboutPage.clickTeamSection();
        logger.info("Clicked on Team section.");
    }

    @Then("Team member list should be displayed")
    public void team_member_list_displayed() {
        Assert.assertTrue("Team member list is not displayed", aboutPage.isTeamListDisplayed());
        logger.info("Team member list is displayed.");
    }

    @When("User enters {string} in the search box")
    public void user_enters_text_in_search_box(String name) {
        aboutPage.enterSearchText(name);
        logger.info("Entered text in search box: " + name);
    }

    @Then("Only {string} should be shown in the team list")
    public void only_name_should_be_shown_in_team_list(String name) {
        Assert.assertTrue("Expected team member not shown", aboutPage.isOnlyThisTeamMemberVisible(name));
        logger.info("Verified only " + name + " is shown in team list.");
    }

    @When("User submits an invalid search {string}")
    public void user_submits_invalid_search(String name) {
        aboutPage.enterSearchText(name);
        logger.info("Entered invalid search: " + name);
    }

    @Then("Error message \"No team member found.\" should be displayed")
    public void error_message_should_be_displayed() {
        Assert.assertTrue("Error message not displayed", aboutPage.isNoTeamMemberFoundMessageDisplayed());
        logger.info("Verified 'No team member found.' error message displayed.");
    }
}
