package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayFifteen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayFifteen dayFifteen = new DayFifteen(new File("input/2018/DayFifteen.txt"));
        System.out.println("Day Fifteen - part 1: " + dayFifteen.partOne());
        System.out.println("Day Fifteen - part 2: " + dayFifteen.partTwo());
    }

    private DayFifteen(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayFifteen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    class Unit
    {
        final char type; // E or G
        final char targetType;
        int attackPower = 3;
        int hitPoints = 200;
        Location location;
        Unit target = null;

        Unit (char type, Location location)
        {
            this.type = type;
            this.targetType = type == 'E' ? 'G' : 'E';
            this.location = location;

            totalHitPoints += hitPoints;
        }

        void updateTarget()
        {
            List<Location> adjacents = getAdjacents(location);
            target = null;

            for (Location adjacent: adjacents)
            {
                if (cavernMap[adjacent.row][adjacent.column] == targetType
                        && (target == null || target.hitPoints > units[adjacent.row][adjacent.column].hitPoints))
                {
                    target = units[adjacent.row][adjacent.column];
                }
            }
        }

        void move()
        {
            if (target == null) { return; }

            List<Location> pathToTarget = findPathToTarget(location);

            if (pathToTarget != null)
            {
                cavernMap[location.row][location.column] = '.';
                units[location.row][location.column] = null;
                location = pathToTarget.get(0);
                cavernMap[location.row][location.column] = type;
                units[location.row][location.column] = this;
                updateTarget();
            }
        }

        private List<Location> findPathToTarget(Location startPoint)
        {
            List<Location> shortestPath = null;
            // TODO
            return shortestPath;
        }

        private void temp()
        {
            Set<Unit> targets = new HashSet<>(targetType == 'E' ? elves : goblins);
            Set<Location> possible = new HashSet<>();

            for (Unit target: targets) { possible.addAll(getAdjacents(target.location)); }
            possible.removeIf(l -> cavernMap[l.row][l.column] != '.'); // in range


        }

        void hit(int attackPower)
        {
            hitPoints -= Math.min(hitPoints, attackPower);
            totalHitPoints -= Math.min(hitPoints, attackPower);

            if (hitPoints == 0) // DIE!!!
            {
                cavernMap[location.row][location.column] = '.';
                units[location.row][location.column] = null;
                if (type == 'E') { elves.remove(this); } else { goblins.remove(this); }
            }
        }

        private List<Location> getAdjacents(Location startPoint)
        {
            List<Location> adjacents = new ArrayList<>();
            if (startPoint.row-1 > 0) { adjacents.add(new Location(startPoint.row-1, startPoint.column)); }
            if (startPoint.column-1 > 0) { adjacents.add(new Location(startPoint.row, startPoint.column-1)); }
            if (startPoint.column+1 < cavernMap[0].length) { adjacents.add(new Location(startPoint.row, startPoint.column+1)); }
            if (startPoint.row+1 < cavernMap.length) { adjacents.add(new Location(startPoint.row+1, startPoint.column)); }
            return  adjacents;
        }
    }

    private char[][] cavernMap;
    private Unit[][] units;
    private Set<Unit> elves = new HashSet<>();
    private Set<Unit> goblins = new HashSet<>();
    private int totalHitPoints = 0, numRounds = 0;

    private void init()
    {
        cavernMap = new char[inputs.length][];
        units = new Unit[inputs.length][];

        for (int row = 0; row < inputs.length; row++)
        {
            cavernMap[row] = inputs[row].toCharArray();
            units[row] = new Unit[cavernMap[row].length];
            Arrays.fill(units[row], null);

            for (int column = 0; column < cavernMap[row].length; column++)
            {
                if (cavernMap[row][column] == 'E')
                {
                    units[row][column] = new Unit('E', new Location(row, column));
                    elves.add(units[row][column]);
                }
                else if (cavernMap[row][column] == 'G')
                {
                    units[row][column] = new Unit('G', new Location(row, column));
                    goblins.add(units[row][column]);
                }
            }
        }
    }

    int partOne()
    {
        Unit unit;

        while(elves.size() > 0 && goblins.size() > 0)
        {
            for (int row = 0; row < cavernMap.length; row++)
            {
                for (int column = 0; column < cavernMap[row].length; column++)
                {
                    if (units[row][column] != null)
                    {
                        if (elves.size() == 0 && goblins.size() == 0) { return numRounds * totalHitPoints; }

                        unit = units[row][column];
                        unit.updateTarget();
                        if (unit.target == null) { unit.move(); }
                        if (unit.target != null) { unit.target.hit(unit.attackPower); }
                    }
                }
            }

            numRounds++;
        }

        return numRounds * totalHitPoints;
    }

    int partTwo()
    {
        return 0;
    }
}
