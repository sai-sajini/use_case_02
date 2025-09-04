package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import com.yourcompany.utils.LoggerUtil;

public class AboutPage {
    private WebDriver driver;

    @FindBy(css = "h1.page-title")
    private WebElement pageTitle;

    @FindBy(id = "about-description")
    private WebElement aboutDescription;

    @FindBy(xpath = "//a[contains(@href,'team')]")
    private WebElement teamLink;

    @FindBy(xpath = "//div[@class='team-member']")
    private WebElement teamSection;

    @FindBy(id = "download-brochure")
    private WebElement downloadBrochureBtn;

    @FindBy(id = "upload-company-profile")
    private WebElement uploadCompanyProfileInput;

    @FindBy(css = "#drag-profile-area")
    private WebElement dragDropProfileArea;

    @FindBy(xpath = "//button[@id='about-submit']")
    private WebElement submitBtn;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        LoggerUtil.info("Getting About Page title");
        return pageTitle.getText();
    }

    public String getDescription() {
        LoggerUtil.info("Getting description from About Page");
        return aboutDescription.getText();
    }

    public void clickTeamLink() {
        LoggerUtil.info("Clicking Team link on About Page");
        teamLink.click();
    }

    public boolean isTeamSectionDisplayed() {
        LoggerUtil.info("Checking if Team section is displayed");
        return teamSection.isDisplayed();
    }

    public void clickDownloadBrochure() {
        LoggerUtil.info("Clicking Download Brochure button");
        downloadBrochureBtn.click();
    }

    public void uploadCompanyProfile(String filePath) {
        LoggerUtil.info("Uploading company profile: " + filePath);
        uploadCompanyProfileInput.sendKeys(filePath);
    }

    public void dragAndDropProfile(String filePath) {
        LoggerUtil.info("Performing drag and drop for company profile: " + filePath);
        Actions actions = new Actions(driver);
        actions.moveToElement(dragDropProfileArea).click().perform();
        uploadCompanyProfile(filePath);
    }

    public void clickSubmit() {
        LoggerUtil.info("Clicking Submit button on About Page");
        submitBtn.click();
    }

    public String getErrorMessage() {
        LoggerUtil.info("Getting error message from About Page");
        return errorMessage.getText();
    }

    public boolean isErrorMessageDisplayed() {
        LoggerUtil.info("Checking if error message is displayed on About Page");
        return errorMessage.isDisplayed();
    }
}
