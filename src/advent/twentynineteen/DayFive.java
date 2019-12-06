package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Scanner;

public class DayFive extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayFive.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DayFive dayFive1 = new DayFive(inputText, ",");
        System.out.println("Day Five - part 1: " + dayFive1.partOne(1));
        DayFive dayFive2 = new DayFive(inputText, ",");
        System.out.println("Day Five - part 2: " + dayFive2.partTwo(5));
    }

    DayFive(String inputText, String separator)
    {
        super(inputText, separator);
    }

    private int partOne(int input)
    {
        IntCodeComputer intCodeComputer = new IntCodeComputer(inputs, new int[] {input});
        intCodeComputer.runProgram();
        return intCodeComputer.getLastOutput();
    }

    int partTwo(int input)
    {
        IntCodeComputer intCodeComputer = new IntCodeComputer(inputs, new int[] {input});
        intCodeComputer.runProgram();
        return intCodeComputer.getLastOutput();
    }
}
