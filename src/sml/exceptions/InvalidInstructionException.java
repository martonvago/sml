package sml.exceptions;

/**
 * InvalidInstructionException is an Exception thrown when the Translator encounters an Instruction class
 * which does not specify a valid instruction.
 *
 * @author Marton Vago
 */
public class InvalidInstructionException extends Exception {
    public InvalidInstructionException(String message) {
        super(message);
    }
}
