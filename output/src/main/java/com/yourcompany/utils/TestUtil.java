package com.yourcompany.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class TestUtil {
    public static final int IMPLICIT_WAIT = 10; // seconds
    public static final int EXPLICIT_WAIT = 15; // seconds

    public static void waitForElementVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() > 0;
    }

    public static void handlePopupIfPresent(WebDriver driver, By popupLocator, By closeButtonLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
            WebElement closeBtn = driver.findElement(closeButtonLocator);
            closeBtn.click();
        } catch (TimeoutException e) {
            // Popup not present, do nothing
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
