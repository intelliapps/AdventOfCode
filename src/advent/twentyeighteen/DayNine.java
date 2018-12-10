package advent.twentyeighteen;

import java.util.*;

public class DayNine extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayNine dayNine1 = new DayNine();
        System.out.println("Day Nine - part 1: " + dayNine1.partOne(416, 71975));
        DayNine dayNine2 = new DayNine();
        System.out.println("Day Nine - part 2: " + dayNine2.partOne(416, 71975 * 100));
    }

    DayNine()
    {
        super("", "");
    }

    int partOne(int numPlayers, int numMarbles)
    {
        List<Integer> marblesPlayed = new ArrayList<>();
        marblesPlayed.add(0);
        Map<Integer, Integer> scores = new HashMap<>();
        for (int i = 0; i <= numPlayers; i++) { scores.put(i, 0); }
        int playerIndex = 1, currentMarbleIndex = 0;

        for (int marbleIndex = 1; marbleIndex <= numMarbles; marbleIndex++)
        {
            if (marbleIndex % 23 == 0)
            {
                scores.put(playerIndex, scores.get(playerIndex) + marbleIndex);
                currentMarbleIndex = currentMarbleIndex - 7;
                if (currentMarbleIndex < 0) { currentMarbleIndex = marblesPlayed.size() + currentMarbleIndex; }
                scores.put(playerIndex, scores.get(playerIndex) + marblesPlayed.get(currentMarbleIndex));
                marblesPlayed.remove(currentMarbleIndex);
                if (currentMarbleIndex == marblesPlayed.size()) { currentMarbleIndex = 0; }
            }
            else
            {
                if (currentMarbleIndex == marblesPlayed.size()-2) { currentMarbleIndex = marblesPlayed.size(); }
                else if (currentMarbleIndex == marblesPlayed.size()-1) { currentMarbleIndex = 1; }
                else { currentMarbleIndex = currentMarbleIndex + 2; }

                if (currentMarbleIndex == marblesPlayed.size()) { marblesPlayed.add(marbleIndex); }
                else { marblesPlayed.add(currentMarbleIndex, marbleIndex); }
            }

            //System.out.println("[" + playerIndex + "] (" + marblesPlayed.get(currentMarbleIndex) + ") " + marblesPlayed.toString());

            playerIndex = playerIndex < numPlayers ? playerIndex+1 : 1;
        }

        return scores.values().stream().max(Comparator.comparing(Integer::intValue)).orElse(0);
    }

    int partTwo()
    {
        return 0;
    }
}
