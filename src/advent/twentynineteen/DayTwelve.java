package advent.twentynineteen;

import advent.AdventOfCode;
import advent.Point3D;

public class DayTwelve extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        int[][] initialState = {{5,13,-3},{18,-7,13},{16,3,4},{0,8,8}};

        DayTwelve dayTwelve1 = new DayTwelve(initialState);
        System.out.println("Day Twelve - part 1: " + dayTwelve1.partOne());
        DayTwelve dayTwelve2 = new DayTwelve(initialState);
        System.out.println("Day Twelve - part 2: " + dayTwelve2.partTwo());
    }

    int[][] initialState;
    int[][] moonPairs = {{1,2},{1,3},{1,0},{2,3},{2,0},{3,0}};
    private Point3D[] moons = new Point3D[4];

    DayTwelve(int[][] initialState)
    {
        super("", "");
        this.initialState = initialState;
        init();
    }

    long partOne()
    {
        for(int stepNo = 0; stepNo < 1000; stepNo++)
        {
            applyGravity();
            applyVelocity();
        }

        return getTotalEnergy();
    }

    long partTwo()
    {
        return 0;
    }

    private void applyGravity()
    {
        Point3D moon1, moon2;

        for (int[] moonPair: moonPairs)
        {
            moon1 = moons[moonPair[0]];
            moon2 = moons[moonPair[1]];
            moon1.applyGravity(moon2);
            moon2.applyGravity(moon1);
        }
    }

    private void applyVelocity()
    {
        for (int moonNo = 0; moonNo < 4; moonNo++) { moons[moonNo].move(); }
    }

    private long getTotalEnergy()
    {
        long totalEnergy = 0;
        for (int moonNo = 0; moonNo < 4; moonNo++) { totalEnergy += moons[moonNo].getTotalEnergy(); }
        return totalEnergy;
    }

    private void init()
    {
        for (int index = 0; index < initialState.length; index++)
        {
            moons[index] = new Point3D(initialState[index][0], initialState[index][1], initialState[index][2]);
        }
    }
}
