package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DayThirteen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DayThirteen.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DayThirteen dayThirteen1 = new DayThirteen(inputText, ",");
        System.out.println("Day Thirteen - part 1: " + dayThirteen1.partOne()); // 236
        DayThirteen dayThirteen2 = new DayThirteen(inputText, ",");
        System.out.println("Day Thirteen - part 2: " + dayThirteen2.partTwo()); //
    }

    private long[] program;

    DayThirteen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    class Game
    {
        private static final int EMPTY = 0;
        private static final int WALL = 1;
        private static final int BLOCK = 2;
        private static final int PADDLE = 3;
        private static final int BALL = 4;

        private final char[] tiles = new char[]{' ','|','\u2588','_','O'};

        private char[][] gameGrid = new char[21][36];
        private int blockCount = 0;
        private long segmentDisplay = 0;
        private int paddleX, ballX = 0, ballXLast = 0;
        private IntCodeComputerDayNine game = new IntCodeComputerDayNine("Game", program.clone());
        private BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        private BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();

        Game() {}

        long getSegmentDisplay() { return segmentDisplay; }

        void run() throws Exception
        {
            Thread gameProcessor = new Thread(() -> {
                try { game.runProgram(inputs, outputs); } catch(Exception ex) { ex.printStackTrace();}
            });
            gameProcessor.start();

            Thread joystick = new Thread(() ->
            {
                long position = 0;
                try { inputs.put(position); } catch (InterruptedException ex) { /* whatever */ }

                while (true)
                {
                    try
                    {
                        if (game.isWaitingForInput())
                        {
                            outputGameGrid();
                            if (ballX < paddleX || (ballX == paddleX && ballX < ballXLast)) { position = -1; }
                            else if (ballX > paddleX || (ballX == paddleX && ballXLast < ballX)) { position = 1; }
                            else { position = 0; }
                            inputs.put(position);
                        }
                        Thread.sleep(2);
                    }
                    catch (InterruptedException ex)
                    {
                        System.out.println("Joystick interrupted");
                        break;
                    }
                }
            });
            joystick.start();

            int x, y, tileType;

            do
            {
                if (outputs.size() == 0) { Thread.sleep(2); continue; }

                x = outputs.take().intValue();
                y = outputs.take().intValue();

                if (x == -1 && y == 0)
                {
                    segmentDisplay = outputs.take();
                    System.out.println("score = " + segmentDisplay);
                }
                else
                {
                    tileType = outputs.take().intValue();

                    if (tileType == BALL)
                    {
                        if (gameGrid[y][x] == tiles[BLOCK]) { blockCount--; }

                        ballXLast = ballX;
                        ballX = x;
                    }
                    else if (tileType == PADDLE)
                    {
                        paddleX = x;
                    }
                    else
                    {
                        if (tileType == BLOCK) { blockCount++; }
                    }

                    gameGrid[y][x] = tiles[tileType];
                }
            }
            while (gameProcessor.isAlive() || outputs.size() > 0);

            joystick.interrupt();
        }

        int getBlockTileCount()
        {
            return blockCount;
        }

        void outputGameGrid()
        {
            for (char[] row: gameGrid)
            {
                for (char tile: row) { System.out.print("" + tile); }
                System.out.println();
            }
        }
    }

    long partOne() throws Exception
    {
        program[0] = 1;
        Game game = new Game();
        game.run();
        game.outputGameGrid();
        return game.getBlockTileCount();
    }

    long partTwo() throws Exception
    {
        program[0] = 2;
        Game game = new Game();
        game.run();
        return game.getSegmentDisplay();
    }

    private void init()
    {
        program = new long[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Long.parseLong(inputs[index]); }
    }
}
