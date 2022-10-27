package sml.instructions;

import sml.Instruction;
import sml.LabelBridge;
import sml.Machine;

public class LinInstruction extends Instruction {
	private String label;
	private int register;
	private int value;
	private int register2;

	public LinInstruction(final String label, int register, int value) {
		super(label, "lin");
		this.register = register;
		this.value = value;
	}

	@Override
	public void execute(Machine m) {

		m.getRegisters().setRegister(register, value);
		var lbl = new LabelBridge(m);
		lbl.indexOf("asdads");
	}

	@Override
	public String toString() {
		return super.toString() + " store in register " + register + " the value " + value;
	}
}
