package advent.twentyeighteen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DayFourteen
{
    public static void main(String[] args) throws Exception
    {
        DayFourteen dayFourteen1 = new DayFourteen(new int[] { 3, 7 });
        System.out.println("Day Fourteen - part 1: " + dayFourteen1.partOne(323081));
        DayFourteen dayFourteen2 = new DayFourteen(new int[] { 3, 7 });
        System.out.println("Day Fourteen - part 2: " + dayFourteen2.partTwo(new Integer[] { 3,2,3,0,8,1 }));
    }

    private List<Integer> recipes = new ArrayList<>();
    private int[] currentRecipes;
    private int numRecipesAdded = 2;

    DayFourteen(int[] initialScores)
    {
        this.currentRecipes = new int[initialScores.length];

        for(int i = 0; i < initialScores.length; i++)
        {
            recipes.add(initialScores[i]);
            currentRecipes[i] = i;
        }
    }

    String partOne(int numRecipes)
    {
        do { addRecipes(); }
        while (recipes.size() < numRecipes + 10);
        return recipes.subList(numRecipes, numRecipes+10).stream().map(s->""+s).collect(Collectors.joining());
    }

    int partTwo(Integer[] searchSequence)
    {
        int searchStart = - (searchSequence.length + numRecipesAdded);
        int index, matchIndex = -1;
        Integer[] sequenceToSearch;

        do
        {
            addRecipes();
            searchStart += numRecipesAdded;

            if (searchStart > 0)
            {
                sequenceToSearch = new Integer[recipes.size()-searchStart];
                index = 0;

                for (int recipeIndex = searchStart; recipeIndex < recipes.size(); recipeIndex++)
                {
                    sequenceToSearch[index++] = recipes.get(recipeIndex);
                }

                matchIndex = Collections.indexOfSubList(Arrays.asList(sequenceToSearch), Arrays.asList(searchSequence));
            }
        }
        while (matchIndex < 0);

        return searchStart + matchIndex;
    }

    private void addRecipes()
    {
        int currentSum = 0, shift;

        for(int recipeIndex: currentRecipes) { currentSum += recipes.get(recipeIndex); }
        for(char sumDigit: ("" + currentSum).toCharArray()) { recipes.add(Integer.parseInt("" + sumDigit)); }
        numRecipesAdded = ("" + currentSum).length();

        for (int elfIndex = 0; elfIndex < currentRecipes.length; elfIndex++)
        {
            shift = 1 + recipes.get(currentRecipes[elfIndex]);
            if (shift >= recipes.size()) { shift = shift % recipes.size(); }
            currentRecipes[elfIndex] += shift;

            if (currentRecipes[elfIndex] >= recipes.size())
            {
                currentRecipes[elfIndex] = Math.abs(recipes.size() - currentRecipes[elfIndex]);
            }
        }

        // System.out.println(recipes.stream().map(s->" "+s).collect(Collectors.joining()));
    }
}
