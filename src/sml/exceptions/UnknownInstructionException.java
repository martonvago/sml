package sml.exceptions;

/**
 * UnknownInstructionException is an Exception thrown when the Translator encounters an op code for which no
 * implementing class has been registered.
 *
 * @author Marton Vago
 */
public class UnknownInstructionException extends Exception {
    public UnknownInstructionException(String opCode) {
        super("No instruction found for op code '" + opCode
                + "'. Are you sure a mapping has been created between the op code and the implementing class?");
    }
}
