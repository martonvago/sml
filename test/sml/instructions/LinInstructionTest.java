package sml.instructions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinInstructionTest {
  private Machine m;

  @BeforeEach
  public void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
  }

  @Test
  public void executeStoresIntegerCorrectly() {
    // given
    var instruction = new LinInstruction("lbl", 1, 2);

    // when
    instruction.execute(m);

    // then
    assertEquals(2, m.getRegisters().getRegister(1));
  }

  @Test
  public void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      var instruction = new LinInstruction("lbl", 32, 3);

      // when
      instruction.execute(m);
    });
  }

  @Test
  public void toStringReturnsCorrectString() {
    // given
    var instruction = new LinInstruction("lbl", 1, 2);

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: lin] store in register 1 the value 2", result);
  }
}
