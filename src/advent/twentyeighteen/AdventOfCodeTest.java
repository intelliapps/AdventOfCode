package advent.twentyeighteen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdventOfCodeTest
{
    @Test
    void testDayOnePartOneExamples()
    {
        assertEquals(3, new DayOne("+1,-2,+3,+1",",").partOne());
        assertEquals(3, new DayOne("+1,+1,+1",",").partOne());
        assertEquals(0, new DayOne("+1,+1,-2",",").partOne());
        assertEquals(-6, new DayOne("-1,-2,-3",",").partOne());
    }

    @Test
    void testDayOnePartTwoExamples()
    {
        assertEquals(2, new DayOne("+1,-2,+3,+1", ",").partTwo());
        assertEquals(0, new DayOne("+1,-1", ",").partTwo());
        assertEquals(10, new DayOne("+3,+3,+4,-2,-4", ",").partTwo());
        assertEquals(5, new DayOne("-6,+3,+8,+5,-6", ",").partTwo());
        assertEquals(14, new DayOne("+7,+7,-2,-7,-4", ",").partTwo());
    }

    @Test
    void testDayTwoPartOneExamples()
    {
        assertEquals(12, new DayTwo("abcdef,bababc,abbcde,abcccd,aabcdd,abcdee,ababab", ",").partOne());
    }

    @Test
    void testDatTwoPartTwoExamples()
    {
        assertEquals("fgij", new DayTwo("abcde,fghij,klmno,pqrst,fguij,axcye,wvxyz", ",").partTwo());
    }

    @Test
    void testDayThreePartOneExamples()
    {
        assertEquals(4, new DayThree("#1 @ 1,3: 4x4;#2 @ 3,1: 4x4;#3 @ 5,5: 2x2", ";").partOne());
    }

    @Test
    void testDayThreePartTwoExamples()
    {
        assertEquals("3", new DayThree("#1 @ 1,3: 4x4;#2 @ 3,1: 4x4;#3 @ 5,5: 2x2", ";").partTwo());
    }

    private static final String dayFourInputText =
            "[1518-11-01 00:00] Guard #10 begins shift," +
                    "[1518-11-01 00:05] falls asleep," +
                    "[1518-11-01 00:25] wakes up," +
                    "[1518-11-01 00:30] falls asleep," +
                    "[1518-11-01 00:55] wakes up," +
                    "[1518-11-01 23:58] Guard #99 begins shift," +
                    "[1518-11-02 00:40] falls asleep," +
                    "[1518-11-02 00:50] wakes up," +
                    "[1518-11-03 00:05] Guard #10 begins shift," +
                    "[1518-11-03 00:24] falls asleep," +
                    "[1518-11-03 00:29] wakes up," +
                    "[1518-11-04 00:02] Guard #99 begins shift," +
                    "[1518-11-04 00:36] falls asleep," +
                    "[1518-11-04 00:46] wakes up," +
                    "[1518-11-05 00:03] Guard #99 begins shift," +
                    "[1518-11-05 00:45] falls asleep," +
                    "[1518-11-05 00:55] wakes up";

    @Test
    void testDayFourPartOneExamples() throws Exception
    {
        assertEquals(240, new DayFour(dayFourInputText, ",").partOne());
    }

    @Test
    void testDayFourPartTwoExamples() throws Exception
    {
        assertEquals(4455, new DayFour(dayFourInputText, ",").partTwo());
    }

    @Test
    void testDayFivePartOneExamples()
    {
        assertEquals(0, new DayFive("aA", ",").partOne());
        assertEquals(0, new DayFive("abBA", ",").partOne());
        assertEquals(4, new DayFive("abAB", ",").partOne());
        assertEquals(6, new DayFive("aabAAB", ",").partOne());
        assertEquals(10, new DayFive("dabAcCaCBAcCcaDA", ",").partOne());
    }

    @Test
    void testDayFivePartTwoExamples()
    {
        assertEquals(4, new DayFive("dabAcCaCBAcCcaDA", ",").partTwo());
    }
}
