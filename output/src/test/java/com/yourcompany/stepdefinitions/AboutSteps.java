package com.yourcompany.stepdefinitions;

import com.yourcompany.pages.AboutPage;
import com.yourcompany.utils.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class AboutSteps {

    private AboutPage aboutPage = new AboutPage(DriverFactory.getDriver());

    @Given("the user is on the About page")
    public void the_user_is_on_the_About_page() {
        aboutPage.goToAboutPage();
        Assert.assertTrue("About page did not load!", aboutPage.isPageLoaded());
    }

    @When("the user views company information")
    public void the_user_views_company_information() {
        Assert.assertTrue("Company info is not displayed!", aboutPage.isCompanyInfoDisplayed());
    }

    @Then("the company history is displayed")
    public void the_company_history_is_displayed() {
        Assert.assertTrue("Company history is missing!", aboutPage.isCompanyHistoryDisplayed());
    }

    @When("the user clicks the Team tab")
    public void the_user_clicks_the_team_tab() {
        aboutPage.clickOnTeamTab();
    }

    @Then("the team member list is shown")
    public void the_team_member_list_is_shown() {
        Assert.assertTrue("Team member list not shown!", aboutPage.isTeamSectionDisplayed());
    }

    @When("the user clicks the Careers tab")
    public void the_user_clicks_the_careers_tab() {
        aboutPage.clickOnCareersTab();
    }

    @Then("the careers section is shown")
    public void the_careers_section_is_shown() {
        Assert.assertTrue("Careers section not displayed!", aboutPage.isCareersSectionDisplayed());
    }

    @When("the user opens the FAQ section")
    public void the_user_opens_the_faq_section() {
        aboutPage.expandFAQSection();
    }

    @Then("the FAQs are visible")
    public void the_faqs_are_visible() {
        Assert.assertTrue("FAQs are not visible!", aboutPage.areFAQsVisible());
    }

    @When("the user enters invalid input in the About page search")
    public void the_user_enters_invalid_input_in_the_about_page_search() {
        aboutPage.enterSearchText("!!!###");
        aboutPage.submitSearch();
    }

    @Then("an error message is shown for invalid search")
    public void an_error_message_is_shown_for_invalid_search() {
        Assert.assertEquals("Invalid search error not shown as expected!", "Please enter a valid search term.", aboutPage.getSearchErrorMessage());
    }

    @When("the user enters a valid input in the About page search")
    public void the_user_enters_a_valid_input_in_the_about_page_search() {
        aboutPage.enterSearchText("Company Vision");
        aboutPage.submitSearch();
    }

    @Then("search results are displayed")
    public void search_results_are_displayed() {
        Assert.assertTrue("Search results not displayed!", aboutPage.areSearchResultsVisible());
    }
}
