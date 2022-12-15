package sml;

import sml.exceptions.FailedToLoadAppConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * AppConfig is a singleton instruction mapper. It contains the properties defined in the config.properties file.
 * These properties map op codes to the (names of the) classes which implement them.
 *
 * @author Marton Vago
 */
public class AppConfig implements InstructionMapper {
    /**
     * The single config instance
     */
    private static AppConfig instance;

    /**
     * The op code mappings
     */
    private final Properties properties = new Properties();

    /**
     * The name of the file where the properties are defined
     */
    public static final String FILE_NAME = "config.properties";

    /**
     * The prefix of the keys for instructions in the properties file
     */
    public static final String INSTRUCTION_KEY_PREFIX = "sml.instructions.";

    /**
     * Constructor:
     * Instantiate the singleton instance by reading in the mappings defined in the properties file.
     *
     * @throws FailedToLoadAppConfigException if the properties file cannot be loaded
     */
    private AppConfig() throws FailedToLoadAppConfigException {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            properties.load(input);
        } catch (IOException e) {
            throw new FailedToLoadAppConfigException(e);
        }
    }

    /**
     * Return the existing singleton instance or create one if it doesn't yet exist.
     *
     * @return the instance
     * @throws FailedToLoadAppConfigException if the properties file cannot be loaded
     */
    public static AppConfig getInstance() throws FailedToLoadAppConfigException {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    /**
     * Return the name of the class which implements the given op code, or null if no mapping was found for the op code.
     * The class name is defined in the properties file.
     *
     * @param opCode the op code read from a line of a program
     * @return the name of the corresponding class
     */
    @Override
    public String getClassNameForOpCode(String opCode) {
        String key = INSTRUCTION_KEY_PREFIX + opCode;
        return properties.getProperty(key);
    }
}
