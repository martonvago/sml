package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddInstructionTest {
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
  void executeAddsPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,6);
    i = new AddInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(11, m.getRegisters().getRegister(1));
  }

  @Test
  void executeAddsPositiveAndNegativeNumberCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,6);
    i = new AddInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(1, m.getRegisters().getRegister(1));
  }

  @Test
  void executeAddsZerosCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,0);
    i = new AddInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(0, m.getRegisters().getRegister(1));
  }

  @Test
  void executeAddsNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,-6);
    i = new AddInstruction("lbl", 1, 2, 3);

    // when
    i.execute(m);

    // then
    assertEquals(-11, m.getRegisters().getRegister(1));
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      i = new AddInstruction("lbl", 1, 32, 3);

      // when
      i.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    i = new AddInstruction("lbl", 1, 2, 3);

    // when
    var result = i.toString();

    // then
    assertEquals("[lbl: add] store in register 1 the contents of register 2 added to the contents of register 3", result);
  }
}
