package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinInstructionTest {
  private Machine m;
  private Instruction i;

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
  }

  @AfterEach
  void tearDown() {
    m = null;
    i = null;
  }

  @Test
  void executeStoresIntegerCorrectly() {
    // given
    i = new LinInstruction("lbl", 1, 2);

    // when
    i.execute(m);

    // then
    assertEquals(2, m.getRegisters().getRegister(1));
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      i = new LinInstruction("lbl", 32, 3);

      // when
      i.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    i = new LinInstruction("lbl", 1, 2);

    // when
    var result = i.toString();

    // then
    assertEquals("[lbl: lin] store in register 1 the value 2", result);
  }
}
