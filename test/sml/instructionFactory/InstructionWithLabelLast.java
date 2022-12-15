package sml.instructionFactory;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing. Its constructor does not expect a String as its first argument,
 * which amounts to a violation of SML syntax.
 *
 * @author Marton Vago
 */
public class InstructionWithLabelLast extends Instruction {
	private final int value;

	public InstructionWithLabelLast(int v, String label) {
		super(label, "test");
		value = v;
	}

	@Override
	public void execute(Machine m) { }

	@Override
	public String toString() {
		return super.toString() + " contains " + value;
	}
}
