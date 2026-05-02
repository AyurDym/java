package config;

import java.io.*;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "app.properties";
    private Properties properties;

    public AppConfig() {
        properties = new Properties();
        loadDefaults();
        loadFromFile();
    }

    private void loadDefaults() {
        properties.setProperty("storage.directory", "salads");
        properties.setProperty("app.name", "Chef Salad Manager");
        properties.setProperty("app.version", "1.0");
    }

    private void loadFromFile() {
        File file = new File(CONFIG_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("Не удалось загрузить конфигурацию: " + e.getMessage());
            }
        } else {
            saveToFile();
        }
    }

    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Application Configuration");
        } catch (IOException e) {
            System.err.println("Не удалось сохранить конфигурацию: " + e.getMessage());
        }
    }

    public String getStorageDirectory() {
        return properties.getProperty("storage.directory", "salads");
    }

    public String getAppName() {
        return properties.getProperty("app.name", "Chef Salad Manager");
    }
}
