package sml.instructionFactory;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing. It has a private constructor.
 *
 * @author Marton Vago
 */
public abstract class InstructionWithPrivateConstructor extends Instruction {
	private final int value1;

	private InstructionWithPrivateConstructor(String label, int v1) {
		super(label, "test");
		value1 = v1;
	}

	@Override
	public void execute(Machine m) { }

	@Override
	public String toString() {
		return super.toString() + " contains " + value1;
	}
}
