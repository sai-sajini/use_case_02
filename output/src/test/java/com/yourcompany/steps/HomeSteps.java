package com.yourcompany.steps;

import com.yourcompany.pages.HomePage;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.apache.logging.log4j.Logger;

public class HomeSteps {
    private HomePage homePage;
    private static final Logger logger = LoggerUtil.getLogger(HomeSteps.class);

    public HomeSteps() {
        homePage = new HomePage();
    }

    @Given("^User is on the Home Page$")
    public void user_is_on_the_home_page() {
        String url = ConfigReader.getProperty("baseURL");
        homePage.navigateTo(url);
        logger.info("Navigated to Home Page: " + url);
    }

    @When("^User clicks the Login button$")
    public void user_clicks_the_login_button() {
        homePage.clickLoginButton();
        logger.info("Clicked Login button");
    }

    @When("^User enters username and password$")
    public void user_enters_username_and_password() {
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");
        homePage.enterUsername(username);
        homePage.enterPassword(password);
        logger.info("Entered username and password");
    }

    @When("^User submits the login form$")
    public void user_submits_the_login_form() {
        homePage.submitLogin();
        logger.info("Login form submitted");
    }

    @Then("^User should see the welcome message$")
    public void user_should_see_the_welcome_message() {
        boolean messageVisible = homePage.isWelcomeMessageDisplayed();
        logger.info("Checked for welcome message: " + messageVisible);
        Assert.assertTrue(messageVisible);
    }

    @When("^User performs a search for product '(.+)'$")
    public void user_performs_a_search_for_product(String product) {
        homePage.enterSearchValue(product);
        homePage.clickSearchButton();
        logger.info("User searched for product: " + product);
    }

    @Then("^Search result for '(.+)' should be displayed$")
    public void search_result_should_be_displayed(String product) {
        boolean isDisplayed = homePage.isProductSearchResultDisplayed(product);
        logger.info("Search result for '" + product + "' displayed: " + isDisplayed);
        Assert.assertTrue(isDisplayed);
    }

    @When("^User tries to login with invalid credentials$")
    public void user_tries_to_login_with_invalid_credentials() {
        String invalidUser = ConfigReader.getProperty("invalidUsername");
        String invalidPass = ConfigReader.getProperty("invalidPassword");
        homePage.enterUsername(invalidUser);
        homePage.enterPassword(invalidPass);
        homePage.submitLogin();
        logger.info("Attempted login with invalid credentials");
    }

    @Then("^Error message should be displayed$")
    public void error_message_should_be_displayed() {
        boolean isError = homePage.isLoginErrorDisplayed();
        logger.info("Error message displayed: " + isError);
        Assert.assertTrue(isError);
    }

    @When("^User navigates to About page from Home$")
    public void user_navigates_to_about_page_from_home() {
        homePage.clickAboutLink();
        logger.info("Navigated to About page from Home");
    }

    @Then("^About page is opened$")
    public void about_page_is_opened() {
        boolean isAboutOpened = homePage.isAtAboutPage();
        logger.info("About page opened: " + isAboutOpened);
        Assert.assertTrue(isAboutOpened);
    }
}
