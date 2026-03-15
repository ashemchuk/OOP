package ru.ashemchuk;

/**
 * Exception thrown when there is an error loading or parsing the pizzeria configuration.
 * This runtime exception indicates problems with configuration file access,
 * format validation, or data integrity during the configuration loading phase.
 */
public class ConfigurationLoadingException extends RuntimeException {

    /**
     * Constructs a new ConfigurationLoadingException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public ConfigurationLoadingException(String message) {
        super(message);
    }
}