package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivInstructionTest {
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
  void executeDividesPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,4);
    regs.setRegister(3,2);
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(2, m.getRegisters().getRegister(1));
  }

  @Test
  void executeDividesPositiveAndNegativeNumberCorrectly() {
    // given
    regs.setRegister(2,-4);
    regs.setRegister(3,2);
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(-2, m.getRegisters().getRegister(1));
  }

  @Test
  void executeDividesZeroByNonZeroCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,4);
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(0, m.getRegisters().getRegister(1));
  }

  @Test
  void executeDividesNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-4);
    regs.setRegister(3,-2);
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(2, m.getRegisters().getRegister(1));
  }

  @Test
  void executeThrowsAwayRemainder() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,2);
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(2, m.getRegisters().getRegister(1));
  }

  @Test
  void executeThrowsIfZeroDivision() {
    assertThrows(ArithmeticException.class, () -> {
      // given
      regs.setRegister(2,-6);
      regs.setRegister(3,0);
      i = new DivInstruction("lbl", 1, 2, 3);

      // when
      i.execute(m);
    });
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      i = new DivInstruction("lbl", 1, 32, 3);

      // when
      i.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    i = new DivInstruction("lbl", 1, 2, 3);

    // when
    var result = i.toString();

    // then
    assertEquals("[lbl: div] store in register 1 the contents of register 2 divided by the contents of register 3", result);
  }
}