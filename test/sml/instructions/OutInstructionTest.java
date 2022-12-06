package sml.instructions;

import org.junit.jupiter.api.*;
import sml.Instruction;
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
  private Instruction i;
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

  @AfterEach
  void tearDown() {
    m = null;
    i = null;
    regs = null;
  }

  @Test
  void executePrintsValueCorrectly() {
    // given
    regs.setRegister(2,4);
    i = new OutInstruction("lbl", 2);

    // when
    i.execute(m);

    // then
    assertEquals("4" + lineSeparator, outContent.toString());
  }

  @Test
  void executePrintsValueCorrectlyWhenRegisterEmpty() {
    // given
    i = new OutInstruction("lbl", 2);

    // when
    i.execute(m);

    // then
    assertEquals("0" + lineSeparator, outContent.toString());
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      i = new OutInstruction("lbl", 32);

      // when
      i.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    i = new OutInstruction("lbl", 1);

    // when
    var result = i.toString();

    // then
    assertEquals("[lbl: out] print the contents of register 1 to the console", result);
  }
}
