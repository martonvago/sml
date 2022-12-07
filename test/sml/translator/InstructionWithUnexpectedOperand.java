package sml.translator;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing. It contains an unexpected Double operand.
 *
 * @author Marton Vago
 */
public class InstructionWithUnexpectedOperand extends Instruction {
	private final Double value;

	public InstructionWithUnexpectedOperand(String label, Double v) {
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
