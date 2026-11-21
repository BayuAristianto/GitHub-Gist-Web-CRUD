package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/staging.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Gagal memuat staging.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
