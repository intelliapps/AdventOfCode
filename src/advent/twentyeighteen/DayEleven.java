package advent.twentyeighteen;

public class DayEleven
{
    public static void main(String[] args) throws Exception
    {
        DayEleven dayEleven = new DayEleven(1308);
        System.out.println("Day Eleven - part 1: " + dayEleven.partOneTwo(3, 3));
        System.out.println("Day Eleven - part 2: " + dayEleven.partOneTwo(1, 300));
    }

    DayEleven(int serialNumber)
    {
        Cell.serialNumber = serialNumber;
    }

    Square partOneTwo(int minSquareSize, int maxSquareSize)
    {
        Cell[][] grid = new Cell[301][301];

        for (int y = 1; y <= 300; y++)
        {
            for (int x = 1; x <= 300; x++)
            {
                grid[x][y] = new Cell(x, y);
            }
        }

        int totalPower, maxTotalPower = 0, highestSquareSize;
        Square result = new Square();

        for (int originY = 1; originY <= 300; originY++)
        {
            for (int originX = 1; originX <= 300; originX++)
            {
                highestSquareSize = Math.min(maxSquareSize, 300 - Math.max(originX, originY) + 1);
                totalPower = 0;

                for (int squareSize = 1; squareSize <= highestSquareSize; squareSize++)
                {
                    for (int yOffset = 0; yOffset < squareSize; yOffset++)
                    {
                        totalPower += grid[originX + squareSize-1][originY + yOffset].powerLevel;
                    }
                    for (int xOffset = 0; xOffset < squareSize-1; xOffset++)
                    {
                        totalPower += grid[originX + xOffset][originY + squareSize-1].powerLevel;
                    }

                    if (squareSize >= minSquareSize && (result.size == 0 || maxTotalPower < totalPower))
                    {
                        maxTotalPower = totalPower;
                        result.originX = originX;
                        result.originY = originY;
                        result.size = squareSize;
                        result.power = totalPower;
                    }
                }
            }
        }

        return result;
    }

    class Square
    {
        int originX;
        int originY;
        int size = 0;
        int power = 0;

        @Override
        public String toString()
        {
            return "[" + originX + ',' + originY + ((size > 0) ? ("," + size) : "") + ']';
        }
    }
}

class Cell
{
    static int serialNumber = 0;

    final int x;
    final int y;
    final int rackID;
    final int powerLevel;

    Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.rackID = x + 10;
        this.powerLevel = calculatePowerLevel();
    }

    private int calculatePowerLevel()
    {
        int tempPowerLevel = rackID * y;
        tempPowerLevel += serialNumber;
        tempPowerLevel = tempPowerLevel * rackID;
        String temp = Integer.toString(tempPowerLevel);
        tempPowerLevel = temp.length() >= 3 ? Integer.valueOf("" + temp.charAt(temp.length()-3)) : 0;
        tempPowerLevel -= 5;
        return tempPowerLevel;
    }
}

