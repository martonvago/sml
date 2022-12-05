package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * A MulInstruction is an Instruction representing a multiplication command.
 *
 * @author Marton Vago
 */
public class MulInstruction extends Instruction {
	private final int resultRegister;
	private final int register1;
	private final int register2;

	/**
	 * Constructor
	 * @param label the label for the command
	 * @param r the register where the result will be stored
	 * @param s1 the register where one of the factors is read from
	 * @param s2 the register where the other factor is read from
	 */
	public MulInstruction(String label, int r, int s1, int s2) {
		super(label, "mul");
		resultRegister = r;
		register1 = s1;
		register2 = s2;
	}

	@Override
	public void execute(Machine m) {
		var value1 = m.getRegisters().getRegister(register1);
		var value2 = m.getRegisters().getRegister(register2);
		m.getRegisters().setRegister(resultRegister, value1 * value2);
	}

	@Override
	public String toString() {
		return super.toString()
			+ " store in register " + resultRegister
			+ " the contents of register " + register1
			+ " multiplied by the contents of register " + register2;
	}
}
