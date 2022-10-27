package sml.instructions;

import sml.Instruction;
import sml.Machine;

public class AddInstruction extends Instruction {
	private String label;
	private int result;
	private int register1;
	private int register2;

	public AddInstruction(String label, int i1, int i2, int i3) {
		super(label, "add");
		result = i1;
		register1 = i2;
		register2 = i3;
	}

	@Override
	public void execute(Machine m) {
		var value1 = m.getRegisters().getRegister(register1);
		var value2 = m.getRegisters().getRegister(register2);
		m.getRegisters().setRegister(result, value1 + value2);
	}

	@Override
	public String toString() {
		return super.toString() + " store in register " + result + " the contents of register " + register1
				+ " added to the contents of register " + register2;
	}
}
