package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayTwenty extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayTwenty dayTwenty1 = new DayTwenty(new File("input/2018/DayTwenty.txt"));
        System.out.println("Day Twenty - part 1: " + dayTwenty1.partOne());
        DayTwenty dayTwenty2 = new DayTwenty(new File("input/2018/DayTwenty.txt"));
        System.out.println("Day Twenty - part 2: " + dayTwenty2.partTwo());
    }

    private static final char ROOM = '.';
    private static final char DOOR_EW = '|';
    private static final char DOOR_NS = '-';

    private Map<Integer, Map<Integer, Character>> baseMap = new HashMap<>();
    private Map<Integer, Map<Integer, Room>> rooms = new HashMap<>();
    private int rMin = 0, rMax = 0, cMin = 0, cMax = 0;

    class Room extends Location
    {
        Set<Character> doors = new HashSet<>();

        Room(int row, int column)
        {
            super(row, column);
            initDoors();
        }

        private void initDoors()
        {
            if (baseMap.containsKey(row-1) && baseMap.get(row-1).get(column) == DOOR_NS) { doors.add('N'); }
            if (baseMap.get(row).containsKey(column+1) && baseMap.get(row).get(column+1) == DOOR_EW) { doors.add('E'); }
            if (baseMap.containsKey(row+1) && baseMap.get(row+1).get(column) == DOOR_NS) { doors.add('S'); }
            if (baseMap.get(row).containsKey(column-1) && baseMap.get(row).get(column-1) == DOOR_EW) { doors.add('W'); }
        }

        Room getRoomVia(char door)
        {
            if (!doors.contains(door)) { return null; }

            switch (door)
            {
                case 'N': return rooms.get(row-2).get(column);
                case 'E': return rooms.get(row).get(column+2);
                case 'S': return rooms.get(row+2).get(column);
                case 'W': return rooms.get(row).get(column-2);
                default: return  null;
            }
        }
    }

    private DayTwenty(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayTwenty(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        generateBaseMap();
        outputBaseMap();
        return findDoorsToFurthestRoom();
    }

    int partTwo()
    {
        return 0;
    }

    private void generateBaseMap()
    {
        Location current = new Location(0,0);
        Stack<Location> groupStarts = new Stack<>();
        String input = inputs[0];
        int index = 1;

        addToMap(current, ROOM);

        do
        {
            switch (input.charAt(index))
            {
                case '(': groupStarts.push(new Location(current.row, current.column)); break;
                case '|': current = groupStarts.peek(); break;
                case ')': groupStarts.pop(); break;
                case 'N':
                    current.row--;
                    addToMap(current, DOOR_NS);
                    current.row--;
                    addToMap(current, ROOM);
                    break;
                case 'E':
                    current.column++;
                    addToMap(current, DOOR_EW);
                    current.column++;
                    addToMap(current, ROOM);
                    break;
                case 'S':
                    current.row++;
                    addToMap(current, DOOR_NS);
                    current.row++;
                    addToMap(current, ROOM);
                    break;
                case 'W':
                    current.column--;
                    addToMap(current, DOOR_EW);
                    current.column--;
                    addToMap(current, ROOM);
                    break;
                default: break;
            }

            rMin = Math.min(rMin, current.row);
            rMax = Math.max(rMax, current.row);
            cMin = Math.min(cMin, current.column);
            cMax = Math.max(cMax, current.column);
            index++;
        }
        while (index < input.length()-1);

        addToMap(current, ROOM);

        addWallsToMap();
    }

    private void addWallsToMap()
    {
        Map<Integer, Character> row;

        for (int r = rMin-1; r <= rMax+1; r++)
        {
            row = baseMap.getOrDefault(r, new HashMap<>());
            for (int c = cMin-1; c <= cMax+1; c++) { row.put(c, row.getOrDefault(c, '#')); }
            baseMap.put(r, row);
        }
    }

    private void addToMap(Location current, char feature)
    {
        if (!baseMap.containsKey(current.row))
        {
            baseMap.put(current.row, new HashMap<>());
            rooms.put(current.row, new HashMap<>());
        }

        baseMap.get(current.row).put(current.column, feature);
    }

    private void outputBaseMap()
    {
        rooms.get(0).put(0, new Room(0,0));
        outputBaseMap(rooms.get(0).get(0));
    }

    private void outputBaseMap(Location start)
    {
        Map<Integer, Character> row;

        for (int r = rMin-1; r <= rMax+1; r++)
        {
            row = baseMap.get(r);
            for (int c = cMin-1; c <= cMax+1; c++)
            {
                if (row.get(c) == ROOM) { rooms.get(r).put(c, new Room(r,c)); }
                System.out.print((r == start.row && c == start.column) ? 'X' : row.get(c));
            }
            System.out.println();
        }

        System.out.println();
    }

    private int findDoorsToFurthestRoom()
    {
        int numDoors = 0;
        Set<Room> currentRooms = new HashSet<>(), nextRooms;
        currentRooms.add(rooms.get(0).get(0));
        Room nextRoom;

        while(numDoors == 0 || currentRooms.size() > 0)
        {
            numDoors++;
            nextRooms = new HashSet<>();

            for (Room currentRoom: currentRooms)
            {
                for (char door: currentRoom.doors)
                {
                    nextRoom = currentRoom.getRoomVia(door);

                    if (baseMap.get(nextRoom.row).get(nextRoom.column) == ROOM)
                    {
                        baseMap.get(nextRoom.row).put(nextRoom.column, ' ');
                        nextRooms.add(nextRoom);
                    }
                }
            }

            currentRooms = nextRooms;
        }

        return numDoors-1;
    }
}
