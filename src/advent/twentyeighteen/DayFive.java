package advent.twentyeighteen;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DayFive extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day Five - part 1: " + new DayFive(new File("input/2018/DayFive.txt")).partOne());
        System.out.println("Day Five - part 2: " + new DayFive(new File("input/2018/DayFive.txt")).partTwo());
    }

    private DayFive(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayFive(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        return reducePolymer(getPolymer()).size();
    }

    private List<Character> getPolymer()
    {
        return inputs[0].chars().mapToObj(i -> (char) i).collect(Collectors.toList());
    }

    private List<Character> reducePolymer(List<Character> polymer)
    {
        int index = 0;

        while (index < polymer.size()-1)
        {
            if (doUnitsReact(polymer.get(index), polymer.get(index+1)))
            {
                polymer.remove(index);
                polymer.remove(index);
                if (index != 0) { index--; }
            }
            else
            {
                index++;
            }
        }

        return polymer;
    }

    private boolean doUnitsReact(char first, char second)
    {
        boolean sameChar = Character.toLowerCase(first) == Character.toLowerCase(second);
        boolean oppositeCase =
            ((Character.isLowerCase(first) && Character.isUpperCase(second))
                || (Character.isUpperCase(first) && Character.isLowerCase(second)));
        return sameChar && oppositeCase;
    }

    int partTwo()
    {
        List<Character> polymerLower = getPolymer().stream().map(Character::toLowerCase).collect(Collectors.toList());
        Set<Character> units = new HashSet<>();
        Collections.addAll(units, polymerLower.toArray(new Character[]{}));
        int shortestLength = polymerLower.size();
        List<Character> polymer;

        for (Character unit: units)
        {
            polymer = getPolymer();
            polymer = polymer.stream().filter(c -> Character.toLowerCase(c) != unit).collect(Collectors.toList());
            polymer = reducePolymer(polymer);
            shortestLength = Math.min(shortestLength, polymer.size());
        }

        return shortestLength;
    }
}
