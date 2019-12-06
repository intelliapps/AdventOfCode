package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;

public class DayOne extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        long dayOneResult = new DayOne(new File("input/2019/DayOne.txt")).partOne();
        long dayTwoResult = new DayOne(new File("input/2019/DayOne.txt")).partTwo();

        System.out.println("Day One - part 1: " + dayOneResult);
        System.out.println("Day One - part 2: " + dayTwoResult);
    }

    private DayOne(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayOne(String inputText, String separator)
    {
        super(inputText, separator);
    }

    long partOne()
    {
        long totalFuelRequired = 0;
        for(String input: inputs) { totalFuelRequired += fuelForMass(Integer.parseInt(input)); }
        return totalFuelRequired;
    }

    private long fuelForMass(long mass)
    {
        return (long) Math.floor(mass / 3.0) - 2;
    }

    long partTwo()
    {
        long fuelForMass, fuelForFuel, totalFuelRequired = 0;
        for(String input: inputs)
        {
            fuelForMass = fuelForMass(Integer.parseInt(input));
            fuelForFuel = fuelForFuel(fuelForMass);
            totalFuelRequired += fuelForMass + fuelForFuel;
        }
        return totalFuelRequired;
    }

    private long fuelForFuel(long mass)
    {
        long fuelForFuel = (long) Math.floor(mass / 3.0) - 2;
        return fuelForFuel > 0 ? fuelForFuel + fuelForFuel(fuelForFuel) : 0;
    }
}
