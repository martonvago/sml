package sml.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Translator;
import sml.exceptions.InstructionParseFailedException;
import sml.instructions.*;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static sml.Translator.INSTRUCTION_KEY_PREFIX;

class GetInstructionTest {
  private Properties properties;

  @BeforeEach
  void setUp() {
    properties = new Properties();
  }

  @Test
  void returnsNullIfLineEmpty() throws InstructionParseFailedException {
    // given
    Translator t = new Translator("a/path", properties);

    // when
    var instruction = t.getInstruction("L0");

    // then
    assertNull(instruction);
  }

  @Test
  void parsesAddCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "add";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), AddInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register1 = 1;
    var register2 = 2;
    var resultRegister = 3;
    t.setLine(String.format("%s %d %d %d", opCode, resultRegister, register1, register2));

    // when
    var instruction = t.getInstruction(label);

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
  void parsesSubCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "sub";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), SubInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register1 = 1;
    var register2 = 2;
    var resultRegister = 3;
    t.setLine(String.format("%s %d %d %d", opCode, resultRegister, register1, register2));

    // when
    var instruction = t.getInstruction(label);

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
  void parsesMulCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "mul";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), MulInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register1 = 1;
    var register2 = 2;
    var resultRegister = 3;
    t.setLine(String.format("%s %d %d %d", opCode, resultRegister, register1, register2));

    // when
    var instruction = t.getInstruction(label);

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
  void parsesDivCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "div";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), DivInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register1 = 1;
    var register2 = 2;
    var resultRegister = 3;
    t.setLine(String.format("%s %d %d %d", opCode, resultRegister, register1, register2));

    // when
    var instruction = t.getInstruction(label);

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
  void parsesLinCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "lin";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), LinInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register = 1;
    var value = 33;
    t.setLine(String.format("%s %d %d", opCode, register, value));

    // when
    var instruction = t.getInstruction(label);

    // then
    assertNotNull(instruction);
    assertEquals(label, instruction.getLabel());
    assertEquals(opCode, instruction.getOpcode());
    var linInstruction = (LinInstruction) instruction;
    assertEquals(register, linInstruction.getRegister());
    assertEquals(value, linInstruction.getValue());
  }

  @Test
  void parsesOutCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "out";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), OutInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register = 1;
    t.setLine(String.format("%s %d", opCode, register));

    // when
    var instruction = t.getInstruction(label);

    // then
    assertNotNull(instruction);
    assertEquals(label, instruction.getLabel());
    assertEquals(opCode, instruction.getOpcode());
    var outInstruction = (OutInstruction) instruction;
    assertEquals(register, outInstruction.getRegister());
  }

  @Test
  void parsesBnzCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "bnz";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), BnzInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var register = 1;
    var jumpToLabel = "target";
    t.setLine(String.format("%s %d %s", opCode, register, jumpToLabel));

    // when
    var instruction = t.getInstruction(label);

    // then
    assertNotNull(instruction);
    assertEquals(label, instruction.getLabel());
    assertEquals(opCode, instruction.getOpcode());
    var bnzInstruction = (BnzInstruction) instruction;
    assertEquals(register, bnzInstruction.getRegister());
    assertEquals(jumpToLabel, bnzInstruction.getJumpToLabel());
  }

  @Test
  void parsesTestInstructionCorrectly() throws InstructionParseFailedException {
    // given
    var opCode = "test";
    properties.setProperty(getInstructionKeyWithPrefix(opCode), TestInstruction.class.getName());
    Translator t = new Translator("a/path", properties);
    var label = "L0";
    var value1 = "first";
    var value2 = "second";
    var value3 = "third";
    t.setLine(String.format("%s %s %s %s", opCode, value1, value2, value3));

    // when
    var instruction = t.getInstruction(label);

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
  void throwsInstructionParseFailedExceptionIfFirstOperandNotString() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), InstructionWithLabelLast.class.getName());
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfClassConstructorPrivate() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), InstructionWithPrivateConstructor.class.getName());
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfNoImplementationLinkedInConfigFile() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      Translator t = new Translator("a/path", properties);
      t.setLine("out 2");

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfOperandNotStringOrInt() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), InstructionWithUnexpectedOperand.class.getName());
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfInstructionClassNotFound() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), "this.class.does.not.exist");
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfErrorWhileInstantiatingClass() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), InstructionWithBrokenConstructor.class.getName());
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  @Test
  void throwsInstructionParseFailedExceptionIfClassCannotBeInstantiated() {
    assertThrows(InstructionParseFailedException.class, () -> {
      // given
      var opCode = "test";
      properties.setProperty(getInstructionKeyWithPrefix(opCode), AbstractTestInstruction.class.getName());
      Translator t = new Translator("a/path", properties);
      t.setLine(String.format("%s 123", opCode));

      // when
      t.getInstruction("L0");
    });
  }

  private String getInstructionKeyWithPrefix(String opCode) {
    return INSTRUCTION_KEY_PREFIX + opCode;
  }
}
