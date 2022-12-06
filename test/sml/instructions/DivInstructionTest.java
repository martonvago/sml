package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivInstructionTest {
  private Machine m;
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
    regs = null;
  }

  @Test
  void executeDividesPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,4);
    regs.setRegister(3,2);
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(2, regs.getRegister(1));
  }

  @Test
  void executeDividesPositiveAndNegativeNumberCorrectly() {
    // given
    regs.setRegister(2,-4);
    regs.setRegister(3,2);
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(-2, regs.getRegister(1));
  }

  @Test
  void executeDividesZeroByNonZeroCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,4);
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(0, regs.getRegister(1));
  }

  @Test
  void executeDividesNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-4);
    regs.setRegister(3,-2);
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(2, regs.getRegister(1));
  }

  @Test
  void executeThrowsAwayRemainder() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,2);
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(2, regs.getRegister(1));
  }

  @Test
  void executeThrowsIfZeroDivision() {
    assertThrows(ArithmeticException.class, () -> {
      // given
      regs.setRegister(2,-6);
      regs.setRegister(3,0);
      var instruction = new DivInstruction("lbl", 1, 2, 3);

      // when
      instruction.execute(m);
    });
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      var instruction = new DivInstruction("lbl", 1, 32, 3);

      // when
      instruction.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    var instruction = new DivInstruction("lbl", 1, 2, 3);

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: div] store in register 1 the contents of register 2 divided by the contents of register 3", result);
  }
}
