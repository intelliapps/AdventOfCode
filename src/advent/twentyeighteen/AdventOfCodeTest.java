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

    @Test
    void testDaySixPartOneExamples()
    {
        assertEquals(17, new DaySix("1, 1:1, 6:8, 3:3, 4:5, 5:8, 9", ":", null).partOne());
    }

    @Test
    void testDaySixPartTwoExamples()
    {
        assertEquals(16, new DaySix("1, 1:1, 6:8, 3:3, 4:5, 5:8, 9", ":", 32).partTwo());
    }

    private static final String daySevenExampleText =
            "Step C must be finished before step A can begin." +
            ":Step C must be finished before step F can begin." +
            ":Step A must be finished before step B can begin." +
            ":Step A must be finished before step D can begin." +
            ":Step B must be finished before step E can begin." +
            ":Step D must be finished before step E can begin." +
            ":Step F must be finished before step E can begin.";

    @Test
    void testDaySevenPartOneExamples()
    {
        assertEquals("CABDFE", new DaySeven(daySevenExampleText, ":").partOne());
    }

    @Test
    void testDaySevenPartTwoExamples()
    {
        assertEquals(15, new DaySeven(daySevenExampleText, ":", 60, 2).partTwo());
    }

    @Test
    void testDayEightPartOneExamples()
    {
        assertEquals(138, new DayEight("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", ":").partOne());
    }

    @Test
    void testDayEightPartTwoExamples()
    {
        assertEquals(66, new DayEight("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", ":").partTwo());
    }

    @Test
    void testDayNinePartOneExamples()
    {
        assertEquals(32, new DayNine().partOne(9, 25));
        assertEquals(8317, new DayNine().partOne(10, 1618));
        assertEquals(146373, new DayNine().partOne(13, 7999));
        assertEquals(2764, new DayNine().partOne(17, 1104));
        assertEquals(54718, new DayNine().partOne(21, 6111));
        assertEquals(37305, new DayNine().partOne(30, 5807));
    }

    private static final String dayTenExampleText =
            "position=< 9,  1> velocity=< 0,  2>" +
            ":position=< 7,  0> velocity=<-1,  0>" +
            ":position=< 3, -2> velocity=<-1,  1>" +
            ":position=< 6, 10> velocity=<-2, -1>" +
            ":position=< 2, -4> velocity=< 2,  2>" +
            ":position=<-6, 10> velocity=< 2, -2>" +
            ":position=< 1,  8> velocity=< 1, -1>" +
            ":position=< 1,  7> velocity=< 1,  0>" +
            ":position=<-3, 11> velocity=< 1, -2>" +
            ":position=< 7,  6> velocity=<-1, -1>" +
            ":position=<-2,  3> velocity=< 1,  0>" +
            ":position=<-4,  3> velocity=< 2,  0>" +
            ":position=<10, -3> velocity=<-1,  1>" +
            ":position=< 5, 11> velocity=< 1, -2>" +
            ":position=< 4,  7> velocity=< 0, -1>" +
            ":position=< 8, -2> velocity=< 0,  1>" +
            ":position=<15,  0> velocity=<-2,  0>" +
            ":position=< 1,  6> velocity=< 1,  0>" +
            ":position=< 8,  9> velocity=< 0, -1>" +
            ":position=< 3,  3> velocity=<-1,  1>" +
            ":position=< 0,  5> velocity=< 0, -1>" +
            ":position=<-2,  2> velocity=< 2,  0>" +
            ":position=< 5, -2> velocity=< 1,  2>" +
            ":position=< 1,  4> velocity=< 2,  1>" +
            ":position=<-2,  7> velocity=< 2, -2>" +
            ":position=< 3,  6> velocity=<-1, -1>" +
            ":position=< 5,  0> velocity=< 1,  0>" +
            ":position=<-6,  0> velocity=< 2,  0>" +
            ":position=< 5,  9> velocity=< 1, -2>" +
            ":position=<14,  7> velocity=<-2,  0>" +
            ":position=<-3,  6> velocity=< 2, -1>";

    @Test
    void testDayTenPartTwoExamples()
    {
        assertEquals(3, new DayTen(dayTenExampleText, ":").partOneTwo());
    }
}
