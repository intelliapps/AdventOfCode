package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.*;

public class DayThree extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayThree dayThree1 = new DayThree(new File("input/2019/DayThree.txt"));
        System.out.println("Day Three - part 1: " + dayThree1.partOne()); // 227
        DayThree dayThree2 = new DayThree(new File("input/2019/DayThree.txt"));
        System.out.println("Day Three - part 2: " + dayThree2.partTwo());
    }

    static class Point
    {
        int x;
        int y;
        int[] numSteps = new int[2];
        int manhattanDistance;

        Point(int x, int y)
        {
            this.x = x;
            this.y = y;
            this.manhattanDistance = Math.abs(x) + Math.abs(y);
        }

        @Override
        public boolean equals(Object o)
        {
            if (o == this) { return true; }
            if (!(o instanceof Point)) { return false; }
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }

        public int getCombinedSteps() { return numSteps[0] + numSteps[1]; }
    }

    private List<Point> pathOne = null;
    private List<Point> pathTwo = null;
    private Map<Integer, Map<Integer,Point>> grid = new HashMap<>();
    private Set<Point> intersections = new HashSet<>();

    private DayThree(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayThree(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne()
    {
        List<Point> sortedIntersections = new ArrayList<>(intersections);
        sortedIntersections.sort((o1, o2) -> o1.manhattanDistance - o2.manhattanDistance);
        return sortedIntersections.size() > 0 ? sortedIntersections.get(0).manhattanDistance : 0;
    }

    int partTwo()
    {
        int fewestCombinedSteps = 999999999;

        for (Point intersection: intersections)
        {
            fewestCombinedSteps = Math.min(intersection.getCombinedSteps(), fewestCombinedSteps);
        }

        return fewestCombinedSteps;
    }

    private void init()
    {
        pathOne = generatePath(inputs[0], 0);
        pathTwo = generatePath(inputs[1], 1);
    }

    private List<Point> generatePath(String pathInput, int pathNo)
    {
        List<Point> path = new ArrayList<>(); // deliberately NOT adding origin point
        String[] moves = pathInput.split(",");
        int x = 0, y = 0, moveSteps, numSteps = 0;
        Map<Integer, Point> row;
        char moveDirection;
        Point nextPoint;

        for (String move: moves)
        {
            moveDirection = move.charAt(0);
            moveSteps = Integer.parseInt(move.substring(1));

            for (int stepNo = 0; stepNo < moveSteps; stepNo++)
            {
                switch(moveDirection)
                {
                    case 'R': x++; break;
                    case 'L': x--; break;
                    case 'U': y++; break;
                    case 'D': y--; break;
                }

                if (!grid.containsKey(y)) { grid.put(y, new HashMap<>()); }
                row = grid.get(y);

                if (!row.containsKey(x))
                {
                    nextPoint = new Point(x, y);
                    row.put(x, nextPoint);
                }
                else
                {
                    nextPoint = row.get(x);
                }

                nextPoint.numSteps[pathNo] = ++numSteps;
                path.add(nextPoint);

                if (pathNo == 1 && nextPoint.numSteps[0] > 0 && nextPoint.numSteps[1] > 0)
                {
                    intersections.add(nextPoint);
                }
            }
        }

        return path;
    }
}
