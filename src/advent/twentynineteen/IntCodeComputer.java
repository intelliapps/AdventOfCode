package advent.twentynineteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntCodeComputer
{
    private int[] program;
    private int[] inputs;
    private List<Integer> outputs;
    private int instructionPointer;

    IntCodeComputer(String[] instructions)
    {
        this(instructions, new int[]{});
    }

    IntCodeComputer(String[] instructions, int[] inputs)
    {
        program = new int[instructions.length];
        for (int index = 0; index < instructions.length; index++) { program[index] = Integer.parseInt(instructions[index]); }
        this.inputs = inputs;
    }

    int runProgram(int noun, int verb)
    {
        program[1] = noun;
        program[2] = verb;
        return runProgram();
    }

    int runProgram()
    {
        return runProgram(new int[]{});
    }

    private int runProgram(int[] inputs)
    {
        outputs = new ArrayList<>();
        instructionPointer = 0;
        int opCode;
        int inputNo = 0;

        do
        {
            opCode = program[instructionPointer];

            switch(opCode)
            {
                case 1: case 2:
                    execTwoOperandInstruction();
                    instructionPointer += 4;
                    break;
                case 3:
                    execSaveInstruction(inputs[inputNo++]);
                    instructionPointer += 2;
                    break;
                case 4:
                    execOutputInstruction();
                    instructionPointer += 2;
                    break;
            }
        }
        while (program[instructionPointer] != 99);

        return program[0];
    }

    private void execTwoOperandInstruction()
    {
        int opCode = program[instructionPointer];
        int paramOneIndex = program[instructionPointer+1];
        int paramTwoIndex = program[instructionPointer+2];
        int resultIndex = program[instructionPointer+3];

        switch(opCode)
        {
            case 1: program[resultIndex] = program[paramOneIndex] + program[paramTwoIndex]; break;
            case 2: program[resultIndex] = program[paramOneIndex] * program[paramTwoIndex]; break;
        }
    }

    private void execSaveInstruction(int input)
    {
        int paramIndex = program[instructionPointer+1];
        program[paramIndex] = input;
    }

    private void execOutputInstruction()
    {
        int paramIndex = program[instructionPointer+1];
        outputs.add(program[paramIndex]);
    }
}
