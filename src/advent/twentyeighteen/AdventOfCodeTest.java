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
    void testDatThreePartTwoExamples()
    {
        assertEquals("3", new DayThree("#1 @ 1,3: 4x4;#2 @ 3,1: 4x4;#3 @ 5,5: 2x2", ";").partTwo());
    }
}
