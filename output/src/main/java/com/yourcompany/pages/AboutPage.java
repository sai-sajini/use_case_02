package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AboutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(text(),'About Us')]")
    private WebElement aboutHeader;

    @FindBy(id = "company-mission")
    private WebElement companyMission;

    @FindBy(id = "company-values")
    private WebElement companyValues;

    @FindBy(css = "button#read-more")
    private WebElement readMoreButton;

    @FindBy(id = "more-description")
    private WebElement moreDescription;

    @FindBy(css = "#team-members")
    private WebElement teamSection;

    @FindBy(css = "#team-members .member")
    private java.util.List<WebElement> teamMembers;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public boolean isAboutHeaderDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(aboutHeader));
        return aboutHeader.isDisplayed();
    }

    public String getCompanyMission() {
        wait.until(ExpectedConditions.visibilityOf(companyMission));
        return companyMission.getText();
    }

    public String getCompanyValues() {
        wait.until(ExpectedConditions.visibilityOf(companyValues));
        return companyValues.getText();
    }

    public void clickReadMore() {
        wait.until(ExpectedConditions.elementToBeClickable(readMoreButton));
        readMoreButton.click();
    }

    public boolean isMoreDescriptionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(moreDescription));
            return moreDescription.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTeamSectionDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(teamSection));
        return teamSection.isDisplayed();
    }

    public int getTeamMembersCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(teamMembers));
        return teamMembers.size();
    }

    public String getTeamMemberName(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(teamMembers));
        if (index < teamMembers.size()) {
            return teamMembers.get(index).getText();
        } else {
            throw new IndexOutOfBoundsException("Team member index out of range");
        }
    }
}
