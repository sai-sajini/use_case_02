package com.yourcompany.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static Logger logger = null;

    public static Logger getLogger(Class<?> clazz) {
        if (logger == null) {
            logger = LogManager.getLogger(clazz);
        }
        return logger;
    }
}
