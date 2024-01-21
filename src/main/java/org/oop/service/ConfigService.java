package org.oop.service;

import org.oop.api.IConfigService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigService implements IConfigService {
    private final Properties properties;
    public ConfigService() {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
