package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DayNine extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayNine.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DayNine dayNine1 = new DayNine(inputText, ",");
        System.out.println("Day Nine - part 1: " + dayNine1.partOne()); // 3280416268
        DayNine dayNine2 = new DayNine(inputText, ",");
        System.out.println("Day Nine - part 2: " + dayNine2.partTwo()); // 80210
    }

    private long[] program;

    DayNine(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    Long[] test() throws Exception
    {
        IntCodeComputerDayNine icc = new IntCodeComputerDayNine("D9Test", program.clone());
        BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();
        icc.runProgram(inputs, outputs);
        return outputs.toArray(new Long[]{});
    }

    long partOne() throws Exception
    {
        IntCodeComputerDayNine icc = new IntCodeComputerDayNine("D9P1", program.clone());
        BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        inputs.put((long)1);
        BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();
        icc.runProgram(inputs, outputs);
        return outputs.remove();
    }

    long partTwo() throws Exception
    {
        IntCodeComputerDayNine icc = new IntCodeComputerDayNine("D9P1", program.clone());
        BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        inputs.put((long)2);
        BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();
        icc.runProgram(inputs, outputs);
        return outputs.remove();
    }

    private void init()
    {
        program = new long[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Long.parseLong(inputs[index]); }
    }
}
