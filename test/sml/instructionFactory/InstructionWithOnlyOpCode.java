package sml.instructionFactory;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing, containing only the op code as an operand.
 *
 * @author Marton Vago
 */
public class InstructionWithOnlyOpCode extends Instruction {
	public InstructionWithOnlyOpCode(String label, String v1, String v2, String v3) {
		super(label, "test");
	}

	@Override
	public void execute(Machine m) { }
}
