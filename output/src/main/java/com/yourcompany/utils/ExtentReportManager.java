package com.yourcompany.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter sparkReporter;
    private static String reportPath;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";
            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("UI Automation Report");
            sparkReporter.config().setReportName("Selenium Cucumber Test Report");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", ConfigReader.getProperty("environment"));
            extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
            extent.setSystemInfo("BaseURL", ConfigReader.getProperty("baseURL"));
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        test = getExtentReports().createTest(testName, description);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static String getReportPath() {
        return reportPath;
    }
}
