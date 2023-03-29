package ru.stomprf.main.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public Properties properties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/macbookpro/IdeaProjects/music_bot/src/main/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
