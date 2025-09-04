package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.io.File;

public class ContactPage {
    private WebDriver driver;

    @FindBy(id = "contact-name")
    private WebElement nameInput;
    
    @FindBy(id = "contact-email")
    private WebElement emailInput;

    @FindBy(id = "contact-subject")
    private WebElement subjectInput;

    @FindBy(id = "contact-message")
    private WebElement messageTextArea;

    @FindBy(id = "contact-type")
    private WebElement typeDropdown;

    @FindBy(id = "contact-file-upload")
    private WebElement fileUploadInput;

    @FindBy(id = "contact-submit")
    private WebElement submitButton;

    @FindBy(css = ".contact-success-message")
    private WebElement successMessage;

    @FindBy(css = ".contact-error-message")
    private WebElement errorMessage;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterSubject(String subject) {
        subjectInput.clear();
        subjectInput.sendKeys(subject);
    }

    public void enterMessage(String message) {
        messageTextArea.clear();
        messageTextArea.sendKeys(message);
    }

    public void selectTypeByVisibleText(String type) {
        Select select = new Select(typeDropdown);
        select.selectByVisibleText(type);
    }

    public void uploadFile(String filePath) {
        File file = new File(filePath);
        fileUploadInput.sendKeys(file.getAbsolutePath());
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public String getSuccessMessage() {
        try {
            return successMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // For negative scenario: check if file upload is accepted
    public boolean isFileUploaded() {
        return fileUploadInput.getAttribute("value") != null && !fileUploadInput.getAttribute("value").isEmpty();
    }
}
