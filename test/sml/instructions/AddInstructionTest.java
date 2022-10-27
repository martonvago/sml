package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

class AddInstructionTest {
  private Machine m;
  private Instruction i;
  private Registers regs;

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    regs = m.getRegisters();
    //...
  }

  @AfterEach
  void tearDown() {
    m = null;
    i = null;
    regs = null;
  }

  @Test
  void executeValid() {
    regs.setRegister(2,5);
    regs.setRegister(3,6);
    i = new AddInstruction("lbl", 1, 2, 3);
    i.execute(m);
    Assertions.assertEquals(11,m.getRegisters().getRegister(1));
  }

  @Test
  void executeValidTwo() {
    regs.setRegister(2,-5);
    regs.setRegister(3,6);
    i = new AddInstruction("lbl", 1, 2, 3);
    i.execute(m);
    Assertions.assertEquals(1,m.getRegisters().getRegister(1));
  }
}