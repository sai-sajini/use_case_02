package com.yourcompany.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.yourcompany.pages.HomePage;
import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;

public class HomePageSteps {
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);
    private LoggerUtil log = LoggerUtil.getLogger(HomePageSteps.class);

    @Given("user launches the application")
    public void user_launches_the_application() {
        String url = ConfigReader.getInstance().getProperty("baseURL");
        driver.get(url);
        log.info("Navigated to baseURL: " + url);
    }

    @Then("homepage is displayed")
    public void homepage_is_displayed() {
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home Page is not displayed");
        log.info("Home Page displayed");
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        homePage.clickLoginButton();
        log.info("Clicked login button");
    }

    @And("user enters username and password")
    public void user_enters_username_and_password() {
        String username = ConfigReader.getInstance().getProperty("username");
        String password = ConfigReader.getInstance().getProperty("password");
        homePage.enterUsername(username);
        homePage.enterPassword(password);
        log.info("Entered username and password");
    }

    @And("user submits the login form")
    public void user_submits_the_login_form() {
        homePage.submitLogin();
        log.info("Submitted login form");
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(homePage.isUserLoggedIn(), "User is not logged in");
        log.info("User login successful");
    }

    @When("user clicks on register button")
    public void user_clicks_on_register_button() {
        homePage.clickRegisterButton();
        log.info("Clicked register button");
    }

    @And("user enters registration details")
    public void user_enters_registration_details() {
        String email = ConfigReader.getInstance().getProperty("registerEmail");
        String regUser = ConfigReader.getInstance().getProperty("registerUsername");
        String regPass = ConfigReader.getInstance().getProperty("registerPassword");
        homePage.enterRegistrationDetails(email, regUser, regPass);
        log.info("Entered registration details");
    }

    @And("user submits the registration form")
    public void user_submits_the_registration_form() {
        homePage.submitRegistration();
        log.info("Submitted registration form");
    }

    @Then("user should be registered successfully")
    public void user_should_be_registered_successfully() {
        Assert.assertTrue(homePage.isUserRegistered(), "Registration failed");
        log.info("User registration successful");
    }

    @When("user enters invalid login credentials")
    public void user_enters_invalid_login_credentials() {
        homePage.enterUsername("invalidUser");
        homePage.enterPassword("wrongPassword");
        log.info("Entered invalid username and password");
    }

    @Then("error message for invalid login is displayed")
    public void error_message_for_invalid_login_is_displayed() {
        Assert.assertTrue(homePage.isLoginErrorDisplayed(), "Login error message not displayed");
        log.info("Login error displayed as expected");
    }

    @When("user enters invalid registration details")
    public void user_enters_invalid_registration_details() {
        homePage.enterRegistrationDetails("bademail", "", "123");
        log.info("Entered invalid registration details");
    }

    @Then("error message for registration is displayed")
    public void error_message_for_registration_is_displayed() {
        Assert.assertTrue(homePage.isRegistrationErrorDisplayed(), "Registration error message not displayed");
        log.info("Registration error displayed as expected");
    }

    @When("user interacts with the homepage carousel")
    public void user_interacts_with_the_homepage_carousel() {
        homePage.clickCarouselNext();
        homePage.clickCarouselPrevious();
        log.info("Interacted with homepage carousel");
    }

    @And("user clicks main navigation links")
    public void user_clicks_main_navigation_links() {
        homePage.clickNavLink("Products");
        homePage.clickNavLink("About");
        homePage.clickNavLink("Contact");
        log.info("Clicked mains navigation links");
    }

    @When("user searches for a product from homepage")
    public void user_searches_for_a_product_from_homepage() {
        homePage.searchForProduct("Laptop");
        log.info("Performed search for product: Laptop");
    }

    @Then("search results are displayed on homepage")
    public void search_results_are_displayed_on_homepage() {
        Assert.assertTrue(homePage.areSearchResultsDisplayed(), "Search results not displayed");
        log.info("Search results displayed on homepage");
    }
}
