package com.yourcompany.steps;

import com.yourcompany.pages.NavigationBar;
import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;

public class NavigationSteps {
    private WebDriver driver = DriverManager.getDriver();
    private NavigationBar navigationBar = new NavigationBar(driver);

    @Given("the user is on the home page")
    public void user_on_home_page() {
        String url = DriverManager.getBaseUrl();
        driver.get(url);
        LoggerUtil.getLogger().info("Navigated to home page: " + url);
    }

    @When("the user navigates to the About page using the navigation bar")
    public void user_navigates_to_about_page() {
        navigationBar.clickAbout();
        LoggerUtil.getLogger().info("Clicked About link in navigation bar.");
    }

    @When("the user navigates to the Products page using the navigation bar")
    public void user_navigates_to_products_page() {
        navigationBar.clickProducts();
        LoggerUtil.getLogger().info("Clicked Products link in navigation bar.");
    }

    @When("the user navigates to the Contact page using the navigation bar")
    public void user_navigates_to_contact_page() {
        navigationBar.clickContact();
        LoggerUtil.getLogger().info("Clicked Contact link in navigation bar.");
    }

    @Then("the {string} page should be displayed")
    public void page_should_be_displayed(String pageName) {
        String actualTitle = driver.getTitle().toLowerCase();
        Assert.assertTrue("Page title does not contain " + pageName,
            actualTitle.contains(pageName.toLowerCase()));
        LoggerUtil.getLogger().info(pageName + " page is displayed. Title: " + actualTitle);
    }

    @When("the user attempts to access a non-existent page using the navigation bar")
    public void user_accesses_non_existent_page() {
        navigationBar.clickNonExistent();
        LoggerUtil.getLogger().info("Attempted to click a non-existent page link in navigation bar.");
    }

    @Then("an error page should be displayed with message {string}")
    public void error_page_should_be_displayed(String errorMessage) {
        String bodyText = navigationBar.getErrorMessage();
        Assert.assertTrue(bodyText.contains(errorMessage));
        LoggerUtil.getLogger().info("Error page displayed with message: " + errorMessage);
    }
}
