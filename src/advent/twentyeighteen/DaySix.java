package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DaySix extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DaySix daySix1 = new DaySix(new File("input/2018/DaySix.txt"), null);
        System.out.println("Day Six - part 1: " + daySix1.partOne());
        DaySix daySix2 = new DaySix(new File("input/2018/DaySix.txt"), 10000);
        System.out.println("Day Six - part 2: " + daySix2.partTwo());
    }

    private Integer regionThreshold = null;
    private Map<Character, PrimaryCoord> primaryCoords = new HashMap<>();
    private int xMax = 0, yMax = 0;
    private Result result;

    private DaySix(File inputFile, Integer regionThreshold) throws Exception
    {
        super(inputFile);
        this.regionThreshold = regionThreshold;
        init();
    }

    DaySix(String inputText, String separator, Integer regionThreshold)
    {
        super(inputText, separator);
        this.regionThreshold = regionThreshold;
        init();
    }

    int partOne()
    {
        return result.largestArea;
    }

    int partTwo()
    {
        return result.regionSize;
    }

    private void init()
    {
        char[] ids = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int idIndex = 0, separatorIndex, x, y;
        PrimaryCoord coord;

        for (String input: inputs)
        {
            separatorIndex = input.indexOf(',');
            x = Integer.parseInt(input.substring(0, separatorIndex));
            y = Integer.parseInt(input.substring(separatorIndex+2));
            coord = new PrimaryCoord(ids[idIndex++], x, y);
            primaryCoords.put(coord.id, coord);

            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
        }

        result = calculate();
    }

    private Result calculate()
    {
        PrimaryCoord empty = new PrimaryCoord(' ', -1, -1);
        PrimaryCoord multiple = new PrimaryCoord('.', -1, -1);

        Result result = new Result();
        Coord from;
        int shortestDistance, totalDistance, distance;
        PrimaryCoord closest;

        for (int y = 0; y <= yMax; y++)
        {
            for (int x = 0; x <= xMax; x++)
            {
                from = new Coord(x, y);
                shortestDistance = xMax + yMax + 1;
                totalDistance = 0;
                closest = empty;

                for (PrimaryCoord to: primaryCoords.values())
                {
                    distance = calculateManhattanDistance(from, to);
                    totalDistance += distance;

                    if (distance < shortestDistance)
                    {
                        shortestDistance = distance;
                        closest = to;
                    }
                    else if (distance == shortestDistance)
                    {
                        closest = multiple;
                    }
                }

                if (shortestDistance != 0) { closest.addCoordToArea(from); }

                if (regionThreshold != null)
                {
                    if (totalDistance < regionThreshold) { result.regionSize++; }
                    System.out.print(totalDistance < regionThreshold ? '#' : '.');
                }
                else
                {
                    System.out.print(closest.id);
                }
            }
            System.out.println();
        }

        for (PrimaryCoord coord: primaryCoords.values())
        {
            if (!coord.areaIsInfinite())
            {
                result.largestArea = Math.max(result.largestArea, coord.getAreaSize());
            }
        }

        return result;
    }

    private int calculateManhattanDistance(Coord from, Coord to)
    {
        return (Math.abs(from.x - to.x) + Math.abs(from.y - to.y));
    }

    class Result
    {
        int largestArea;
        int regionSize;
    }

    class Coord
    {
        int x;
        int y;

        Coord(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    class PrimaryCoord extends Coord
    {
        private Set<Coord> area = new HashSet<>();
        private boolean areaIsInfinite = false;
        char id;

        PrimaryCoord(char id, int x, int y)
        {
            super(x, y);
            area.add(this);
            this.id = id;
        }

        void addCoordToArea(Coord coord)
        {
            area.add(coord);

            areaIsInfinite = areaIsInfinite || coord.x == 0 || coord.x == xMax || coord.y == 0 || coord.y == yMax;
        }

        int getAreaSize()
        {
            return area.size();
        }

        boolean areaIsInfinite()
        {
            return areaIsInfinite;
        }
    }
}
