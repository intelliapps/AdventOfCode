package advent.twentynineteen;

import advent.AdventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DayFour extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayFour dayFour1 = new DayFour("284639-748759");
        System.out.println("Day Four - part 1: " + dayFour1.partOne()); // 895
        DayFour dayFour2 = new DayFour("284639-748759");
        System.out.println("Day Four - part 2: " + dayFour2.partTwo());
    }

    private int rangeMin;
    private int rangeMax;

    DayFour(String input)
    {
        super(input, "-");
        rangeMin = Integer.parseInt(inputs[0]);
        rangeMax = Integer.parseInt(inputs[1]);
    }

    private int partOne()
    {
        int numMeetingCriteria = 0;

        for (int candidate = rangeMin; candidate <= rangeMax; candidate++)
        {
            if (hasRepeatingDigits(candidate, false) && hasEscalatingDigits(candidate)) { numMeetingCriteria++; }
        }

        return numMeetingCriteria;
    }

    private int partTwo()
    {
        int numMeetingCriteria = 0;

        for (int candidate = rangeMin; candidate <= rangeMax; candidate++)
        {
            if (hasRepeatingDigits(candidate, true) && hasEscalatingDigits(candidate)) { numMeetingCriteria++; }
        }

        return numMeetingCriteria;
    }

    boolean hasRepeatingDigits(int value, boolean onlyTwo)
    {
        Pattern pattern = Pattern.compile("(\\d)(\\1)+");
        Matcher matcher = pattern.matcher("" + value);
        boolean hasRepeatingDigits = false;
        int numRepeatingDigits;

        while (matcher.find())
        {
            numRepeatingDigits = matcher.end() - matcher.start();
            hasRepeatingDigits = onlyTwo ? numRepeatingDigits == 2 : numRepeatingDigits >= 2;
            if (hasRepeatingDigits) break;
        }

        return hasRepeatingDigits;
    }

    boolean hasEscalatingDigits(int value)
    {
        String valueText = "" + value;
        boolean hasEscalatingDigits = true;
        int charCodeOne, charCodeTwo;

        for (int index = 0; index < valueText.length()-1; index++)
        {
            charCodeOne = valueText.charAt(index);
            charCodeTwo = valueText.charAt(index + 1);
            hasEscalatingDigits = charCodeOne <= charCodeTwo;
            if (!hasEscalatingDigits) break;
        }

        return hasEscalatingDigits;
    }
}
