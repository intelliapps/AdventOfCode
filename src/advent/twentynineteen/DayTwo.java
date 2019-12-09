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

        long dayOneResult = new DayTwo(inputText, ",").partOne(); // 3224742
        long dayTwoResult = new DayTwo(inputText, ",").partTwo(19690720); // 7960

        System.out.println("Day Two - part 1: " + dayOneResult);
        System.out.println("Day Two - part 2: " + dayTwoResult);
    }

    private int[] program;
    private int instructionPointer;

    DayTwo(String inputText, String separator)
    {
        super(inputText, separator);
    }

    long partOneTest() throws Exception
    {
        IntCodeComputer intCodeComputer = new IntCodeComputer(inputs);
        return intCodeComputer.runProgram();
    }

    private long partOne() throws Exception
    {
        IntCodeComputer intCodeComputer = new IntCodeComputer(inputs);
        return intCodeComputer.runProgram(12, 2);
    }

    private long partTwo(long expectedOutput) throws Exception
    {
        IntCodeComputer intCodeComputer;
        long programOutput;

        for (int noun = 0; noun < 100; noun++)
        {
            for (int verb = 0; verb < 100; verb++)
            {
                intCodeComputer = new IntCodeComputer(inputs);
                programOutput = intCodeComputer.runProgram(noun, verb);
                if (programOutput == expectedOutput) { return (100 * noun) + verb; }
            }
        }

        return 0;
    }
}
