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

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "message")
    private WebElement messageTextarea;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadInput;

    @FindBy(id = "dragDropZone")
    private WebElement dragDropZone;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        wait.until(ExpectedConditions.visibilityOf(messageTextarea));
        messageTextarea.clear();
        messageTextarea.sendKeys(message);
    }

    public void uploadFile(String filePath) {
        wait.until(ExpectedConditions.visibilityOf(fileUploadInput));
        fileUploadInput.sendKeys(filePath);
    }

    public void dragAndDropFile(String filePath) {
        // Drag & drop usually requires an external tool as sendKeys does not simulate drag-drop file upload
        // This is a placeholder. In real application, use Robot class or a JS executor for actual drag-drop file upload
        Actions actions = new Actions(driver);
        actions.moveToElement(dragDropZone).click().perform();
        fileUploadInput.sendKeys(filePath);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return successMessage.isDisplayed() && successMessage.getText().trim().length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed() && errorMessage.getText().trim().length() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    public boolean isFormDisplayed() {
        try {
            return nameInput.isDisplayed() && emailInput.isDisplayed() && messageTextarea.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
