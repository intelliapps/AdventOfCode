package advent.twentynineteen;

import java.util.ArrayList;
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

    int getLastOutput()
    {
        return outputs.size() > 0 ? outputs.get(outputs.size()-1) : -1;
    }

    int runProgram(int noun, int verb)
    {
        program[1] = noun;
        program[2] = verb;
        return runProgram();
    }

    int runProgram()
    {
        outputs = new ArrayList<>();
        instructionPointer = 0;
        int opCode;
        int inputNo = 0;

        do
        {
            opCode = program[instructionPointer] % 10;

            switch(opCode)
            {
                case 1:
                case 2:
                case 5:
                case 6:
                case 7:
                case 8:
                    instructionPointer = execTwoOperandInstruction();
                    break;
                case 3:
                    instructionPointer = execSaveInstruction(inputs[inputNo++]);
                    break;
                case 4:
                    instructionPointer = execOutputInstruction();
                    break;
            }
        }
        while (program[instructionPointer] != 99);

        return program[0];
    }

    private int execTwoOperandInstruction()
    {
        String paramModes = String.format("%03d", program[instructionPointer] / 100);
        int opCode = program[instructionPointer] % 10;
        int paramOneIndex = paramModes.charAt(2) == '0' ? program[instructionPointer+1] : instructionPointer+1;
        int paramTwoIndex = paramModes.charAt(1) == '0' ? program[instructionPointer+2] : instructionPointer+2;
        int resultIndex = program[instructionPointer+3]; // never immediate

        switch(opCode)
        {
            case 1: instructionPointer = execAddInstruction(paramOneIndex, paramTwoIndex, resultIndex); break;
            case 2: instructionPointer = execMultiplyInstruction(paramOneIndex, paramTwoIndex, resultIndex); break;
            case 5: instructionPointer = execJumpIfTrueInstruction(paramOneIndex, paramTwoIndex); break;
            case 6: instructionPointer = execJumpIfFalseInstruction(paramOneIndex, paramTwoIndex); break;
            case 7: instructionPointer = execLessThanInstruction(paramOneIndex, paramTwoIndex, resultIndex); break;
            case 8: instructionPointer = execEqualsInstruction(paramOneIndex, paramTwoIndex, resultIndex); break;
        }

        return instructionPointer;
    }

    private int execJumpIfTrueInstruction(int paramOneIndex, int paramTwoIndex)
    {
        instructionPointer = program[paramOneIndex] != 0 ? program[paramTwoIndex] : instructionPointer + 3;
        System.out.println(
                "Jump if index " + paramOneIndex + " true (" + program[paramOneIndex] +  ")"
                + " to index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
        );
        return instructionPointer;
    }

    private int execJumpIfFalseInstruction(int paramOneIndex, int paramTwoIndex)
    {
        instructionPointer = program[paramOneIndex] == 0 ? program[paramTwoIndex] : instructionPointer + 3;
        System.out.println(
                "Jump if index " + paramOneIndex + " false (" + program[paramOneIndex] +  ")"
                        + " to index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
        );
        return instructionPointer;
    }

    private int execAddInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        program[resultIndex] = program[paramOneIndex] + program[paramTwoIndex];
        System.out.println(
                "Add index " + paramOneIndex + '(' + program[paramOneIndex] + ")"
                        + " + index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
                        + " into index " + resultIndex + '(' + program[resultIndex] + ')'
        );
        return instructionPointer + 4;
    }

    private int execMultiplyInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        program[resultIndex] = program[paramOneIndex] * program[paramTwoIndex];
        System.out.println(
                "Multiply index " + paramOneIndex + '(' + program[paramOneIndex] + ")"
                        + " * index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
                        + " into index " + resultIndex + '(' + program[resultIndex] + ')'
        );
        return instructionPointer + 4;
    }

    private int execLessThanInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        program[resultIndex] = program[paramOneIndex] < program[paramTwoIndex] ? 1 : 0;
        System.out.println(
                "Evaluate index " + paramOneIndex + '(' + program[paramOneIndex] + ")"
                        + " < index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
                        + " into index " + resultIndex + '(' + program[resultIndex] + ')'
        );
        return instructionPointer + 4;
    }

    private int execEqualsInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        program[resultIndex] = program[paramOneIndex] == program[paramTwoIndex] ? 1 : 0;
        System.out.println(
                "Evaluate index " + paramOneIndex + '(' + program[paramOneIndex] + ")"
                        + " == index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
                        + " into index " + resultIndex + '(' + program[resultIndex] + ')'
        );
        return instructionPointer + 4;
    }

    private int execSaveInstruction(int input)
    {
        int paramIndex = program[instructionPointer+1];
        program[paramIndex] = input;
        System.out.println("Saved input " + input + " to index " + paramIndex);
        return instructionPointer + 2;
    }

    private int execOutputInstruction()
    {
        int paramMode = program[instructionPointer] / 100;
        int paramIndex = paramMode == 0 ? program[instructionPointer+1] : instructionPointer+1;
        outputs.add(program[paramIndex]);
        System.out.println("Output index " + paramIndex + ": " + program[paramIndex]);
        return instructionPointer + 2;
    }
}
