package sml.exceptions;

/**
 * UnknownOperandException is an Exception thrown when the Translator encounters an Instruction with an operand
 * whose type is not String, Integer or int.
 *
 * @author Marton Vago
 */
public class UnknownOperandException extends Exception {
    public UnknownOperandException(Class<?> paramType) {
        super("Expected operand to have type 'String', 'Integer' or 'int', but operand type was " + paramType);
    }
}
