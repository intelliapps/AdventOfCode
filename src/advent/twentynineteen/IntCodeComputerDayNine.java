package advent.twentynineteen;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IntCodeComputerDayNine
{
    private String name = "program";
    private long[] program;
    private int instructionPointer;
    private int relativeBase = 0;
    private boolean debug = false;

    IntCodeComputerDayNine(long[] program)
    {
        this.program = program;
    }

    IntCodeComputerDayNine(String name, long[] program)
    {
        this.name = name;
        this.program = Arrays.copyOf(program, 10000);
    }

    long runProgram() throws Exception
    {
        return runProgram(new LinkedBlockingQueue<>(), new LinkedBlockingQueue<>());
    }

    long runProgram(BlockingQueue<Long> programInputs, BlockingQueue<Long> programOutputs) throws Exception
    {
        instructionPointer = 0;
        int opCode;

        do
        {
            opCode = (int) program[instructionPointer] % 10;

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
                    if (debug) System.out.println(name + ": trying to take an input...");
                    instructionPointer = execSaveInstruction(programInputs.take());
                    break;
                case 4:
                    instructionPointer = execOutputInstruction(programOutputs);
                    break;
                case 9:
                    instructionPointer = execAdjustRelativeBaseInstruction();
                    break;
            }
        }
        while (program[instructionPointer] != 99);

        return program[0];
    }

    private int execTwoOperandInstruction()
    {
        String paramModes = String.format("%03d", program[instructionPointer] / 100);
        int opCode = (int) program[instructionPointer] % 10;
        int paramOneIndex = getParamIndexForMode(Character.getNumericValue(paramModes.charAt(2)), instructionPointer);
        int paramTwoIndex = getParamIndexForMode(Character.getNumericValue(paramModes.charAt(1)), instructionPointer+1);
        int resultIndex = getParamIndexForMode(Character.getNumericValue(paramModes.charAt(0)), instructionPointer+2);;

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
        instructionPointer = program[paramOneIndex] != 0 ? (int) program[paramTwoIndex] : instructionPointer + 3;
        if (debug) System.out.println(
                name + ": Jump if index " + paramOneIndex + " true (" + program[paramOneIndex] +  ")"
                + " to index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
        );
        return instructionPointer;
    }

    private int execJumpIfFalseInstruction(int paramOneIndex, int paramTwoIndex)
    {
        instructionPointer = program[paramOneIndex] == 0 ? (int) program[paramTwoIndex] : instructionPointer + 3;
        if (debug) System.out.println(
                name + ": Jump if index " + paramOneIndex + " false (" + program[paramOneIndex] +  ")"
                        + " to index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')'
        );
        return instructionPointer;
    }

    private int execAddInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        if (debug) System.out.print(name + ": Add index " + paramOneIndex + '(' + program[paramOneIndex] + ")");
        if (debug) System.out.print(" + index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')');
        program[resultIndex] = program[paramOneIndex] + program[paramTwoIndex];
        if (debug) System.out.println(" into index " + resultIndex + '(' + program[resultIndex] + ')');
        return instructionPointer + 4;
    }

    private int execMultiplyInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        if (debug) System.out.print(name + ": Multiply index " + paramOneIndex + '(' + program[paramOneIndex] + ")");
        if (debug) System.out.print(" * index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')');
        program[resultIndex] = program[paramOneIndex] * program[paramTwoIndex];
        if (debug) System.out.println(" into index " + resultIndex + '(' + program[resultIndex] + ')');
        return instructionPointer + 4;
    }

    private int execLessThanInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        if (debug) System.out.print(name + ": Evaluate index " + paramOneIndex + '(' + program[paramOneIndex] + ")");
        if (debug) System.out.print(" < index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')');
        program[resultIndex] = program[paramOneIndex] < program[paramTwoIndex] ? 1 : 0;
        if (debug) System.out.println(" into index " + resultIndex + '(' + program[resultIndex] + ')');
        return instructionPointer + 4;
    }

    private int execEqualsInstruction(int paramOneIndex, int paramTwoIndex, int resultIndex)
    {
        if (debug) System.out.print(name + ": Evaluate index " + paramOneIndex + '(' + program[paramOneIndex] + ")");
        if (debug) System.out.print(" == index " + paramTwoIndex + '(' + program[paramTwoIndex] + ')');
        program[resultIndex] = program[paramOneIndex] == program[paramTwoIndex] ? 1 : 0;
        if (debug) System.out.println(" into index " + resultIndex + '(' + program[resultIndex] + ')');
        return instructionPointer + 4;
    }

    private int execSaveInstruction(long input)
    {
        int paramMode = (int) program[instructionPointer] / 100;
        int paramIndex = getParamIndexForMode(paramMode, instructionPointer);
        program[paramIndex] = input;
        if (debug) System.out.println(name + ": Saved input " + input + " to index " + paramIndex);
        return instructionPointer + 2;
    }

    private int execOutputInstruction(BlockingQueue<Long> programOutputs) throws Exception
    {
        int paramMode = (int) program[instructionPointer] / 100;
        int paramIndex = getParamIndexForMode(paramMode, instructionPointer);
        if (debug) System.out.println(name + ": trying to put an output...");
        programOutputs.put(program[paramIndex]);
        if (debug) System.out.println(name + ": Output index " + paramIndex + ": " + program[paramIndex]);
        return instructionPointer + 2;
    }

    private int execAdjustRelativeBaseInstruction()
    {
        int paramMode = (int) program[instructionPointer] / 100;
        int paramIndex = getParamIndexForMode(paramMode, instructionPointer);
        if (debug) System.out.print(name + ": Adjusting relative base (" + relativeBase + ") by " + program[paramIndex]);
        relativeBase += program[paramIndex];
        if (debug) System.out.println(" to " + relativeBase);
        return instructionPointer + 2;
    }

    private int getParamIndexForMode(int paramMode, int instructionPointer)
    {
        switch(paramMode)
        {
            case 0: return (int) program[instructionPointer + 1];
            case 1: return instructionPointer + 1;
            case 2: return (int) program[instructionPointer + 1] + relativeBase;
        }

        return -1;
     }
}
