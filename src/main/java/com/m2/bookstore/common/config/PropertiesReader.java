package com.m2.bookstore.common.config;

import com.m2.bookstore.common.logger.CustomLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.m2.bookstore.common.constants.Constants.UTILITY_CLASS;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @createdOn Nov, 2023
 */
public final class PropertiesReader implements CustomLogger {

    public static Map<String, String> readProperties(String fileName) {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);

                // Collect properties into a Map
                return properties.entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toString(),
                                entry -> entry.getValue().toString()
                        ));
            } else {
                log.error(String.format(
                        """
                                %s Not found in the ClassPath ^_^.
                                You look running the APP from the IDE.
                                So Enjoy with Data folder text file.
                                """
                        , fileName)
                );
            }
        } catch (IOException e) {
            log.error("Error Message: " + e.getMessage());
        }
        return Collections.emptyMap();
    }

    public static void retrieveUserFileNamesEnv(String[] args) {
        if (args.length > 0) {
            String filePathConfig = args[0];
            System.setProperty("file.path.config", filePathConfig);
        }
    }

    private PropertiesReader() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
