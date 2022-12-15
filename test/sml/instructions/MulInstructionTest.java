package sml.instructions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MulInstructionTest {
  private Machine m;
  private Registers regs;

  @BeforeEach
  public void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
  }

  @Test
  public void executeMultipliesPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,6);
    var instruction = new MulInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(30, regs.getRegister(1));
  }

  @Test
  public void executeMultipliesPositiveAndNegativeNumberCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,6);
    var instruction = new MulInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(-30, regs.getRegister(1));
  }

  @Test
  public void executeMultipliesZerosCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,0);
    var instruction = new MulInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(0, regs.getRegister(1));
  }

  @Test
  public void executeMultipliesNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,-6);
    var instruction = new MulInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(30, regs.getRegister(1));
  }

  @Test
  public void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      var instruction = new MulInstruction("lbl", 1, 32, 3);

      // when
      instruction.execute(m);
    });
  }

  @Test
  public void toStringReturnsCorrectString() {
    // given
    var instruction = new MulInstruction("lbl", 1, 2, 3);

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: mul] store in register 1 the contents of register 2 multiplied by the contents of register 3", result);
  }
}
