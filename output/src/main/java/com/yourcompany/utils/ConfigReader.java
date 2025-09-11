package com.yourcompany.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private static ConfigReader configReader;
    private final String propertyFilePath = "src/main/java/com/yourcompany/config/config.properties";

    private ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propertyFilePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not read config.properties file. " + e.getMessage());
        }
    }

    public static ConfigReader getInstance() {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getBrowser() {
        String browser = properties.getProperty("browser");
        if (browser != null)
            return browser;
        else
            throw new RuntimeException("browser not specified in the config.properties file.");
    }

    public String getBaseUrl() {
        String url = properties.getProperty("url");
        if (url != null)
            return url;
        else
            throw new RuntimeException("url not specified in the config.properties file.");
    }

    public String getReportPath() {
        String path = properties.getProperty("reportPath");
        if (path != null)
            return path;
        else
            throw new RuntimeException("reportPath not specified in the config.properties file.");
    }
}
