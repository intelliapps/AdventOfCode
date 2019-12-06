package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Scanner;

public class DayTwo extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayTwo.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        long dayOneResult = new DayTwo(inputText, ",").partOne();
        long dayTwoResult = new DayTwo(inputText, ",").partTwo(19690720);

        System.out.println("Day Two - part 1: " + dayOneResult);
        System.out.println("Day Two - part 2: " + dayTwoResult);
    }

    private int[] program;
    private int instructionPointer;

    DayTwo(String inputText, String separator)
    {
        super(inputText, separator);
    }

    private void init()
    {
        program = new int[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Integer.parseInt(inputs[index]); }
        instructionPointer = 0;
    }

    long partOneTest()
    {
        init();
        return runProgram();
    }

    private long partOne()
    {
        init();
        return runProgram(12, 2);
    }

    private long partTwo(long expectedOutput)
    {
        long programOutput;

        for (int noun = 0; noun < 100; noun++)
        {
            for (int verb = 0; verb < 100; verb++)
            {
                init();
                programOutput = runProgram(noun, verb);
                if (programOutput == expectedOutput) { return (100 * noun) + verb; }
            }
        }

        return 0;
    }

    private int runProgram(int noun, int verb)
    {
        program[1] = noun;
        program[2] = verb;
        return runProgram();
    }

    private int runProgram()
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
