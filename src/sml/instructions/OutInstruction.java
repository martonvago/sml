package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * An OutInstruction is an Instruction representing a command for printing a value to the console.
 *
 * @author Marton Vago
 */
public class OutInstruction extends Instruction {
	private final int register;

	/**
	 * Constructor
	 * @param label the label for the command
	 * @param register the register where the value to print is read from
	 */
	public OutInstruction(final String label, int register) {
		super(label, "out");
		this.register = register;
	}

	@Override
	public void execute(Machine m) {
		System.out.println(m.getRegisters().getRegister(register));
	}

	@Override
	public String toString() {
		return super.toString() + " print the contents of register " + register + " to the console";
	}

	public int getRegister() {
		return register;
	}
}
