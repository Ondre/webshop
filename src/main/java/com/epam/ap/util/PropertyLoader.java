package com.epam.ap.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public PropertyLoader() {
    }

    public static Properties getProperties(String fileName) throws PropertyLoaderException {
        Properties properties = new Properties();
        try (InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in != null) {
                properties.load(in);
            } else throw new PropertyLoaderException("Cannot load property" + fileName);
        } catch (IOException e) {
             throw new PropertyLoaderException("Cannot load property" + fileName, e);
        }
        return properties;
    }


}
