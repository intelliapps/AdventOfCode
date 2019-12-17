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

    DayTwo(String inputText, String separator)
    {
        super(inputText, separator);
    }

    long partOneTest() throws Exception
    {
        init();
        IntCodeComputer intCodeComputer = new IntCodeComputer(program);
        return intCodeComputer.runProgram();
    }

    private long partOne() throws Exception
    {
        init(12, 2);
        IntCodeComputer intCodeComputer = new IntCodeComputer(program);
        return intCodeComputer.runProgram();
    }

    private long partTwo(long expectedOutput) throws Exception
    {
        IntCodeComputer intCodeComputer;
        long programOutput;

        for (int noun = 0; noun < 100; noun++)
        {
            for (int verb = 0; verb < 100; verb++)
            {
                init(noun, verb);
                intCodeComputer = new IntCodeComputer(program);
                programOutput = intCodeComputer.runProgram();
                if (programOutput == expectedOutput) { return (100 * noun) + verb; }
            }
        }

        return 0;
    }

    private void init(int noun, int verb)
    {
        init();
        program[1] = noun;
        program[2] = verb;
    }

    private void init()
    {
        program = new int[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Integer.parseInt(inputs[index]); }
    }
}
