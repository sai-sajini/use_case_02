package com.yourcompany.stepdefinitions;

import com.yourcompany.pages.HomePage;
import com.yourcompany.utils.DriverFactory;
import com.yourcompany.utils.TestUtil;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class HomeSteps {
    private WebDriver driver = DriverFactory.getDriver();
    private HomePage homePage = new HomePage(driver);

    @Given("user is on the Home page")
    public void user_is_on_the_home_page() {
        driver.get(TestUtil.getAppURL());
        TestUtil.waitForPageLoaded(driver);
        Assert.assertTrue(homePage.isLogoDisplayed());
    }

    @When("user navigates to About page from Home page")
    public void user_navigates_to_about_page_from_home_page() {
        homePage.clickAboutLink();
    }

    @Then("About page title should be {string}")
    public void about_page_title_should_be(String expectedTitle) {
        TestUtil.waitForPageLoaded(driver);
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @When("user searches for {string} in the search bar")
    public void user_searches_for_in_the_search_bar(String query) {
        homePage.enterSearchText(query);
        homePage.submitSearch();
    }

    @Then("search results should show items relevant to {string}")
    public void search_results_should_show_items_relevant_to(String query) {
        Assert.assertTrue(homePage.areSearchResultsRelevant(query));
    }

    @When("user enters invalid search query {string}")
    public void user_enters_invalid_search_query(String query) {
        homePage.enterSearchText(query);
        homePage.submitSearch();
    }

    @Then("an error message {string} is displayed on Home page")
    public void an_error_message_is_displayed_on_home_page(String expectedMessage) {
        Assert.assertEquals(expectedMessage, homePage.getSearchErrorMessage());
    }

    @When("user clicks on the Products link from Home page")
    public void user_clicks_on_the_products_link_from_home_page() {
        homePage.clickProductsLink();
    }

    @Then("Products page is opened with title {string}")
    public void products_page_is_opened_with_title(String expectedTitle) {
        TestUtil.waitForPageLoaded(driver);
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @When("user clicks on Contact link in navigation menu")
    public void user_clicks_on_contact_link_in_navigation_menu() {
        homePage.clickContactLink();
    }

    @Then("Contact page should be displayed with title {string}")
    public void contact_page_should_be_displayed_with_title(String expectedTitle) {
        TestUtil.waitForPageLoaded(driver);
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @When("user encounters an overlay popup")
    public void user_encounters_an_overlay_popup() {
        homePage.handleOverlayPopupIfPresent();
    }

    @Then("overlay popup is closed and user can interact with Home page")
    public void overlay_popup_is_closed_and_user_can_interact_with_home_page() {
        Assert.assertTrue(homePage.isHomePageReady());
    }
}
