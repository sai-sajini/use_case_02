package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.yourcompany.utils.LoggerUtil;

public class AboutPage {
    WebDriver driver;

    @FindBy(xpath = "//h1[contains(text(),'About Us')]")
    private WebElement headingAboutUs;

    @FindBy(id = "company-history")
    private WebElement companyHistorySection;

    @FindBy(id = "team-section")
    private WebElement teamSection;

    @FindBy(css = "#team-section .member")
    private WebElement firstTeamMember;

    @FindBy(xpath = "//button[@id='see-more-history']")
    private WebElement btnSeeMoreHistory;

    @FindBy(css = "#history-details")
    private WebElement historyDetails;

    @FindBy(xpath = "//a[text()='Contact Us']")
    private WebElement lnkContactUs;

    @FindBy(css = "div.testimonial")
    private WebElement testimonialSection;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        LoggerUtil.info("Getting About Us page heading text.");
        return headingAboutUs.getText();
    }

    public boolean isCompanyHistoryDisplayed() {
        LoggerUtil.info("Checking if Company History section is displayed.");
        return companyHistorySection.isDisplayed();
    }

    public boolean isTeamSectionDisplayed() {
        LoggerUtil.info("Checking if Team section is displayed.");
        return teamSection.isDisplayed();
    }

    public String getFirstTeamMemberName() {
        LoggerUtil.info("Getting first team member name.");
        return firstTeamMember.getText();
    }

    public void clickSeeMoreHistory() {
        LoggerUtil.info("Clicking See More History button.");
        btnSeeMoreHistory.click();
    }

    public boolean isHistoryDetailsVisible() {
        LoggerUtil.info("Checking if History Details are visible.");
        return historyDetails.isDisplayed();
    }

    public void clickContactUsLink() {
        LoggerUtil.info("Clicking Contact Us link from About page.");
        lnkContactUs.click();
    }

    public boolean isTestimonialSectionDisplayed() {
        LoggerUtil.info("Checking if Testimonial section is displayed.");
        return testimonialSection.isDisplayed();
    }
}
