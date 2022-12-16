package sml.translator;

import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Translator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These tests are not meant to be exhaustive, only for sanity checking purposes.
 */
public class TranslatorTest {
    private final Machine machine = new Machine();
    private final List<Instruction> instructions = new ArrayList<>();
    private final String pathPrefix = "test/sml/translator/";

    @Test
    public void parsesFileWithOnlyWhitespaceCorrectly() {
        // given
        var translator = new Translator(pathPrefix + "whitespace.sml");

        // when
        var success = translator.readAndTranslate(machine.getLabels(), instructions);

        // then
        assertTrue(success);
        assertThatLabelsAndInstructionsEmpty();
    }

    @Test
    public void parsesFileWithOnlyLabelsCorrectly() {
        // given
        var translator = new Translator(pathPrefix + "labels.sml");

        // when
        var success = translator.readAndTranslate(machine.getLabels(), instructions);

        // then
        assertTrue(success);
        assertThatLabelsAndInstructionsEmpty();
    }

    @Test
    public void parsesCompleteFileCorrectly() {
        // given
        var translator = new Translator("sample-files/test1.sml");

        // when
        var success = translator.readAndTranslate(machine.getLabels(), instructions);

        // then
        assertTrue(success);
        assertEquals("(L1, Thing, L2, L3, L4, L5)", "" + machine.getLabels());
        assertEquals(6, instructions.size());
    }

    @Test
    public void returnsSuccessFalseWhenFileNotFound() {
        // given
        var translator = new Translator("i/do/not/exist.sml");

        // when
        var success = translator.readAndTranslate(machine.getLabels(), instructions);

        // then
        assertFalse(success);
        assertThatLabelsAndInstructionsEmpty();
    }

    private void assertThatLabelsAndInstructionsEmpty() {
        assertEquals("()", "" + machine.getLabels());
        assertEquals(0, instructions.size());
    }
}
