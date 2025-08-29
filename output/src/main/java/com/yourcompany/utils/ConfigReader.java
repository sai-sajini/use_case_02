package com.yourcompany.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";
    private static boolean isLoaded = false;

    private ConfigReader() {}

    public static void loadConfig() {
        if (isLoaded)
            return;
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            isLoaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    public static String getProperty(String key) {
        if (!isLoaded) {
            loadConfig();
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getBaseURL() {
        return getProperty("baseURL");
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }

    public static String getTestDataPath() {
        return getProperty("testDataPath");
    }
}
