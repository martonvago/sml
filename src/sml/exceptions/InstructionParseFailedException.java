package sml.exceptions;

/**
 * InstructionParseFailedException is an Exception thrown when the Translator fails to parse a line in a program file
 * into an Instruction for any reason.
 *
 * @author Marton Vago
 */
public class InstructionParseFailedException extends Exception {
    public InstructionParseFailedException(Exception e) {
        super("Failed to parse instruction.", e);
    }
}
