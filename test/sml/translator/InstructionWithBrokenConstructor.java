package sml.translator;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing. It contains a constructor that throws an exception.
 *
 * @author Marton Vago
 */
public class InstructionWithBrokenConstructor extends Instruction {
	private final int value1;

	public InstructionWithBrokenConstructor(String label, int v1) {
		super(label, "test");
		value1 = v1 / 0;
	}

	@Override
	public void execute(Machine m) { }

	@Override
	public String toString() {
		return super.toString() + " contains " + value1;
	}
}
