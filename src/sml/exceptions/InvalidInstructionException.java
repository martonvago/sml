package sml.exceptions;

/**
 * InvalidInstructionException is an Exception thrown when the Translator encounters an Instruction specification
 * which violates SML syntax.
 *
 * @author Marton Vago
 */
public class InvalidInstructionException extends Exception {
    public InvalidInstructionException(String message) {
        super(message);
    }
}
