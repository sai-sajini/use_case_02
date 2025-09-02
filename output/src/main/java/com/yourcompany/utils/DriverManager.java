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
import java.time.Duration;
import java.net.URL;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initializeDriver() {
        if (driver.get() == null) {
            String browser = ConfigReader.getProperty("browser").toLowerCase();
            String remote = ConfigReader.getProperty("remote");
            WebDriver wd = null;
            try {
                if (remote != null && remote.equalsIgnoreCase("true")) {
                    String remoteUrl = ConfigReader.getProperty("remoteUrl");
                    switch (browser) {
                        case "chrome":
                            ChromeOptions chromeOptions = new ChromeOptions();
                            wd = new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
                            break;
                        case "firefox":
                            FirefoxOptions firefoxOptions = new FirefoxOptions();
                            wd = new RemoteWebDriver(new URL(remoteUrl), firefoxOptions);
                            break;
                        case "edge":
                            EdgeOptions edgeOptions = new EdgeOptions();
                            wd = new RemoteWebDriver(new URL(remoteUrl), edgeOptions);
                            break;
                        default:
                            throw new RuntimeException("Unsupported remote browser: " + browser);
                    }
                } else {
                    switch (browser) {
                        case "chrome":
                            WebDriverManager.chromedriver().setup();
                            wd = new ChromeDriver();
                            break;
                        case "firefox":
                            WebDriverManager.firefoxdriver().setup();
                            wd = new FirefoxDriver();
                            break;
                        case "edge":
                            WebDriverManager.edgedriver().setup();
                            wd = new EdgeDriver();
                            break;
                        default:
                            throw new RuntimeException("Unsupported browser: " + browser);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error initializing the driver: " + e.getMessage());
            }

            wd.manage().window().maximize();
            wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(wd);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
