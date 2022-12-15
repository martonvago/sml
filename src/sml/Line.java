package sml;

/**
 * A Line represents a line in a .sml program file.
 * By default, the contents of the line that can vary between instructions (everything other than label and op code)
 * are not processed. Classes interacting with the line can consume this content according to their own needs.
 *
 * @author Classroom[bot] and Marton Vago
 */
public class Line {
    /**
     * The unprocessed part of the line
     */
    private String content;

    /**
     * The first element in the line
     */
    private String label;

    /**
     * The second element in the line
     */
    private String opCode;

    /**
     * Construct a line without any processing.
     *
     * @param lineContent the raw content of the line
     */
    private Line(String lineContent) {
        content = lineContent;
    }

    /**
     * Initialise a line by parsing its first two elements as the label and the op code.
     */
    private void init() {
        label = scan();
        opCode = scan();
    }

    /**
     * Create a Line and parse the label and op code.
     *
     * @param content the raw content of the line
     * @return the preprocessed Line
     */
    public static Line of(String content) {
        Line line = new Line(content);
        line.init();
        return line;
    }

    /**
     * Return true if the Line does not contain an instruction.
     * In principle, the shortest instruction is composed of a label and an op code.
     * Due to the initialisation sequence, if a Line contains an op code, it is also guaranteed to contain a label.
     *
     * @return if the line is empty
     */
    public boolean isEmpty() {
        return opCode == null || opCode.isEmpty();
    }

    /**
     * Return the label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Return the op code.
     *
     * @return the op code
     */
    public String getOpCode() {
        return opCode;
    }

    /**
     * Remove the first word from the content and return it.
     * If the content contains only whitespace, return the empty string.
     *
     * @return the first word of the line
     */
    public String scan() {
        content = content.trim();
        if (content.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < content.length() && content.charAt(i) != ' ' && content.charAt(i) != '\t') {
            i++;
        }
        String word = content.substring(0, i);
        content = content.substring(i);
        return word;
    }

    /**
     * Remove the first word from the content and return it parsed as an integer.
     * If the word is not parseable as an integer, return the maximum integer.
     *
     * @return the first word of the line as an integer
     */
    public int scanInt() {
        String word = scan();

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
