package advent.twentynineteen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeTest
{
    @Test
    void testDayOnePartOneExamples()
    {
        assertEquals(2, new DayOne("12", ":").partOne());
        assertEquals(2, new DayOne("14", ":").partOne());
        assertEquals(654, new DayOne("1969", ":").partOne());
        assertEquals(33583, new DayOne("100756", ":").partOne());
    }

    @Test
    void testDayOnePartTwoExamples()
    {
        assertEquals(2, new DayOne("14", ":").partTwo());
        assertEquals(966, new DayOne("1969", ":").partTwo());
        assertEquals(50346, new DayOne("100756", ":").partTwo());
    }

    @Test
    void testDayTwoPartOneExamples()
    {
        assertEquals(2, new DayTwo("1,0,0,0,99", ",").partOneTest());
        assertEquals(2, new DayTwo("2,3,0,3,99", ",").partOneTest());
        assertEquals(2, new DayTwo("2,4,4,5,99,0", ",").partOneTest());
        assertEquals(30, new DayTwo("1,1,1,4,99,5,6,0,99", ",").partOneTest());
    }

    @Test
    void testDayThreePartOneExamples()
    {
        assertEquals(6, new DayThree("R8,U5,L5,D3:U7,R6,D4,L4", ":").partOne());
        assertEquals(159, new DayThree("R75,D30,R83,U83,L12,D49,R71,U7,L72:U62,R66,U55,R34,D71,R55,D58,R83", ":").partOne());
        assertEquals(135, new DayThree("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51:U98,R91,D20,R16,D67,R40,U7,R15,U6,R7", ":").partOne());
    }

    @Test
    void testDayThreePartTwoExamples()
    {
        assertEquals(30, new DayThree("R8,U5,L5,D3:U7,R6,D4,L4", ":").partTwo());
    }

    @Test
    void testDayFourHasRepeatingDigits()
    {
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(284639, false));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(748759, false));
        assertEquals(true, new DayFour("284639-748759").hasRepeatingDigits(284644, false));
    }

    @Test
    void testDayFourHasOnlyTwoRepeatingDigits()
    {
        assertEquals(true, new DayFour("284639-748759").hasRepeatingDigits(334444, true));
        assertEquals(true, new DayFour("284639-748759").hasRepeatingDigits(284644, true));
        assertEquals(true, new DayFour("284639-748759").hasRepeatingDigits(331234, true));
        assertEquals(true, new DayFour("284639-748759").hasRepeatingDigits(356634, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(333234, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(344434, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(345554, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(333331, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(344444, true));
        assertEquals(false, new DayFour("284639-748759").hasRepeatingDigits(333333, true));
    }

    @Test
    void testDayFourHasEscalatingDigits()
    {
        assertEquals(false, new DayFour("284639-748759").hasEscalatingDigits(284639));
        assertEquals(false, new DayFour("284639-748759").hasEscalatingDigits(748759));
        assertEquals(true, new DayFour("284639-748759").hasEscalatingDigits(288999));
        assertEquals(true, new DayFour("284639-748759").hasEscalatingDigits(333333));
    }

    /*
    @Test
    void testDayXXXPartOneExamples()
    {
        assertEquals(0, new DayXXX("", ":").partOne());
    }

    @Test
    void testDayXXXPartTwoExamples()
    {
        assertEquals(0, new DayXXX("", ":").partTwo());
    }
 */
}
