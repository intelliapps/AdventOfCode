package advent.twentynineteen;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    void testDayTwoPartOneExamples() throws Exception
    {
        assertEquals(2, new DayTwo("1,0,0,0,99", ",").partOneTest());
        assertEquals(2, new DayTwo("2,3,0,3,99", ",").partOneTest());
        assertEquals(2, new DayTwo("2,4,4,5,99,0", ",").partOneTest());
        assertEquals(30, new DayTwo("1,1,1,4,99,5,6,0,99", ",").partOneTest());
    }

    @Test
    void testDayThreePartOneExamples() throws Exception
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

    @Test
    void testDayFivePartTwoExamples() throws Exception
    {
        assertEquals(0, new DayFive("3,9,8,9,10,9,4,9,99,-1,8", ",").partTwo(7)); // input == 8, true
        assertEquals(1, new DayFive("3,9,8,9,10,9,4,9,99,-1,8", ",").partTwo(8)); // input == 8, false
        assertEquals(0, new DayFive("3,9,7,9,10,9,4,9,99,-1,8", ",").partTwo(9)); // input < 8, false
        assertEquals(0, new DayFive("3,9,7,9,10,9,4,9,99,-1,8", ",").partTwo(8)); // input < 8, false
        assertEquals(1, new DayFive("3,9,7,9,10,9,4,9,99,-1,8", ",").partTwo(7)); // input < 8, true
        assertEquals(0, new DayFive("3,3,1108,-1,8,3,4,3,99", ",").partTwo(7)); // input == 8, true
        assertEquals(1, new DayFive("3,3,1108,-1,8,3,4,3,99", ",").partTwo(8)); // input == 8, false
        assertEquals(0, new DayFive("3,3,1107,-1,8,3,4,3,99", ",").partTwo(9)); // input < 8, false
        assertEquals(0, new DayFive("3,3,1107,-1,8,3,4,3,99", ",").partTwo(8)); // input < 8, false
        assertEquals(1, new DayFive("3,3,1107,-1,8,3,4,3,99", ",").partTwo(7)); // input < 8, true
        assertEquals(0, new DayFive("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", ",").partTwo(0));
        assertEquals(1, new DayFive("3,12,6,12,15,1,13,14,13,4,13,99,7,0,1,9", ",").partTwo(7));
        assertEquals(0, new DayFive("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", ",").partTwo(0));
        assertEquals(1, new DayFive("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", ",").partTwo(7));
        assertEquals(999, new DayFive("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", ",").partTwo(7));
        assertEquals(1000, new DayFive("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", ",").partTwo(8));
        assertEquals(1001, new DayFive("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", ",").partTwo(9));
    }

    @Test
    void testDaySixPartOneExamples()
    {
        assertEquals(42, new DaySix("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L", ",").partOne());
    }

    @Test
    void testDaySixPartTwoExamples()
    {
        assertEquals(4, new DaySix("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L,K)YOU,I)SAN", ",").partTwo());
    }

    @Test
    void testDaySevenTryPhaseSetting() throws Exception
    {
        assertEquals(43210, new DaySeven("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0", ",").tryPhaseSetting(new int[]{4,3,2,1,0}));
        assertEquals(54321, new DaySeven("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0", ",").tryPhaseSetting(new int[]{0,1,2,3,4}));
        assertEquals(65210, new DaySeven("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0", ",").tryPhaseSetting(new int[]{1,0,4,3,2}));
    }

    @Test
    void testDaySevenPartTwo() throws Exception
    {
        assertEquals(139629729, new DaySeven("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5", ",").partTwo());
        assertEquals(18216, new DaySeven("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10", ",").partTwo());
    }

    @Test
    void testDayEightGenerateLayers()
    {
        assertEquals(Arrays.deepToString(new int[][][] {{{1,2,3},{4,5,6}},{{7,8,9},{0,1,2}}}), Arrays.deepToString(new DayEight("123456789012", ":", 3, 2).getLayers()));
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
