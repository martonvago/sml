package sml.instructionFactory;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing. It is abstract and therefore cannot be instantiated.
 *
 * @author Marton Vago
 */
public abstract class AbstractTestInstruction extends Instruction {
	private final int value1;

	public AbstractTestInstruction(String label, int v1) {
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
