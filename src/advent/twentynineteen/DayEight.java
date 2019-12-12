package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Arrays;

public class DayEight extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayEight dayEight1 = new DayEight(new File("input/2019/DayEight.txt"), 25, 6);
        System.out.println("Day Eight - part 1: " + dayEight1.partOne());
        DayEight dayEight2 = new DayEight(new File("input/2019/DayEight.txt"), 25, 6);
        dayEight2.partTwo();
    }

    private boolean debug = false;
    private int width;
    private int height;
    private String imageData;
    private int[][][] layers;
    private int[][] layerCounts;

    private DayEight(File inputFile, int width, int height) throws Exception
    {
        super(inputFile);
        init(width, height);
    }

    DayEight(String inputText, String separator, int width, int height)
    {
        super(inputText, separator);
        init(width, height);
    }

    int[][][] getLayers()
    {
        return layers;
    }

    int partOne()
    {
        int layerNoWithFewestZeros = 0;

        for (int layerNo = 0; layerNo < layerCounts.length; layerNo++)
        {
            if (layerCounts[layerNo][0] < layerCounts[layerNoWithFewestZeros][0])
            {
                layerNoWithFewestZeros = layerNo;
            }
        }

        return layerCounts[layerNoWithFewestZeros][1] * layerCounts[layerNoWithFewestZeros][2];
    }

    void partTwo()
    {
        int[][] image = generateImage();
        outputImage(image);
    }

    private void init(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.imageData = inputs[0];
        generateLayers();
    }

    private void generateLayers()
    {
        int layerLength = width * height;
        int numLayers = imageData.length() / layerLength;
        layers = new int[numLayers][height][width];
        layerCounts = new int[numLayers][10];
        int dataIndex = 0;

        for (int layerNo = 0; layerNo < numLayers; layerNo++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    int data = Character.getNumericValue(imageData.charAt(dataIndex++));
                    if (debug) System.out.println("layers["+layerNo+"]["+y+"]["+x+"] = " + data);
                    layers[layerNo][y][x] = data;
                    layerCounts[layerNo][data] = layerCounts[layerNo][data] + 1;
                }
            }
        }
    }

    int[][] generateImage()
    {
        int[][] image = layers[0].clone();

        for (int layerNo = 1; layerNo < layers.length; layerNo++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    if (image[y][x] == 2 && layers[layerNo][y][x] != 2) { image[y][x] = layers[layerNo][y][x]; }
                }
            }
        }

        return image;
    }

    private void outputImage(int[][] image)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++) { System.out.print(image[y][x] == 1 ? '#' : ' '); }
            System.out.println();
        }
    }
}
