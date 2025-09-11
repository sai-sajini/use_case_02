package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    // UI Elements
    @FindBy(xpath = "//nav//a[text()='Home']")
    private WebElement homeNavLink;

    @FindBy(xpath = "//nav//a[text()='About']")
    private WebElement aboutNavLink;

    @FindBy(xpath = "//nav//a[text()='Products']")
    private WebElement productsNavLink;

    @FindBy(xpath = "//nav//a[text()='Contact']")
    private WebElement contactNavLink;

    @FindBy(css = "h1.page-title")
    private WebElement pageTitle;

    @FindBy(css = "div.alert.alert-success")
    private WebElement successAlert;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement errorAlert;

    @FindBy(xpath = "//button[text()='Get Started']")
    private WebElement getStartedBtn;

    @FindBy(id = "newsletter-email")
    private WebElement newsletterEmailInput;

    @FindBy(id = "newsletter-submit")
    private WebElement newsletterSubmitBtn;

    @FindBy(css = "div.newsletter-success-message")
    private WebElement newsletterSuccessMsg;

    @FindBy(css = "div.newsletter-error-message")
    private WebElement newsletterErrorMsg;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getPageHeading() {
        return pageTitle.getText();
    }

    public void clickHomeNav() {
        homeNavLink.click();
    }

    public void clickAboutNav() {
        aboutNavLink.click();
    }

    public void clickProductsNav() {
        productsNavLink.click();
    }

    public void clickContactNav() {
        contactNavLink.click();
    }

    public void clickGetStartedButton() {
        getStartedBtn.click();
    }

    public void enterNewsletterEmail(String email) {
        newsletterEmailInput.clear();
        newsletterEmailInput.sendKeys(email);
    }

    public void submitNewsletter() {
        newsletterSubmitBtn.click();
    }

    public String getNewsletterSuccessMsg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(newsletterSuccessMsg));
        return newsletterSuccessMsg.getText();
    }

    public String getNewsletterErrorMsg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(newsletterErrorMsg));
        return newsletterErrorMsg.getText();
    }

    public boolean isSuccessAlertVisible() {
        try {
            return successAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorAlertVisible() {
        try {
            return errorAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Handle any potential modal/popups blocking UI
    @FindBy(css = "div.modal-dialog button.close")
    private WebElement modalCloseButton;

    public void closeModalIfPresent() {
        try {
            if (modalCloseButton.isDisplayed()) {
                modalCloseButton.click();
            }
        } catch (Exception ex) {
            // do nothing
        }
    }
}
