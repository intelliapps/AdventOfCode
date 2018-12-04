package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayTwo extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day Two - part 1: " + new DayTwo(new File("input/2018/DayTwo.txt")).partOne());
        System.out.println("Day Two - part 2: " + new DayTwo(new File("input/2018/DayTwo.txt")).partTwo());
    }

    private DayTwo(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayTwo(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        int numIDsWithTwoLetters = 0;
        int numIDsWithThreeLetters = 0;

        for(String input: inputs)
        {
            if (textContainsLetterCount(input, 2)) { numIDsWithTwoLetters++; }
            if (textContainsLetterCount(input, 3)) { numIDsWithThreeLetters++; }
        }

        return numIDsWithTwoLetters * numIDsWithThreeLetters;
    }

    String partTwo()
    {
        int textLength = inputs[0].length();
        Set<String> transformedInputs;
        String transformedInput;

        for (int textIndex = 0; textIndex < textLength; textIndex++)
        {
            transformedInputs = new HashSet<>();

            for (String input: inputs)
            {
                transformedInput = input.substring(0, textIndex) + input.substring(textIndex+1);
                if (transformedInputs.contains(transformedInput)) { return transformedInput; }
                else { transformedInputs.add(transformedInput); }
            }
        }

        return "";
    }

    private boolean textContainsLetterCount(String text, int count)
    {
        Map<Character, Integer> letterCounts = getLetterCounts(text);
        return letterCounts.values().contains(count);
    }

    private Map<Character, Integer> getLetterCounts(String text)
    {
        Map<Character, Integer> letterCounts = new HashMap<>();
        Character letter;

        for (int index = 0; index < text.length(); index++)
        {
            letter = text.charAt(index);
            if (!letterCounts.containsKey(letter)) { letterCounts.put(letter, 0); }
            letterCounts.put(letter, letterCounts.get(letter)+1);
        }

        return letterCounts;
    }
}
