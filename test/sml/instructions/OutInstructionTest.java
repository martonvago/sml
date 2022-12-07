package sml.instructions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutInstructionTest {
  private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;
  private static final String lineSeparator = System.getProperty("line.separator");

  private Machine m;
  private Registers regs;

  @BeforeAll
  static void beforeAll() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterAll
  static void afterAll() {
    System.setOut(originalOut);
  }

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
    outContent.reset();
  }

  @Test
  void executePrintsValueCorrectly() {
    // given
    regs.setRegister(2,4);
    var instruction = new OutInstruction("lbl", 2);

    // when
    instruction.execute(m);

    // then
    assertEquals("4" + lineSeparator, outContent.toString());
  }

  @Test
  void executePrintsValueCorrectlyWhenRegisterEmpty() {
    // given
    var instruction = new OutInstruction("lbl", 2);

    // when
    instruction.execute(m);

    // then
    assertEquals("0" + lineSeparator, outContent.toString());
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      var instruction = new OutInstruction("lbl", 32);

      // when
      instruction.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    var instruction = new OutInstruction("lbl", 1);

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: out] print the contents of register 1 to the console", result);
  }
}
