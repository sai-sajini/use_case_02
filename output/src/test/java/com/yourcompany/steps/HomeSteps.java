package com.yourcompany.steps;

import com.yourcompany.pages.HomePage;
import com.yourcompany.pages.NavigationBar;
import com.yourcompany.utils.ConfigReader;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import com.yourcompany.utils.DriverManager;
import org.testng.Assert;

public class HomeSteps {
    private WebDriver driver = DriverManager.getDriver();
    private HomePage homePage = new HomePage(driver);
    private NavigationBar navigationBar = new NavigationBar(driver);

    @Given("User is on the Home page")
    public void user_is_on_the_home_page() {
        LoggerUtil.info("Navigating to Home Page");
        driver.get(ConfigReader.getProperty("baseURL"));
        Assert.assertTrue(homePage.isLoaded(), "Home Page did not load properly");
    }

    @Then("Home page title should be displayed as {string}")
    public void home_page_title_should_be_displayed_as(String expectedTitle) {
        String title = homePage.getTitle();
        LoggerUtil.info("Actual title: " + title);
        Assert.assertEquals(title, expectedTitle, "Home page title mismatch");
    }

    @Then("Welcome message should be {string}")
    public void welcome_message_should_be(String expectedMsg) {
        String actualMsg = homePage.getWelcomeMessage();
        LoggerUtil.info("Welcome message: " + actualMsg);
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message mismatch");
    }

    @When("User clicks on the login button")
    public void user_clicks_on_the_login_button() {
        LoggerUtil.info("Clicking login button");
        homePage.clickLoginButton();
    }

    @Then("User should be redirected to the login page")
    public void user_should_be_redirected_to_the_login_page() {
        boolean redirected = homePage.isRedirectedToLogin();
        LoggerUtil.info("User redirected to login page: " + redirected);
        Assert.assertTrue(redirected, "User was not redirected to login page");
    }

    @When("User navigates to About page using the navigation bar")
    public void user_navigates_to_about_page_using_the_navigation_bar() {
        LoggerUtil.info("Navigating to About page via navigation bar");
        navigationBar.clickAbout();
    }

    @Then("About page should be displayed")
    public void about_page_should_be_displayed() {
        LoggerUtil.info("Verifying About page is displayed");
        Assert.assertTrue(navigationBar.isAboutPageActive(), "About page is not active");
    }

    @Then("Major UI components should be visible on the Home page")
    public void major_ui_components_should_be_visible_on_home_page() {
        Assert.assertTrue(homePage.isBannerVisible(), "Home banner not visible");
        Assert.assertTrue(homePage.isProductSectionVisible(), "Product section not visible");
        Assert.assertTrue(homePage.isContactLinkVisible(), "Contact link not visible");
        LoggerUtil.info("All major UI components are visible");
    }

    @When("User performs drag and drop of the banner image")
    public void user_performs_drag_and_drop_of_banner_image() {
        LoggerUtil.info("Performing drag and drop on banner image");
        homePage.dragAndDropBannerImage();
    }

    @Then("Banner image should be dropped at the specified location")
    public void banner_image_should_be_dropped_at_specified_location() {
        Assert.assertTrue(homePage.isBannerImageDropped(), "Banner image was not dropped at correct location");
        LoggerUtil.info("Banner image dropped successfully");
    }
}
