package advent.twentyeighteen;

import java.io.File;
import java.util.List;

public class DaySeventeen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DaySeventeen daySeventeen1 = new DaySeventeen(new File("input/2018/DaySeventeen.txt"), 50);
        System.out.println("Day Seventeen - part 1: " + daySeventeen1.partOneTwo(10));
        DaySeventeen daySeventeen2 = new DaySeventeen(new File("input/2018/DaySeventeen.txt"), 50);
        System.out.println("Day Seventeen - part 2: " + daySeventeen2.partOneTwo(1000000000));
    }

    private static final char OPEN = '.';
    private static final char TREES = '|';
    private static final char YARD = '#';

    private int areaSize;
    private char[][] lumberArea, updatedArea;

    private DaySeventeen(File inputFile, int dimension) throws Exception
    {
        super(inputFile);
        this.areaSize = dimension;
        init();
    }

    DaySeventeen(String inputText, String separator, int dimension)
    {
        super(inputText, separator);
        this.areaSize = dimension;
        init();
    }

    int partOneTwo(int numMinutes)
    {
        for (int minute = 1; minute <= numMinutes; minute++)
        {
            updateLumberArea();
            System.out.println("Minute: " + minute + ", Resource value: " + getResourceValue());
        }

        return getResourceValue();
    }

    private void init()
    {
        lumberArea = new char[areaSize][areaSize];
        for (int i = 0; i < areaSize; i++) { lumberArea[i] = inputs[i].toCharArray(); }
        updatedArea = new char[areaSize][areaSize];
    }

    private void updateLumberArea()
    {
        LumberLocation ll;

        for (int r = 0; r < areaSize; r++)
        {
            for (int c = 0; c < areaSize; c++)
            {
                ll = new LumberLocation(r, c);
                if (ll.isOpen() && ll.numAdjTrees >= 3) { updatedArea[r][c] = TREES; }
                else if (ll.isTrees() && ll.numAdjYard >= 3) { updatedArea[r][c] = YARD; }
                else if (ll.isYard() && (ll.numAdjYard == 0 || ll.numAdjTrees == 0)) { updatedArea[r][c] =  OPEN; }
                else { updatedArea[r][c] = lumberArea[r][c]; }
            }

            // System.out.println(lumberArea[r]);
        }

        // System.out.println();

        char[][] temp = lumberArea;
        lumberArea = updatedArea;
        updatedArea = temp;
    }

    private int getResourceValue()
    {
        int numTrees = 0;
        int numYards = 0;

        for (int r = 0; r < areaSize; r++)
        {
            for (int c = 0; c < areaSize; c++)
            {
                if (lumberArea[r][c] == TREES) { numTrees++; }
                else if (lumberArea[r][c] == YARD) { numYards++; }
            }
        }

        return numTrees * numYards;
    }

    class LumberLocation extends Location
    {
        int numAdjOpen = 0;
        int numAdjTrees = 0;
        int numAdjYard = 0;

        LumberLocation(int row, int column)
        {
            super(row, column);
            updateAdjacentCounts();
        }

        boolean isOpen() { return lumberArea[row][column] == OPEN; }
        boolean isTrees() { return lumberArea[row][column] == TREES; }
        boolean isYard() { return lumberArea[row][column] == YARD; }

        private void updateAdjacentCounts()
        {
            List<Location> adjacents = getEightAdjacents(lumberArea);
            for (Location adjacent: adjacents)
            {
                if (lumberArea[adjacent.row][adjacent.column] == OPEN) { numAdjOpen++; }
                else if (lumberArea[adjacent.row][adjacent.column] == TREES) { numAdjTrees++; }
                else /* YARD */ { numAdjYard++; }
            }
        }
    }
}
