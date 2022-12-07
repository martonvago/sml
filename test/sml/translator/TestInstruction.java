package sml.translator;

import sml.Instruction;
import sml.Machine;

/**
 * An arbitrary Instruction subclass for testing, containing 3 String operands.
 *
 * @author Marton Vago
 */
public class TestInstruction extends Instruction {
	private final String value1;
	private final String value2;
	private final String value3;

	public TestInstruction(String label, String v1, String v2, String v3) {
		super(label, "test");
		value1 = v1;
		value2 = v2;
		value3 = v3;
	}

	@Override
	public void execute(Machine m) { }

	@Override
	public String toString() {
		return super.toString() + " contains " + value1 + " and " + value2 + " and " + value3;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}

	public String getValue3() {
		return value3;
	}
}
