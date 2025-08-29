package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "loginBtn")
    private WebElement loginButton;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "#rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = "#welcomeBanner")
    private WebElement welcomeBanner;

    @FindBy(xpath = "//div[@id='dragSource']")
    private WebElement dragSource;

    @FindBy(xpath = "//div[@id='dropTarget']")
    private WebElement dropTarget;

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(id = "loginError")
    private WebElement loginErrorMsg;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isWelcomeBannerDisplayed() {
        return welcomeBanner.isDisplayed();
    }

    public void enterUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void setRememberMe(boolean shouldCheck) {
        if (rememberMeCheckbox.isSelected() != shouldCheck) {
            rememberMeCheckbox.click();
        }
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isLogoutButtonDisplayed() {
        return logoutButton.isDisplayed();
    }

    public void clickLogout() {
        if (logoutButton.isDisplayed()) {
            logoutButton.click();
        }
    }

    public boolean isLoginErrorDisplayed() {
        try {
            return loginErrorMsg.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

    public void dragAndDropWelcomeBanner() {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(dragSource, dropTarget).perform();
    }
}
