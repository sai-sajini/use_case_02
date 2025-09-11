package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "contact-name")
    private WebElement nameField;

    @FindBy(id = "contact-email")
    private WebElement emailField;

    @FindBy(id = "contact-message")
    private WebElement messageField;

    @FindBy(id = "contact-submit")
    private WebElement submitButton;

    @FindBy(css = ".contact-success")
    private WebElement successMessage;

    @FindBy(css = ".contact-error")
    private WebElement errorMessage;

    @FindBy(css = ".overlay,.modal,.popup-close")
    private WebElement popupCloseBtn;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameField));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(messageField));
        messageField.clear();
        messageField.sendKeys(message);
    }

    public void clickSubmit() {
        waitForPopupAndCloseIfPresent();
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    // Handles overlays or popups that may block interactions
    public void waitForPopupAndCloseIfPresent() {
        try {
            if (popupCloseBtn.isDisplayed() && popupCloseBtn.isEnabled()) {
                popupCloseBtn.click();
            }
        } catch (Exception ignored) {}
    }

    public boolean isOnContactPage() {
        return driver.getCurrentUrl().toLowerCase().contains("contact");
    }
}
