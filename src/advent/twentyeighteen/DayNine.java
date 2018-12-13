package advent.twentyeighteen;

import advent.CircularDeque;
import java.util.*;

public class DayNine extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayNine dayNine = new DayNine();
        System.out.println("Day Nine - part 1: " + dayNine.partOneTwo(416, 71975));
        System.out.println("Day Nine - part 2: " + dayNine.partOneTwo(416, 7197500));
    }

    DayNine()
    {
        super("", "");
    }

    long partOneTwo(int numPlayers, int numMarbles)
    {
        CircularDeque<Integer> marblesPlayed = new CircularDeque<>();
        marblesPlayed.addFirst(0);
        long[] scores = new long[numPlayers];

        for (int marbleNumber = 1; marbleNumber <= numMarbles; marbleNumber++)
        {
            if (marbleNumber % 23 == 0)
            {
                marblesPlayed.rotate(-7);
                scores[marbleNumber % numPlayers] += marbleNumber + marblesPlayed.pop();
            }
            else
            {
                marblesPlayed.rotate(2);
                marblesPlayed.addLast(marbleNumber);
            }
        }

        return Arrays.stream(scores).max().orElse(0);
    }
}
