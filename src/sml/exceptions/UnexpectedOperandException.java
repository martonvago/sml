package sml.exceptions;

/**
 * UnexpectedOperandException is an Exception thrown when the Translator encounters an Instruction with an operand
 * whose type is not String, Integer or int.
 *
 * @author Marton Vago
 */
public class UnexpectedOperandException extends Exception {
    public UnexpectedOperandException(Class<?> paramType) {
        super("Expected operand to have type 'String', 'Integer' or 'int', but operand type was " + paramType);
    }
}
