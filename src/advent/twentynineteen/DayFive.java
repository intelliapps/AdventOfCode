package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class DayFive extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayFive.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DayFive dayFive1 = new DayFive(inputText, ",");
        System.out.println("Day Five - part 1: " + dayFive1.partOne(1)); // 6745903
        DayFive dayFive2 = new DayFive(inputText, ",");
        System.out.println("Day Five - part 2: " + dayFive2.partTwo(5)); // 9168267
    }

    private int[] program;

    DayFive(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    private int partOne(int programInput) throws Exception
    {
        LinkedBlockingQueue<Integer> programInputs = new LinkedBlockingQueue<>();
        programInputs.add(programInput);
        LinkedBlockingQueue<Integer> programOutputs = new LinkedBlockingQueue<>();
        IntCodeComputer intCodeComputer = new IntCodeComputer(program);
        intCodeComputer.runProgram(programInputs, programOutputs);
        return programOutputs.size() > 0 ? Arrays.asList(programOutputs.toArray(new Integer[]{})).get(programOutputs.size()-1) : -1;
    }

    int partTwo(int programInput) throws Exception
    {
        LinkedBlockingQueue<Integer> programInputs = new LinkedBlockingQueue<>();
        programInputs.add(programInput);
        LinkedBlockingQueue<Integer> programOutputs = new LinkedBlockingQueue<>();
        IntCodeComputer intCodeComputer = new IntCodeComputer(program);
        intCodeComputer.runProgram(programInputs, programOutputs);
        return programOutputs.size() > 0 ? Arrays.asList(programOutputs.toArray(new Integer[]{})).get(programOutputs.size()-1) : -1;
    }

    private void init()
    {
        program = new int[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Integer.parseInt(inputs[index]); }
    }
}
