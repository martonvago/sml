package sml.instructionFactory;

import sml.InstructionMapper;

import java.util.Properties;

/**
 * Dummy instruction mapper to facilitate testing the instruction factory.
 */
public class TestAppConfig implements InstructionMapper {
    private final Properties properties = new Properties();

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void clear() {
        properties.clear();
    }

    @Override
    public String getClassNameForOpCode(String opCode) {
        return properties.getProperty(opCode);
    }
}
