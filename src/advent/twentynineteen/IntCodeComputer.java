package advent.twentynineteen;

public class IntCodeComputer
{
    private int[] program;
    private int instructionPointer;

    IntCodeComputer(String[] instructions)
    {
        program = new int[instructions.length];
        for (int index = 0; index < instructions.length; index++) { program[index] = Integer.parseInt(instructions[index]); }
        instructionPointer = 0;
    }

    int runProgram(int noun, int verb)
    {
        program[1] = noun;
        program[2] = verb;
        return runProgram();
    }

    int runProgram()
    {
        do { instructionPointer = execTwoOperandInstruction(); }
        while (program[instructionPointer] != 99);
        return program[0];
    }

    private int execTwoOperandInstruction()
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

        return instructionPointer + 4;
    }
}
