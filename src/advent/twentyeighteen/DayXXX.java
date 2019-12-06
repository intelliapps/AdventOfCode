package advent.twentyeighteen;

import java.io.File;

public class DayXXX extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayXXX dayXXX1 = new DayXXX(new File("input/2018/DayXXX.txt"));
        System.out.println("Day XXX - part 1: " + dayXXX1.partOne());
        DayXXX dayXXX2 = new DayXXX(new File("input/2018/DayXXX.txt"));
        System.out.println("Day XXX - part 2: " + dayXXX2.partTwo());
    }

    private DayXXX(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayXXX(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne()
    {
        return 0;
    }

    int partTwo()
    {
        return 0;
    }

    private void init()
    {
    }
}
