package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Registers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BnzInstructionTest {
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
  void executeDoesNotChangeInstructionOrderIfValueZeroAndLabelExists() {
    // given
    regs.setRegister(1,0);
    var targetLabel = "L3";
    var jumpInstruction = new BnzInstruction("L1", 1, targetLabel);
    var instructions = new ArrayList<>(Arrays.asList(
            jumpInstruction,
            new LinInstruction("L2", 2, 2),
            new LinInstruction(targetLabel, 2, 3)
    ));
    m.setProg(instructions);

    // when
    jumpInstruction.execute(m);

    // then
    assertEquals(0, m.getPc());
  }

  @Test
  void executeDoesNotChangeInstructionOrderIfValueZeroAndLabelDoesNotExist() {
    // given
    regs.setRegister(1,0);
    var targetLabel = "L3";
    var jumpInstruction = new BnzInstruction("L1", 1, targetLabel);
    var instructions = new ArrayList<>(Arrays.asList(
            jumpInstruction,
            new LinInstruction("L2", 2, 2)
    ));
    m.setProg(instructions);

    // when
    jumpInstruction.execute(m);

    // then
    assertEquals(0, m.getPc());
  }

  @Test
  void executeJumpsToGivenInstructionIfValueNotZero() {
    // given
    regs.setRegister(1,1);
    var targetLabel = "L3";
    var jumpInstruction = new BnzInstruction("L1", 1, targetLabel);
    var instructions = new ArrayList<>(Arrays.asList(
            jumpInstruction,
            new LinInstruction("L2", 2, 2),
            new LinInstruction(targetLabel, 2, 3)
    ));
    m.setProg(instructions);

    // when
    jumpInstruction.execute(m);

    // then
    assertEquals(2, m.getPc());
  }

  @Test
  void executeJumpsToFirstInstructionWithMatchingLabelIfValueNotZero() {
    // given
    regs.setRegister(1,1);
    var targetLabel = "L3";
    var jumpInstruction = new BnzInstruction("L1", 1, targetLabel);
    var instructions = new ArrayList<>(Arrays.asList(
            jumpInstruction,
            new LinInstruction("L2", 2, 2),
            new LinInstruction(targetLabel, 2, 3),
            new LinInstruction(targetLabel, 2, 4),
            new LinInstruction(targetLabel, 2, 5)
    ));
    m.setProg(instructions);

    // when
    jumpInstruction.execute(m);

    // then
    assertEquals(2, m.getPc());
  }

  @Test
  void executeThrowsIfRegisterDoesNotExist() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // given
      var instruction = new BnzInstruction("lbl", 32, "target");

      // when
      instruction.execute(m);
    });
  }

  @Test
  void executeThrowsIfGivenLabelDoesNotExistAndValueNotZero() {
    assertThrows(NoSuchElementException.class, () -> {
      // given
      regs.setRegister(1,1);
      var instruction = new BnzInstruction("lbl", 1, "target");

      // when
      instruction.execute(m);
    });
  }

  @Test
  void toStringReturnsCorrectString() {
    // given
    var instruction = new BnzInstruction("lbl", 1, "target");

    // when
    var result = instruction.toString();

    // then
    assertEquals("[lbl: bnz] jump to the instruction with label 'target' if the contents of register 1 are not zero", result);
  }

  @Test
  void bnzDoesNotChangeInstructionOrderIfValueZero_FullProgramRun() {
    // given
    var targetLabel = "L3";
    var instructions = new ArrayList<>(Arrays.asList(
            new BnzInstruction("L1", 1, targetLabel),
            new LinInstruction("L2", 2, 2),
            new AddInstruction(targetLabel, 3, 2, 2)
    ));
    m.setProg(instructions);

    // when
    m.execute();

    // then
    assertEquals(2, m.getRegisters().getRegister(2));
    assertEquals(4, m.getRegisters().getRegister(3));
    assertEquals(3, m.getPc());
  }

  @Test
  void bnzJumpsToGivenInstructionIfValueNotZero_FullProgramRun() {
    // given
    var targetLabel = "L3";
    var instructions = new ArrayList<>(Arrays.asList(
            new LinInstruction("L0", 1, 1),
            new BnzInstruction("L1", 1, targetLabel),
            new LinInstruction("L2", 2, 2),
            new AddInstruction(targetLabel, 3, 2, 2)
    ));
    m.setProg(instructions);

    // when
    m.execute();

    // then
    assertEquals(0, m.getRegisters().getRegister(2));
    assertEquals(0, m.getRegisters().getRegister(3));
    assertEquals(4, m.getPc());
  }
}
