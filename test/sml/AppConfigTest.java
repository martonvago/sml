package sml;

import org.junit.jupiter.api.Test;
import sml.exceptions.FailedToLoadAppConfigException;
import sml.instructions.AddInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AppConfigTest {

    @Test
    public void isSingleton() throws FailedToLoadAppConfigException {
        // given
        var instance = AppConfig.getInstance();

        // when
        var otherInstance = AppConfig.getInstance();

        // then
        assertEquals(instance, otherInstance);
    }

    @Test
    public void returnsCorrectClassNameForOpCode() throws FailedToLoadAppConfigException {
        // given the config.properties file in src

        // when
        var className = AppConfig.getInstance().getClassNameForOpCode("add");

        // then
        assertEquals(AddInstruction.class.getName(), className);
    }

    @Test
    public void returnsNullIfOpCodeNotMapped() throws FailedToLoadAppConfigException {
        // given the config.properties file in src

        // when
        var className = AppConfig.getInstance().getClassNameForOpCode("unknown");

        // then
        assertNull(className);
    }
}
