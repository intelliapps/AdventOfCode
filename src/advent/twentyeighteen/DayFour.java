package advent.twentyeighteen;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFour extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day Four - part 1: " + new DayFour(new File("input/2018/DayFour.txt")).partOne());
        System.out.println("Day Four - part 2: " + new DayFour(new File("input/2018/DayFour.txt")).partTwo());
    }

    private Map<Integer, Guard> guards = new HashMap<>();

    private DayFour(File inputFile) throws Exception
    {
        super(inputFile);
        populateGuardList();
    }

    DayFour(String inputText, String separator)
    {
        super(inputText, separator);
        populateGuardList();
    }

    int partOne()
    {
        Guard sleepiestGuard = guards.values().stream().max(
                Comparator.comparing(Guard::getMinutesAsleep)).orElse(new Guard(0));
        return sleepiestGuard.getId() * sleepiestGuard.getMinuteMostOftenAsleep();
    }

    int partTwo()
    {
        Guard mostFrequentlyAsleep = guards.values().stream().max(
                Comparator.comparing(Guard::getHighestMinuteCount)).orElse(new Guard(0));
        return mostFrequentlyAsleep.getId() * mostFrequentlyAsleep.getMinuteMostOftenAsleep();
    }

    private class Sleep
    {
        int start;
        int end;

        int getDuration() { return end-start; }
    }

    private class Guard
    {
        private int id;
        private Integer minutesAsleep = 0;
        private int[] minuteCounts = new int[60];
        private int highestMinuteCount = 0;
        private Integer minuteMostOftenAsleep = 0;

        Guard(int id)
        {
            this.id = id;
            Arrays.fill(minuteCounts, 0);
        }

        int getId() { return id; }
        int getMinutesAsleep() { return minutesAsleep; }
        int getHighestMinuteCount() { return highestMinuteCount; }
        int getMinuteMostOftenAsleep() { return minuteMostOftenAsleep; }

        void addSleep(Sleep sleep)
        {
            minutesAsleep += sleep.getDuration();

            for (int minute = sleep.start; minute < sleep.end; minute++)
            {
                minuteCounts[minute]++;

                if (highestMinuteCount < minuteCounts[minute])
                {
                    highestMinuteCount = minuteCounts[minute];
                    minuteMostOftenAsleep = minute;
                }
            }
        }
    }

    private void populateGuardList()
    {
        Arrays.sort(inputs);
        int inputIndex = 0, id;
        Guard guard;
        Sleep sleep;

        while(inputIndex < inputs.length)
        {
            Pattern pattern = Pattern.compile("\\[(.+)\\] Guard #(\\d+) begins shift");
            Matcher matcher = pattern.matcher(inputs[inputIndex++]);

            if (matcher.find())
            {
                id = Integer.parseInt(matcher.group(2));
                guard = guards.containsKey(id) ? guards.get(id) : new Guard(id);

                while (inputIndex < inputs.length
                        && inputs[inputIndex].charAt(19) != 'G')
                {
                    sleep = new Sleep();
                    sleep.start = Integer.parseInt(inputs[inputIndex++].substring(15, 17));
                    sleep.end = Integer.parseInt(inputs[inputIndex++].substring(15, 17));
                    guard.addSleep(sleep);
                }

                guards.put(id, guard);
            }
        }
    }
}
