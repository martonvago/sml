package sml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			System.err.println("Could not find config.properties");
			System.exit(-1);
		}

		if (args.length != 1) {
			System.err.println("Incorrect number of arguments - Machine <file> - required");
			System.exit(-1);
		}

		Machine m = new Machine();
		Translator t = new Translator(args[0], properties);
		var success = t.readAndTranslate(m.getLabels(), m.getProg());
		if (!success) {
			System.err.println("Could not parse program file.");
			System.exit(-1);
		}

		System.out.println("Here is the program; it has " + m.getProg().size() + " instructions.");
		System.out.println(m);

		System.out.println("Beginning program execution.");
		m.execute();
		System.out.println("Ending program execution.");

		System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
	}
}
