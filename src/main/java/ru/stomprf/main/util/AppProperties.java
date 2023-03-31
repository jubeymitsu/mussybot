package ru.stomprf.main.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getConfig() {
        return properties;
    }

}
