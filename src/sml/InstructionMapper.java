package sml;

/**
 * An interface implemented by any class which performs a mapping from the op code of an instruction to
 * the name of the class implementing the instruction.
 *
 * @author Marton Vago
 */
public interface InstructionMapper {
    /**
     * Return the name of the class which implements the given op code.
     * It is up to concrete mappers how the mapping is performed.
     *
     * @param opCode the op code read from a line of a program
     * @return the name of the corresponding class
     */
    String getClassNameForOpCode(String opCode);
}
