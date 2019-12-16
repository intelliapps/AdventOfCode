package advent.twentynineteen;

import advent.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DayEleven extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayEleven.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DayEleven dayEleven1 = new DayEleven(inputText, ",");
        //System.out.println("Day Eleven - part 1: " + dayEleven1.partOne()); // 2322
        DayEleven dayEleven2 = new DayEleven(inputText, ",");
        System.out.println("Day Eleven - part 2: " + dayEleven2.partTwo()); //
    }

    private long[] program;
    private boolean test = false;
    private boolean debug = false;

    private class Robot
    {
        final static char UP = '^';
        final static char DOWN = 'v';
        final static char LEFT = '<';
        final static char RIGHT = '>';

        final static long PAINT_BLACK = 0;
        final static long PAINT_WHITE = 1;
        final static long TURN_LEFT = 0;
        final static long TURN_RIGHT = 1;

        IntCodeComputerDayNine brain;
        Map<Integer, Map<Integer, Long>> grid = new HashMap<>();
        BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();
        int xPos = 0, yPos = 0;
        int minX, minY, maxX, maxY;
        char orientation = UP;
        int numMoves = 0;
        long startColour;

        Robot(long startColour)
        {
            brain = new IntCodeComputerDayNine("RobotBrain", program.clone());
            this.startColour = startColour;
        }

         void run() throws Exception
         {
             if (test) { outputGrid(); }

             Thread brainProcessor = new Thread(() -> { try { brain.runProgram(inputs, outputs); } catch(Exception ex) { ex.printStackTrace();} });
             brainProcessor.start();

             long newColour, direction;

             System.out.println("startColour = " + startColour);

             do
             {
                 inputs.put(getCurrentPanelColour());
                 Thread.sleep(2);

                 newColour = outputs.take();
                 direction = outputs.take();

                 if (debug) System.out.println(
                        "Position " + xPos + ',' + yPos + ',' + orientation
                                + ": paint " + (newColour==PAINT_BLACK?'B':'W')
                                + " then turn " + (direction==TURN_LEFT?'L':'R'));

                 paint(newColour);
                 turn(direction);
                 move();

                 if (test) { outputGrid(); }
             }
             while (brainProcessor.isAlive());
         }

         void paint(long colour)
         {
             if (!grid.containsKey(yPos)) { grid.put(yPos, new HashMap<>()); }
             grid.get(yPos).put(xPos, colour);
         }

         void turn(long direction)
         {
             switch(orientation)
             {
                 case (UP): orientation = direction == TURN_LEFT ? LEFT : RIGHT; break;
                 case (DOWN): orientation = direction == TURN_LEFT ? RIGHT : LEFT; break;
                 case (LEFT): orientation = direction == TURN_LEFT ? DOWN : UP; break;
                 case (RIGHT): orientation = direction == TURN_LEFT ? UP : DOWN; break;
             }
         }

         void move()
         {
             switch(orientation)
             {
                 case (UP): yPos -= 1; minY = Math.min(minY, yPos); break;
                 case (DOWN): yPos += 1; maxY = Math.max(maxY, yPos); break;
                 case (LEFT):  xPos -= 1; minX = Math.min(minX, xPos); break;
                 case (RIGHT): xPos += 1; maxX = Math.max(maxX, xPos); break;
             }
             numMoves++;
         }

         long getCurrentPanelColour()
         {
             if (numMoves == 0) { return startColour; }

             return (grid.containsKey(yPos) && grid.get(yPos).containsKey(xPos))
                        ? grid.get(yPos).get(xPos)
                        : PAINT_BLACK;
         }

         int getNumPanelsPainted()
         {
             int total = 0;
             for (Map<Integer, Long> row: grid.values()) { total += row.size(); }
             return total;
         }

         void outputGrid()
         {
             for (int y = minY; y < maxY+1; y++)
             {
                 for (int x = minX; x < maxX+1; x++)
                 {
                     if (x == xPos && y == yPos) { System.out.print(orientation); }
                     else if (grid.containsKey(y) && grid.get(y).containsKey(x)) { System.out.print(grid.get(y).get(x)==0 ? ' ' : '\u2588'); }
                     else { System.out.print('.'); }
                 }
                 System.out.println();
             }
         }
    }

    DayEleven(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    long partOneTest() throws Exception
    {
        test = true;
        return partOne();
    }

    long partOne() throws Exception
    {
        Robot robot = new Robot(Robot.PAINT_BLACK);
        robot.run();
        return robot.getNumPanelsPainted();
    }

    long partTwo() throws Exception
    {
        Robot robot = new Robot(Robot.PAINT_WHITE);
        robot.run();
        robot.outputGrid();
        return robot.getNumPanelsPainted();
    }

    private void init()
    {
        program = new long[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Long.parseLong(inputs[index]); }
    }
}
