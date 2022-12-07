package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * An AddInstruction is an Instruction representing an addition command.
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
	 * @param r the register where the result will be stored
	 * @param s1 the register where one of the values to add is read from
	 * @param s2 the register where the other value to add is read from
	 */
	public AddInstruction(String label, int r, int s1, int s2) {
		super(label, "add");
		resultRegister = r;
		register1 = s1;
		register2 = s2;
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

	public int getResultRegister() {
		return resultRegister;
	}

	public int getRegister1() {
		return register1;
	}

	public int getRegister2() {
		return register2;
	}
}
