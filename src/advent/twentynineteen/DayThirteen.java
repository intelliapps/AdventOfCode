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
        System.out.println("Day Thirteen - part 1: " + dayThirteen1.partOne()); // 3280416268
        DayThirteen dayThirteen2 = new DayThirteen(inputText, ",");
        System.out.println("Day Thirteen - part 2: " + dayThirteen2.partTwo()); // 80210
    }

    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int BLOCK = 2;
    private static final int PADDLE = 3;
    private static final int BALL = 4;

    private static final char[] tiles = new char[]{' ','|','\u2588','_','O'};

    private int[][] gameGrid = new int[500][500];
    private long[] program;

    DayThirteen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    long partOne() throws Exception
    {
        IntCodeComputerDayNine game = new IntCodeComputerDayNine("Game", program.clone());
        BlockingQueue<Long> inputs = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputs = new LinkedBlockingQueue<>();

        Thread gameProcessor = new Thread(() -> { try { game.runProgram(inputs, outputs); } catch(Exception ex) { ex.printStackTrace();} });
        gameProcessor.start();

        int x, y, tileType;

        do
        {
            x = outputs.take().intValue();
            y = outputs.take().intValue();
            tileType = outputs.take().intValue();

            if (tileType != BALL)
            {
                gameGrid[y][x] = tiles[tileType];
            }
            else // BALL
            {
                if (gameGrid[y][x] == tiles[EMPTY]) { gameGrid[y][x] = tiles[BALL]; }
                else if (gameGrid[y][x] == tiles[BLOCK]) { gameGrid[y][x] = tiles[EMPTY]; }
            }
        }
        while (gameProcessor.isAlive());

        return getBlockTileCount();
    }

    long partTwo() throws Exception
    {
        return 0;
    }

    int getBlockTileCount()
    {
        int blockCount = 0;
        for (int[] row: gameGrid) { for (int tile: row) { blockCount += (tile == tiles[BLOCK]) ? 1 : 0; } }
        return blockCount;
    }

    private void init()
    {
        program = new long[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Long.parseLong(inputs[index]); }
    }
}
