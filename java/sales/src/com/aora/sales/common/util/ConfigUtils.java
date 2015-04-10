package com.aora.sales.common.util;

import java.util.ResourceBundle;

public class ConfigUtils {

    private static ResourceBundle bundle = ResourceBundle.getBundle("configuration/config");

    private ConfigUtils() {

    }

    public static String getProperty(String key) {
        String value = bundle.getString(key);
        return value;
    }
}
