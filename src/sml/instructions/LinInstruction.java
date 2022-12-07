package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * A LinInstruction is an Instruction representing a command for storing an integer in a register.
 *
 * @author Marton Vago
 */
public class LinInstruction extends Instruction {
	private final int register;
	private final int value;

	/**
	 * Constructor
	 * @param label the label for the command
	 * @param register the register where the integer will be stored
	 * @param value the integer to store
	 */
	public LinInstruction(final String label, int register, int value) {
		super(label, "lin");
		this.register = register;
		this.value = value;
	}

	@Override
	public void execute(Machine m) {
		m.getRegisters().setRegister(register, value);
	}

	@Override
	public String toString() {
		return super.toString() + " store in register " + register + " the value " + value;
	}

	public int getRegister() {
		return register;
	}

	public int getValue() {
		return value;
	}
}
