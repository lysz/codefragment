package com.aora.wifi.tools;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyInfo {

    private static ResourceBundle resourceBundle;

    // 加载properties文件
    static {

        try {
            resourceBundle = ResourceBundle.getBundle("config", Locale.CHINA);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // 获取key所对应的属性值
    public static String getValue(String key, Object[] paraArr) {

        if (key == null || "".equals(key.trim())) {
            return null;
        }

        String aKey = key.trim();
        String format = null;

        try {
            String value = resourceBundle.getString(aKey);
            format = (MessageFormat.format(value, paraArr)).trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return format;
    }

    public static void main(String[] args) {
        String aa = PropertyInfo.getValue("hello", null);
        System.out.println(">>" + aa);
    }

}