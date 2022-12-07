package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * A SubInstruction is an Instruction representing a subtraction command.
 *
 * @author Marton Vago
 */
public class SubInstruction extends Instruction {
	private final int resultRegister;
	private final int register1;
	private final int register2;

	/**
	 * Constructor
	 * @param label the label for the command
	 * @param r the register where the result will be stored
	 * @param s1 the register where the minuend is read from
	 * @param s2 the register where the subtrahend is read from
	 */
	public SubInstruction(String label, int r, int s1, int s2) {
		super(label, "sub");
		resultRegister = r;
		register1 = s1;
		register2 = s2;
	}

	@Override
	public void execute(Machine m) {
		var value1 = m.getRegisters().getRegister(register1);
		var value2 = m.getRegisters().getRegister(register2);
		m.getRegisters().setRegister(resultRegister, value1 - value2);
	}

	@Override
	public String toString() {
		return super.toString()
			+ " store in register " + resultRegister
			+ " the contents of register " + register2
			+ " subtracted from the contents of register " + register1;
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
