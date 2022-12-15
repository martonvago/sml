package sml;

import sml.exceptions.FailedToLoadAppConfigException;
import sml.exceptions.InstructionParseFailedException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Translator encapsulates the logic for parsing a .sml file into an SML program. It reads the program file
 * line by line, translating each line into an Instruction.
 *
 * @author Classroom[bot] and Marton Vago
 */
public final class Translator {

	/**
	 * The path to the program file
	 */
	private final String pathToFile;

	/**
	 * Construct a translator for the given file.
	 *
	 * @param path the path to the program file
	 */
	public Translator(String path) {
		pathToFile = path;
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
		var success = false;
		// Initialise instruction factory to null
		InstructionFactory instructionFactory;
		try (var scanner = new Scanner(new File(pathToFile), StandardCharsets.UTF_8)) {
			// Inject singleton app config instance into instruction factory
			instructionFactory = new InstructionFactory(AppConfig.getInstance());

			labels.reset();
			instructions.clear();

			// Each iteration parses a line from the .sml file
			while (scanner.hasNextLine()) {
				var line = Line.of(scanner.nextLine());
				var instruction = instructionFactory.createInstruction(line);
				if (instruction != null) {
					labels.addLabel(line.getLabel());
					instructions.add(instruction);
				}
			}
			success = true;
		} catch (FailedToLoadAppConfigException ex) {
			System.err.println("Error while loading " + AppConfig.FILE_NAME + ".");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.err.println("Error while reading the program file.");
			ex.printStackTrace();
		} catch (InstructionParseFailedException ex) {
			System.err.println("Error while parsing an instruction.");
			ex.printStackTrace();
		}
		return success;
	}
}
