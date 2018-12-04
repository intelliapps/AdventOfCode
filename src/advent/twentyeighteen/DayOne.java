package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayOne extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day One - part 1: " + new DayOne(new File("input/2018/DayOne.txt")).partOne());
        System.out.println("Day One - part 2: " + new DayOne(new File("input/2018/DayOne.txt")).partTwo());
    }

    private Set<Integer> frequenciesHit = new HashSet<>();
    private Integer frequencyHitTwice = null;

    private DayOne(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayOne(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        frequenciesHit.add(0);
        return partOne(0);
    }

    private int partOne(int initialFrequency)
    {
        int frequency = initialFrequency;
        char op;
        int shift;

        for (String input: inputs)
        {
            op = input.charAt(0);
            shift = Integer.parseInt(input.substring(1));

            if (op == '+') { frequency += shift; }
            else /* op == '-' */ { frequency -= shift; }

            if (frequencyHitTwice == null && frequenciesHit.contains(frequency))
            {
                frequencyHitTwice = frequency;
            }

            frequenciesHit.add(frequency);
        }

        return frequency;
    }

    int partTwo()
    {
        frequenciesHit.add(0);
        int frequency = 0;

        while (frequencyHitTwice == null)
        {
            frequency = partOne(frequency);
        }

        return frequencyHitTwice;
    }
}
