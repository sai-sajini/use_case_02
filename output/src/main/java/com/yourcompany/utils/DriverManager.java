package com.yourcompany.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import java.net.MalformedURLException;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String browser = ConfigReader.getProperty("browser");
    private static String baseURL = ConfigReader.getProperty("baseURL");
    private static String remoteURL = ConfigReader.getProperty("remoteURL");

    public static void initDriver() {
        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(getChromeOptions()));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver(getFirefoxOptions()));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver(getEdgeOptions()));
                    break;
                case "remote-chrome":
                    try {
                        driver.set(new RemoteWebDriver(new URL(remoteURL), getChromeOptions()));
                    } catch (MalformedURLException e) {
                        LoggerUtil.getLogger().error("Malformed remoteURL: " + remoteURL, e);
                        throw new RuntimeException(e);
                    }
                    break;
                case "remote-firefox":
                    try {
                        driver.set(new RemoteWebDriver(new URL(remoteURL), getFirefoxOptions()));
                    } catch (MalformedURLException e) {
                        LoggerUtil.getLogger().error("Malformed remoteURL: " + remoteURL, e);
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(getChromeOptions()));
                    break;
            }
            getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            getDriver().manage().window().maximize();
            getDriver().get(baseURL);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        return options;
    }
}
