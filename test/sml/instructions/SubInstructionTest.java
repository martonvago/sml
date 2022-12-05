package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubInstructionTest {
  private Machine m;
  private Instruction i;
  private Registers regs;

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
  }

  @AfterEach
  void tearDown() {
    m = null;
    i = null;
    regs = null;
  }

  @Test
  void executeSubtractsPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,6);
    i = new SubInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(-1, m.getRegisters().getRegister(1));
  }

  @Test
  void executeSubtractsZerosCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,0);
    i = new SubInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(0, m.getRegisters().getRegister(1));
  }

  @Test
  void executeSubtractsNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,-6);
    i = new SubInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(1, m.getRegisters().getRegister(1));
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      i = new SubInstruction("lbl", 1, 32, 3);

      // when
      i.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,-6);
    i = new SubInstruction("lbl", 1, 2, 3);

    // when
    var result = i.toString();

    // then
    assertEquals("[lbl: sub] store in register 1 the contents of register 3 subtracted from the contents of register 2", result);
  }
}
