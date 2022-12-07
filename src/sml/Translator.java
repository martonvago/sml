package sml;

import sml.exceptions.InstructionParseFailedException;
import sml.exceptions.InvalidInstructionException;
import sml.exceptions.UnexpectedOperandException;
import sml.exceptions.UnknownInstructionException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Translator encapsulates the logic for parsing a .sml file into an SML program. It reads the program file
 * line by line, translating each line into an Instruction.
 *
 * @author Classroom[bot] and Marton Vago
 */
public final class Translator {
	/**
	 * The prefix of the keys in config.properties
	 */
	public static final String INSTRUCTION_KEY_PREFIX = "sml.instructions.";

	/**
	 * The contents of the config.properties file
	 */
	private final Properties properties;

	/**
	 * The path to the program file
	 */
	private final String pathToFile;

	/**
	 * The line of the program file being parsed
	 */
	private String line = "";

	/**
	 * Constructor
	 * @param path the path to the program file
	 * @param props the contents of the config.properties file
	 */
	public Translator(String path, Properties props) {
		pathToFile = path;
		properties = props;
	}

	/**
	 * Change the current line of the Translator
	 * @param newLine the new line
	 */
	public void setLine(String newLine) {
		line = newLine;
	}

	/**
	 * Parses an SML program from a file with the given file name.
	 * A file is parsed successfully if each line is translated into an Instruction without errors.
	 * If one line fails, the entire file is deemed invalid and translation is terminated.
	 * Empty lines are skipped.
	 * An empty program file is a valid program file.
	 *
	 * @param labels the labels of a Machine
	 * @param instructions the program instructions of a Machine
	 * @return if the file was parsed successfully or not
	 */
	public boolean readAndTranslate(Labels labels, List<Instruction> instructions) {
		try (var scanner = new Scanner(new File(pathToFile), StandardCharsets.UTF_8)) {
			labels.reset();
			instructions.clear();

			// Each iteration parses a line from the .sml file
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				var label = scan();

				if (label.length() > 0) {
					var instruction = getInstruction(label);
					if (instruction != null) {
						labels.addLabel(label);
						instructions.add(instruction);
					}
				}
			}
			return true;
		} catch (IOException ex) {
			System.err.println("Error while reading the program file.");
			ex.printStackTrace();
			return false;
		} catch (InstructionParseFailedException ex) {
			System.err.println("Error while parsing an instruction.");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Parses a line from a program file (with its label detached) into an SML Instruction.
	 * Returns null if the line is empty.
	 *
	 * @param label the label of the Instruction
	 * @return the parsed Instruction with the label attached
	 */
	public Instruction getInstruction(String label) throws InstructionParseFailedException {

		if (line.equals("")) {
			return null;
		}

		var opCode = scan();
		// Look up the class implementing the instruction with the given op code
		var className = properties.getProperty(INSTRUCTION_KEY_PREFIX + opCode);

		try {
			if (className == null) {
				throw new UnknownInstructionException(opCode);
			}

			var instructionClass = Class.forName(className);
			// Find the constructor for the class
			var constructors = instructionClass.getConstructors();
			if (constructors.length < 1) {
				throw new InvalidInstructionException("Instructions must have a public constructor.");
			}
			var constructor = constructors[0];

			// Look up the parameters of the constructor
			var paramTypes = constructor.getParameterTypes();
			if (paramTypes.length < 1 || !paramTypes[0].equals(String.class)) {
				throw new InvalidInstructionException("Instructions must have a label as their first component.");
			}

			// Read in values from the program line which match the constructor parameters
			Object[] constructorArguments = new Object[paramTypes.length];
			// The first argument is always the label
			constructorArguments[0] = label;
			// The other arguments depend on the specific implementation
			for (int i = 1; i < paramTypes.length; i++) {
				var paramType = paramTypes[i];
				if (paramType.equals(String.class)) {
					constructorArguments[i] = scan();
				} else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
					constructorArguments[i] = scanInt();
				} else {
					throw new UnexpectedOperandException(paramType);
				}
			}

			// Instantiate the instruction
			return (Instruction) constructor.newInstance(constructorArguments);

		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| UnexpectedOperandException | UnknownInstructionException | InvalidInstructionException
				| ClassNotFoundException e
		) {
			// Collect all exceptions into a meaningful umbrella exception
			throw new InstructionParseFailedException(e);
		}
	}

	/**
	 * Remove the first word from 'line' and return it. If the line contains only whitespace, return the empty string.
	 * @return the first word of the line
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0) {
			return "";
		}

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i++;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	/**
	 * Remove the first word from 'line' and return it parsed as an integer.
	 * If the word is not parseable as an integer return the maximum integer.
	 * @return the first word of the line
	 */
	private int scanInt() {
		String word = scan();

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}
