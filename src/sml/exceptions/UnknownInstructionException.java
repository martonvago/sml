package sml.exceptions;

/**
 * UnknownInstructionException is an Exception thrown when the Translator encounters an op code which does not have
 * an Instruction class specified in the config.properties file.
 *
 * @author Marton Vago
 */
public class UnknownInstructionException extends Exception {
    public UnknownInstructionException(String opCode) {
        super("No instruction found for opcode '" + opCode
                + "'. Are you sure you have registered the instruction in the config.properties file?");
    }
}
