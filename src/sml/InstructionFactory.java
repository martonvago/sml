package sml;

import sml.exceptions.InstructionParseFailedException;
import sml.exceptions.InvalidInstructionException;
import sml.exceptions.UnknownInstructionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * InstructionFactory is responsible for parsing a Line from a program file into the Instruction subclass
 * corresponding to the op code.
 * 
 * @author Marton Vago
 */
public class InstructionFactory {
    /**
     * The mapping service used by the factory to obtain the class name for the op code
     */
    private final InstructionMapper instructionMapper;

    /**
     * Construct a factory instance with an instruction mapper injected
     * @param mapper the instruction mapper
     */
    public InstructionFactory(InstructionMapper mapper) {
        instructionMapper = mapper;
    }

    /**
     * Parse a Line into the Instruction subclass corresponding to the op code.
     * Return null if the Line is empty.
     * 
     * @param line a line in a program file
     * @return the parsed instruction
     * @throws InstructionParseFailedException if the parsing was unsuccessful
     */
    public Instruction createInstruction(Line line) throws InstructionParseFailedException {
        if (line.isEmpty()) {
            return null;
        }

        // Look up the class implementing the instruction with the given op code
        var className = instructionMapper.getClassNameForOpCode(line.getOpCode());

        try {
            if (className == null) {
                throw new UnknownInstructionException(line.getOpCode());
            }

            var constructor = getConstructor(className);
            var constructorArguments = getConstructorArguments(constructor, line);

            // Instantiate the instruction
            return (Instruction) constructor.newInstance(constructorArguments);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | IllegalArgumentException | UnknownInstructionException | InvalidInstructionException
                 | ClassNotFoundException e
        ) {
            // Collect all exceptions into a meaningful umbrella exception
            throw new InstructionParseFailedException(e);
        }
    }

    /**
     * Return the first public constructor of the class with the given class name.
     * 
     * @param className the name of the class
     * @return the constructor 
     * @throws ClassNotFoundException if there is no class corresponding to the class name
     * @throws InvalidInstructionException if the class has no public constructor
     */
    private Constructor<?> getConstructor(String className) throws ClassNotFoundException, InvalidInstructionException {
        var instructionClass = Class.forName(className);
        var constructors = instructionClass.getConstructors();
        if (constructors.length < 1) {
            throw new InvalidInstructionException("Instructions must have a public constructor.");
        }
        return constructors[0];
    }

    /**
     * Extract the arguments required by the given constructor from the line.
     * We expect only string or integer arguments.
     * 
     * @param constructor the constructor
     * @param line the line
     * @return an array containing the constructor's arguments
     * @throws InvalidInstructionException if the first argument's type is not string
     * @throws IllegalArgumentException if an argument's type is not string or integer
     */
    private Object[] getConstructorArguments(Constructor<?> constructor, Line line) throws InvalidInstructionException, IllegalArgumentException {
        // Look up the parameters of the constructor
        var paramTypes = constructor.getParameterTypes();
        if (paramTypes.length < 1 || !paramTypes[0].equals(String.class)) {
            throw new InvalidInstructionException("Instructions must have a label as their first component.");
        }

        // Read in values from the program line which match the constructor parameters
        Object[] constructorArguments = new Object[paramTypes.length];
        // The first argument is always the label
        constructorArguments[0] = line.getLabel();
        // The other arguments depend on the specific implementation
        for (int i = 1; i < paramTypes.length; i++) {
            var paramType = paramTypes[i];
            if (paramType.equals(String.class)) {
                constructorArguments[i] = line.scan();
            } else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                constructorArguments[i] = line.scanInt();
            } else {
                throw new IllegalArgumentException("Expected operand to have type 'String', 'Integer' or 'int', but operand type was " + paramType);
            }
        }
        return constructorArguments;
    }
}
