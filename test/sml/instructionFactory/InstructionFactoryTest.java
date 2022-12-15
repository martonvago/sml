package sml.instructionFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.InstructionFactory;
import sml.Line;
import sml.exceptions.InstructionParseFailedException;
import sml.instructions.*;

import static org.junit.jupiter.api.Assertions.*;

class InstructionFactoryTest {
    private final TestAppConfig testMapper = new TestAppConfig();
    private final InstructionFactory instructionFactory = new InstructionFactory(testMapper);
    
    @BeforeEach
    public void setUp() {
        testMapper.clear();
    }
    
    @Test
    public void returnsNullIfLineIsEmptyString() throws InstructionParseFailedException {
        // given
        var line = Line.of("");
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNull(instruction);
    }

    @Test
    public void returnsNullIfLineHasOnlyLabel() throws InstructionParseFailedException {
        // given
        var line = Line.of("L0");

        // when
        var instruction = instructionFactory.createInstruction(line);

        // then
        assertNull(instruction);
    }
    
    @Test
    public void parsesAddCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "add";
        testMapper.setProperty(opCode, AddInstruction.class.getName());
        var label = "L0";
        var register1 = 1;
        var register2 = 2;
        var resultRegister = 3;
        var line = Line.of(String.format("%s %s %d %d %d", label, opCode, resultRegister, register1, register2));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var addInstruction = (AddInstruction) instruction;
        assertEquals(register1, addInstruction.getRegister1());
        assertEquals(register2, addInstruction.getRegister2());
        assertEquals(resultRegister, addInstruction.getResultRegister());
    }
    
    @Test
    public void parsesSubCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "sub";
        testMapper.setProperty(opCode, SubInstruction.class.getName());
        var label = "L0";
        var register1 = 1;
        var register2 = 2;
        var resultRegister = 3;
        var line = Line.of(String.format("%s %s %d %d %d", label, opCode, resultRegister, register1, register2));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var subInstruction = (SubInstruction) instruction;
        assertEquals(register1, subInstruction.getRegister1());
        assertEquals(register2, subInstruction.getRegister2());
        assertEquals(resultRegister, subInstruction.getResultRegister());
    }
    
    @Test
    public void parsesMulCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "mul";
        testMapper.setProperty(opCode, MulInstruction.class.getName());
        var label = "L0";
        var register1 = 1;
        var register2 = 2;
        var resultRegister = 3;
        var line = Line.of(String.format("%s %s %d %d %d", label, opCode, resultRegister, register1, register2));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var mulInstruction = (MulInstruction) instruction;
        assertEquals(register1, mulInstruction.getRegister1());
        assertEquals(register2, mulInstruction.getRegister2());
        assertEquals(resultRegister, mulInstruction.getResultRegister());
    }
    
    @Test
    public void parsesDivCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "div";
        testMapper.setProperty(opCode, DivInstruction.class.getName());
        var label = "L0";
        var register1 = 1;
        var register2 = 2;
        var resultRegister = 3;
        var line = Line.of(String.format("%s %s %d %d %d", label, opCode, resultRegister, register1, register2));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var divInstruction = (DivInstruction) instruction;
        assertEquals(register1, divInstruction.getRegister1());
        assertEquals(register2, divInstruction.getRegister2());
        assertEquals(resultRegister, divInstruction.getResultRegister());
    }
    
    @Test
    public void parsesLinCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "lin";
        testMapper.setProperty(opCode, LinInstruction.class.getName());
        var label = "L0";
        var register = 1;
        var value = 33;
        var line = Line.of(String.format("%s %s %d %d", label, opCode, register, value));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var linInstruction = (LinInstruction) instruction;
        assertEquals(register, linInstruction.getRegister());
        assertEquals(value, linInstruction.getValue());
    }
    
    @Test
    public void parsesOutCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "out";
        testMapper.setProperty(opCode, OutInstruction.class.getName());
        var label = "L0";
        var register = 1;
        var line = Line.of(String.format("%s %s %d", label, opCode, register));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var outInstruction = (OutInstruction) instruction;
        assertEquals(register, outInstruction.getRegister());
    }
    
    @Test
    public void parsesBnzCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "bnz";
        testMapper.setProperty(opCode, BnzInstruction.class.getName());
        var label = "L0";
        var register = 1;
        var jumpToLabel = "target";
        var line = Line.of(String.format("%s %s %d %s", label, opCode, register, jumpToLabel));
        
        // when
        var instruction = instructionFactory.createInstruction(line);
        
        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var bnzInstruction = (BnzInstruction) instruction;
        assertEquals(register, bnzInstruction.getRegister());
        assertEquals(jumpToLabel, bnzInstruction.getJumpToLabel());
    }

    @Test
    public void parsesInstructionWithOnlyOpCodeCorrectly() throws InstructionParseFailedException {
        // given
        var opCode = "test";
        testMapper.setProperty(opCode, InstructionWithOnlyOpCode.class.getName());
        var label = "L0";
        var line = Line.of(String.format("%s %s", label, opCode));

        // when
        var instruction = instructionFactory.createInstruction(line);

        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
    }

    @Test
    public void ignoresExtraLineElementsWhenParsing() throws InstructionParseFailedException {
        // given
        var opCode = "test";
        testMapper.setProperty(opCode, TestInstruction.class.getName());
        var label = "L0";
        var value1 = "first";
        var value2 = "second";
        var value3 = 34;
        var line = Line.of(String.format("%s %s %s %s %d something else 1234", label, opCode, value1, value2, value3));

        // when
        var instruction = instructionFactory.createInstruction(line);

        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var testInstruction = (TestInstruction) instruction;
        assertEquals(value1, testInstruction.getValue1());
        assertEquals(value2, testInstruction.getValue2());
        assertEquals(value3, testInstruction.getValue3());
    }

    @Test
    public void handlesFailedIntegerParseGracefully() throws InstructionParseFailedException {
        // given
        var opCode = "test";
        testMapper.setProperty(opCode, TestInstruction.class.getName());
        var label = "L0";
        var value1 = "first";
        var value2 = "second";
        var line = Line.of(String.format("%s %s %s %s notANumber", label, opCode, value1, value2));

        // when
        var instruction = instructionFactory.createInstruction(line);

        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var testInstruction = (TestInstruction) instruction;
        assertEquals(value1, testInstruction.getValue1());
        assertEquals(value2, testInstruction.getValue2());
        assertEquals(Integer.MAX_VALUE, testInstruction.getValue3());
    }

    @Test
    public void handlesMissingOperandsGracefullyWhenParsing() throws InstructionParseFailedException {
        // given
        var opCode = "test";
        testMapper.setProperty(opCode, TestInstruction.class.getName());
        var label = "L0";
        var value1 = "first";
        var line = Line.of(String.format("%s %s %s", label, opCode, value1));

        // when
        var instruction = instructionFactory.createInstruction(line);

        // then
        assertNotNull(instruction);
        assertEquals(label, instruction.getLabel());
        assertEquals(opCode, instruction.getOpcode());
        var testInstruction = (TestInstruction) instruction;
        assertEquals(value1, testInstruction.getValue1());
        assertEquals("", testInstruction.getValue2());
        assertEquals(Integer.MAX_VALUE, testInstruction.getValue3());
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfFirstOperandNotString() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, InstructionWithLabelLast.class.getName());
            var line = Line.of(String.format("%s 123 L0", opCode));
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfClassConstructorPrivate() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, InstructionWithPrivateConstructor.class.getName());
            var line = Line.of(String.format("L0 %s 123", opCode));
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfNoImplementationLinkedInConfigFile() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var line = Line.of("L0 out 2");
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfOperandNotStringOrInt() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, InstructionWithUnexpectedOperand.class.getName());
            var line = Line.of(String.format("L0 %s 123", opCode));
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfInstructionClassNotFound() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, "this.class.does.not.exist");
            var line = Line.of(String.format("L0 %s 123", opCode));
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfErrorWhileInstantiatingClass() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, InstructionWithBrokenConstructor.class.getName());
            var line = Line.of(String.format("L0 %s 123", opCode));
            
            // when
            instructionFactory.createInstruction(line);
        });
    }
    
    @Test
    public void throwsInstructionParseFailedExceptionIfClassCannotBeInstantiated() {
        assertThrows(InstructionParseFailedException.class, () -> {
            // given
            var opCode = "test";
            testMapper.setProperty(opCode, AbstractTestInstruction.class.getName());
            var line = Line.of(String.format("L0 %s 123", opCode));

            // when
            instructionFactory.createInstruction(line);
        });
    }
}
