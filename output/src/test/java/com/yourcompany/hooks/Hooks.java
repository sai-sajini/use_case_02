package com.yourcompany.hooks;

import com.yourcompany.utils.DriverManager;
import com.yourcompany.utils.ExtentReportUtil;
import com.yourcompany.utils.LoggerUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private static Logger logger = LoggerUtil.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("================ Test Started: " + scenario.getName() + " =================");
        DriverManager.initDriver();
        ExtentReportUtil.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed()) {
            logger.error("Test Failed: " + scenario.getName());
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
                ExtentReportUtil.fail(scenario.getName(), "Scenario failed. Screenshot attached.");
            } else {
                ExtentReportUtil.fail(scenario.getName(), "Scenario failed but WebDriver is null.");
            }
        } else {
            logger.info("Test Passed: " + scenario.getName());
            ExtentReportUtil.pass(scenario.getName(), "Scenario passed.");
        }
        ExtentReportUtil.flushReports();
        DriverManager.quitDriver();
        logger.info("================ Test Finished: " + scenario.getName() + " =================");
    }
}
