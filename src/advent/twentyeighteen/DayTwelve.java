package advent.twentyeighteen;

import java.io.File;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTwelve extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayTwelve dayTwelve1 = new DayTwelve(new File("input/2018/DayTwelve.txt"), false);
        System.out.println("Day Twelve - part 1: " + dayTwelve1.partOne());
        DayTwelve dayTwelve2 = new DayTwelve(new File("input/2018/DayTwelve.txt"), false);
        System.out.println("Day Twelve - part 2: " + dayTwelve2.partTwo());
    }

    private boolean isExample;
    private String initialState;
    private List<Rule> rules = new ArrayList<>();
    private int zeroIndex = 0;

    private DayTwelve(File inputFile, boolean isExample) throws Exception
    {
        super(inputFile);
        this.isExample = isExample;
        init();
    }

    DayTwelve(String inputText, String separator, boolean isExample)
    {
        super(inputText, separator);
        this.isExample = isExample;
        init();
    }

    private void init()
    {
        initialState = inputs[0].substring(15);
        Rule rule;

        for(int i = 2; i < inputs.length; i++)
        {
            rule = new Rule();
            rule.condition = inputs[i].substring(0, 5);
            rule.conditionPattern = "(?=(" + rule.condition.replace(".", "\\.") + ")).";
            rule.result = inputs[i].charAt(9);
            if (isExample || rule.condition.charAt(2) != rule.result) { rules.add(rule); }
        }
    }

    long partOne()
    {
        return calculate(20);
    }

    long partTwo()
    {
        return calculate(Long.parseLong("50000000000"));
    }

    private long calculate(long numGens)
    {
        String currentState = initialState, lastState = "";
        int lastZeroIndex = 0;
        long genNum;

        for(genNum = 1; genNum <= numGens; genNum++)
        {
            lastState = currentState;
            lastZeroIndex = zeroIndex;
            currentState = getNextState(currentState);
            if (currentState.equals(lastState)) break;
        }

        long total = 0;

        for(int potNum = -zeroIndex; potNum < currentState.length()-zeroIndex; potNum++)
        {
            if (currentState.charAt(potNum + zeroIndex) == '#') { total += potNum; }
        }

        if (numGens > genNum)
        {
            long potCount = currentState.chars().filter(ch -> ch == '#').count();
            total += potCount * (lastZeroIndex - zeroIndex) * (numGens - genNum);
        }

        return total;
    }

    private String getNextState(String currentState)
    {
        currentState = "....." + currentState + ".....";
        char[] nextState = currentState.toCharArray();
        zeroIndex += 5;

        if (isExample)
        {
            nextState = new char[currentState.length()];
            Arrays.fill(nextState, '.');
        }

        Pattern pattern;
        Matcher matcher;

        for(Rule rule: rules)
        {
            pattern = Pattern.compile(rule.conditionPattern);
            matcher = pattern.matcher(currentState);

            while (matcher.find())
            {
                nextState[matcher.toMatchResult().start()+2] = rule.result;
            }
        }

        return trimState(new String(nextState));
    }

    private String trimState(String state)
    {
        int start = 0, end = state.length();
        while (state.charAt(start) == '.') { start++; }
        while (state.charAt(end-1) == '.') { end--; }
        zeroIndex -= start;
        return state.substring(start, end);
    }

    private class Rule
    {
        String condition;
        String conditionPattern;
        char result;
    }
}
