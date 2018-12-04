package advent.twentyeighteen;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThree extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day Three - part 1: " + new DayThree(new File("input/2018/DayThree.txt")).partOne());
        System.out.println("Day Three - part 2: " + new DayThree(new File("input/2018/DayThree.txt")).partTwo());
    }

    private DayThree(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayThree(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        return calculate().overlapCount;
    }

    String partTwo()
    {
        return calculate().idNoOverlap;
    }

    private class DayThreeResult
    {
        int overlapCount = 0;
        String idNoOverlap = "";
    }

    private DayThreeResult calculate()
    {
        String id;
        int leftOffset, topOffset, width, height;
        Set<String> idsWithoutOverlap = new HashSet<>();

        String[][] fabric = new String[1000][1000];
        for (int index = 0; index < 1000; index++) { Arrays.fill(fabric[index], ""); }

        DayThreeResult result = new DayThreeResult();

        for (String input: inputs)
        {
            Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find())
            {
                id = (matcher.group(1));
                leftOffset = Integer.parseInt(matcher.group(2));
                topOffset = Integer.parseInt(matcher.group(3));
                width = Integer.parseInt(matcher.group(4));
                height = Integer.parseInt(matcher.group(5));

                idsWithoutOverlap.add(id);

                for(int column = leftOffset; column < leftOffset + width; column++)
                {
                    for(int row = topOffset; row < topOffset + height; row++)
                    {
                        if (fabric[row][column].isEmpty())
                        {
                            fabric[row][column] = id;
                        }
                        else
                        {
                            if (!fabric[row][column].equals("X"))
                            {
                                idsWithoutOverlap.remove(fabric[row][column]);
                                result.overlapCount++;
                                fabric[row][column] = "X";
                            }

                            idsWithoutOverlap.remove(id);
                        }
                    }
                }
            }
        }

        result.idNoOverlap = idsWithoutOverlap.iterator().next();

        return result;
    }
}
