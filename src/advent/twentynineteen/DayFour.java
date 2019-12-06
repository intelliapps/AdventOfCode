package advent.twentynineteen;

import advent.twentyeighteen.AdventOfCode;

import java.io.File;

public class DayFour extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayFour dayXXX1 = new DayFour(new File("input/2018/DayXXX.txt"));
        System.out.println("Day XXX - part 1: " + dayXXX1.partOne());
        DayFour dayXXX2 = new DayFour(new File("input/2018/DayXXX.txt"));
        System.out.println("Day XXX - part 2: " + dayXXX2.partTwo());
    }

    private DayFour(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayFour(String inputText, String separator)
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
