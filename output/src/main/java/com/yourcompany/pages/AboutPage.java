package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AboutPage {
    private WebDriver driver;
    private static final int TIMEOUT = 10;

    @FindBy(xpath = "//h1[text()='About Us']")
    private WebElement aboutUsHeader;

    @FindBy(id = "company-history")
    private WebElement companyHistorySection;

    @FindBy(id = "our-team")
    private WebElement ourTeamSection;

    @FindBy(className = "mission-text")
    private WebElement missionStatement;

    @FindBy(css = "button#learn-more")
    private WebElement learnMoreButton;

    @FindBy(id = "popup-close")
    private WebElement popupCloseButton;

    @FindBy(css = "div.popup-overlay")
    private WebElement popupOverlay;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        // Use AJAX locator factory for implicit wait on elements
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public boolean isAboutUsHeaderDisplayed() {
        return aboutUsHeader.isDisplayed();
    }

    public String getMissionStatementText() {
        return missionStatement.getText();
    }

    public boolean isCompanyHistorySectionDisplayed() {
        return companyHistorySection.isDisplayed();
    }

    public boolean isOurTeamSectionDisplayed() {
        return ourTeamSection.isDisplayed();
    }

    public void clickLearnMoreButton() {
        learnMoreButton.click();
    }

    public boolean isPopupOverlayDisplayed() {
        try {
            return popupOverlay.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closePopupIfPresent() {
        if(isPopupOverlayDisplayed()) {
            popupCloseButton.click();
        }
    }

}
