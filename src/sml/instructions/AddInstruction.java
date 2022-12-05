package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * A AddInstruction is an Instruction representing an addition command.
 *
 * @author Marton Vago
 */
public class AddInstruction extends Instruction {
	private final int resultRegister;
	private final int register1;
	private final int register2;

	/**
	 * Constructor
	 * @param label the label for the command
	 * @param i1 the register where the result will be stored
	 * @param i2 the register where one of the values to add is read from
	 * @param i3 the register where the other value to add is read from
	 */
	public AddInstruction(String label, int i1, int i2, int i3) {
		super(label, "add");
		resultRegister = i1;
		register1 = i2;
		register2 = i3;
	}

	@Override
	public void execute(Machine m) {
		var value1 = m.getRegisters().getRegister(register1);
		var value2 = m.getRegisters().getRegister(register2);
		m.getRegisters().setRegister(resultRegister, value1 + value2);
	}

	@Override
	public String toString() {
		return super.toString()
			+ " store in register " + resultRegister
			+ " the contents of register " + register1
			+ " added to the contents of register " + register2;
	}
}
