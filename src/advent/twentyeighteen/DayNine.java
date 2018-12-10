package advent.twentyeighteen;

import java.util.*;

public class DayNine extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayNine dayNine1 = new DayNine();
        System.out.println("Day Nine - part 1: " + dayNine1.partOne(416, 71975));
        DayNine dayNine2 = new DayNine();
        System.out.println("Day Nine - part 2: " + dayNine2.partOne(416, 7197500));
    }

    DayNine()
    {
        super("", "");
    }

    int partOne(int numPlayers, int numMarbles)
    {
        Map<Integer, Integer> scores = new HashMap<>();
        for (int i = 0; i <= numPlayers; i++) { scores.put(i, 0); }
        List<Integer> marblesPlayed = new ArrayList<>();
        marblesPlayed.add(0);
        marblesPlayed.add(1);

        int playerIndex = 2, index23 = 2, currentMarbleIndex = 1, currentInsertionIndex = 1;

        for (int marbleNumber = 2; marbleNumber <= numMarbles; marbleNumber++)
        {
            if (index23 == 23)
            {
                System.out.println(marbleNumber);
                scores.put(playerIndex, scores.get(playerIndex) + marbleNumber);

                currentMarbleIndex = currentMarbleIndex - 7;
                if (currentMarbleIndex < 0) { currentMarbleIndex = marblesPlayed.size() + currentMarbleIndex; }
                scores.put(playerIndex, scores.get(playerIndex) + marblesPlayed.get(currentMarbleIndex));
                marblesPlayed.remove(currentMarbleIndex);
                if (currentMarbleIndex == marblesPlayed.size()) { currentMarbleIndex = 0; }

                if (currentMarbleIndex == marblesPlayed.size()-1)
                {
                    currentInsertionIndex = 1;
                }
                else
                {
                    currentInsertionIndex = currentMarbleIndex + 2;
                }

                index23 = 1;
            }
            else
            {
                currentMarbleIndex = currentInsertionIndex;

                if (currentInsertionIndex == marblesPlayed.size())
                {
                    marblesPlayed.add(marbleNumber);
                    currentInsertionIndex = 1;
                }
                else
                {
                    marblesPlayed.add(currentInsertionIndex, marbleNumber);
                    currentInsertionIndex += 2;
                }

                index23++;
            }

//            System.out.println("[" + playerIndex + "] (" + marblesPlayed.get(currentMarbleIndex) + ") " + marblesPlayed.toString());

            playerIndex = playerIndex < numPlayers ? playerIndex + 1 : 1;
        }

        return scores.values().stream().max(Comparator.comparing(Integer::intValue)).orElse(0);
    }

    int partTwo()
    {
        return 0;
    }
}
