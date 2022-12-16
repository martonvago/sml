package sml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LineTest {

    @Test
    public void initialisedCorrectlyWhenEmptyString() {
        // when
        var line = Line.of("");

        // then
        assertEquals("", line.getLabel());
        assertEquals("", line.getOpCode());
        assertTrue(line.isEmpty());
    }

    @Test
    public void initialisedCorrectlyWhenWhitespaceOnly() {
        // when
        var line = Line.of("        ");

        // then
        assertEquals("", line.getLabel());
        assertEquals("", line.getOpCode());
        assertTrue(line.isEmpty());
    }

    @Test
    public void initialisedCorrectlyWhenOneWord() {
        // given
        var first = "first";

        // when
        var line = Line.of(first);

        // then
        assertEquals(first, line.getLabel());
        assertEquals("", line.getOpCode());
        assertTrue(line.isEmpty());
    }

    @Test
    public void initialisedCorrectlyWhenTwoWords() {
        // given
        var first = "first";
        var second = "second";

        // when
        var line = Line.of(String.format("%s    %s", first, second));

        // then
        assertEquals(first, line.getLabel());
        assertEquals(second, line.getOpCode());
        assertFalse(line.isEmpty());
    }

    @Test
    public void scansCorrectly() {
        // given
        var first = "first";
        var second = "second";
        var third = "third";
        var line = Line.of(String.format("%s    %s  %s", first, second, third));

        // when
        var scanned = line.scan();

        // then
        assertEquals(first, line.getLabel());
        assertEquals(second, line.getOpCode());
        assertFalse(line.isEmpty());
        assertEquals(third, scanned);
    }

    @Test
    public void scansIntegerCorrectly() {
        // given
        var first = "first";
        var second = "second";
        var third = 44;
        var line = Line.of(String.format("%s    %s  %d", first, second, third));

        // when
        var scanned = line.scanInt();

        // then
        assertEquals(third, scanned);
    }

    @Test
    public void scansIntegerCorrectlyWhenParsingFails() {
        // given
        var first = "first";
        var second = "second";
        var third = "third";
        var line = Line.of(String.format("%s    %s  %s", first, second, third));

        // when
        var scanned = line.scanInt();

        // then
        assertEquals(Integer.MAX_VALUE, scanned);
    }

    @Test
    public void scansCorrectlyWhenAllContentConsumed() {
        // given
        var first = "first";
        var second = "second";
        var third = 44;
        var line = Line.of(String.format("%s    %s  %d", first, second, third));

        // when
        line.scanInt();
        var scanned = line.scan();

        // then
        assertEquals("", scanned);
    }

    @Test
    public void scansIntegerCorrectlyWhenAllContentConsumed() {
        // given
        var first = "first";
        var second = "second";
        var third = 44;
        var line = Line.of(String.format("%s    %s  %d", first, second, third));

        // when
        line.scanInt();
        var scanned = line.scanInt();

        // then
        assertEquals(Integer.MAX_VALUE, scanned);
    }
}
