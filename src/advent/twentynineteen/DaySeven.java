package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DaySeven extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DaySeven.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DaySeven daySeven1 = new DaySeven(inputText, ",");
        System.out.println("Day Seven - part 1: " + daySeven1.partOne());
        DaySeven daySeven2 = new DaySeven(inputText, ",");
        System.out.println("Day Seven - part 2: " + daySeven2.partTwo());
    }

    private int[] program;

    DaySeven(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne() throws Exception
    {
        return 0;
    }

    int partTwo()
    {
        return 0;
    }

    int tryPhaseSetting(int[] phaseSettings) throws Exception
    {
        BlockingQueue<Integer> ampInput, ampOutput = null;
        IntCodeComputer amp;

        for (int index = 0; index < 5; index++)
        {
            if (ampOutput == null)
            {
                ampInput = new LinkedBlockingQueue<>();
                ampInput.add(phaseSettings[0]);
                ampInput.add(0);
            }
            else
            {
                ampInput = ampOutput;
            }

            ampOutput = new LinkedBlockingQueue<>();
            if (index != 4) { ampOutput.add(phaseSettings[index+1]); }
            amp = new IntCodeComputer(program.clone());
            amp.runProgram(ampInput, ampOutput);
        }

        return ampOutput.take();
    }

    private void init()
    {
        program = new int[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Integer.parseInt(inputs[index]); }
    }
}
