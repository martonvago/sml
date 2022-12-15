package sml.instructions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddInstructionTest {
  private Machine m;
  private Registers regs;

  @BeforeEach
  public void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
  }

  @Test
  public void executeAddsPositiveNumbersCorrectly() {
    // given
    regs.setRegister(2,5);
    regs.setRegister(3,6);
    var instruction = new AddInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(11, regs.getRegister(1));
  }

  @Test
  public void executeAddsPositiveAndNegativeNumberCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,6);
    var instruction = new AddInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(1, regs.getRegister(1));
  }

  @Test
  public void executeAddsZerosCorrectly() {
    // given
    regs.setRegister(2,0);
    regs.setRegister(3,0);
    var instruction = new AddInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(0, regs.getRegister(1));
  }

  @Test
  public void executeAddsNegativeNumbersCorrectly() {
    // given
    regs.setRegister(2,-5);
    regs.setRegister(3,-6);
    var instruction = new AddInstruction("lbl", 1, 2, 3);

    // when
    instruction.execute(m);

    // then
    assertEquals(-11, regs.getRegister(1));
  }

  @Test
  public void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      regs.setRegister(3,-6);
      var instruction = new AddInstruction("lbl", 1, 32, 3);

      // when
      instruction.execute(m);
    });
  }

  @Test
  public void toStringReturnsCorrectString() {
    // given
    var instruction = new AddInstruction("lbl", 1, 2, 3);

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: add] store in register 1 the contents of register 2 added to the contents of register 3", result);
  }
}
