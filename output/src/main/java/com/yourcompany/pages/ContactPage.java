package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "contact_name")
    private WebElement nameInput;

    @FindBy(id = "contact_email")
    private WebElement emailInput;

    @FindBy(id = "contact_message")
    private WebElement messageInput;

    @FindBy(id = "contact_file")
    private WebElement fileUploadInput;

    @FindBy(id = "contact_submit")
    private WebElement submitButton;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(id = "drag-drop-area")
    private WebElement dragDropArea;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(messageInput));
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    public void uploadFile(String filePath) {
        wait.until(ExpectedConditions.visibilityOf(fileUploadInput));
        fileUploadInput.sendKeys(filePath);
    }

    public void dragAndDropFile(String filePath) {
        // Selenium does not support HTML5 drag and drop natively, so as an example:
        uploadFile(filePath);
    }

    public void clickSubmit() {
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

    public boolean isAt() {
        return wait.until(ExpectedConditions.visibilityOf(submitButton)).isDisplayed();
    }
}
