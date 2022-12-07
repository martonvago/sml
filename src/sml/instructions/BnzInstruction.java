package sml.instructions;

import sml.Instruction;
import sml.Machine;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * A BnzInstruction is an Instruction representing a jump command.
 * If the value in the given register is not 0, program execution will jump to the given label.
 *
 * @author Marton Vago
 */
public class BnzInstruction extends Instruction {
	private final int register;
	private final String jumpToLabel;

	/**
	 * Constructor
	 * @param currentLabel the label for the command
	 * @param s1 the register where the value to check is read from
	 * @param targetLabel the label where execution should jump to
	 */
	public BnzInstruction(String currentLabel, int s1, String targetLabel) {
		super(currentLabel, "bnz");
		register = s1;
		jumpToLabel = targetLabel;
	}

	/**
	 * If the value in the register is not 0, make program execution jump to the first label that matches
	 * the specified label. If there is no such label throw a NoSuchElementException.
	 * @param m the Machine to execute on
	 */
	@Override
	public void execute(Machine m) {
		if (m.getRegisters().getRegister(register) == 0) {
			return;
		}

		var instructions = m.getProg();
		IntStream.range(0, instructions.size())
			.filter(i -> instructions.get(i).getLabel().equals(jumpToLabel))
			.findFirst()
			.ifPresentOrElse(
				m::setPc,
				() -> { throw new NoSuchElementException("No instruction with label" + jumpToLabel); }
			);
	}

	@Override
	public String toString() {
		return super.toString()
			+ " jump to the instruction with label '" + jumpToLabel + "'"
			+ " if the contents of register " + register
			+ " are not zero";
	}

	public int getRegister() {
		return register;
	}

	public String getJumpToLabel() {
		return jumpToLabel;
	}
}
