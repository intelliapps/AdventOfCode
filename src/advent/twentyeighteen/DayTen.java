package advent.twentyeighteen;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayTen dayTen = new DayTen(new File("input/2018/DayTen.txt"));
        System.out.println("Day Ten: " + dayTen.partOneTwo());
    }

    private List<Point> points = new ArrayList<>();
    private static int pxMin, pxMax, pyMin, pyMax;

    private DayTen(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayTen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOneTwo()
    {
        List<Point> lastPoints = new ArrayList<>();;
        int height = 200000, lastHeight = 200000;
        int secondsElapsed = 0;

        while (height <= lastHeight)
        {
            lastPoints = new ArrayList<>();
            for(Point point: points) { lastPoints.add(point.clone()); }

            points.forEach(Point::move);

            lastHeight = height;
            updatePositionMinMax();
            height = Math.abs(pyMin - pyMax);

            secondsElapsed++;
        }

        outputPointsView(lastPoints);

        return secondsElapsed-1;
    }

    private void init()
    {
        int px, py, vx, vy;

        for (String input: inputs)
        {
            Pattern pattern = Pattern.compile("position=<([ -]?\\d+), ([ -]?\\d+)> velocity=<([ -]?\\d+), ([ -]?\\d+)>");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find())
            {
                px = Integer.parseInt(matcher.group(1).trim());
                py = Integer.parseInt(matcher.group(2).trim());
                vx = Integer.parseInt(matcher.group(3).trim());
                vy = Integer.parseInt(matcher.group(4).trim());

                points.add(new Point(px, py, vx, vy));
            }
            else
            {
                System.out.println("No match found for: " + input);
            }
        }

        updatePositionMinMax();
    }

    private void updatePositionMinMax()
    {
        pxMin = 99999;
        pxMax = -99999;
        pyMin = 99999;
        pyMax = -99999;

        for (Point point: points)
        {
            pxMin = Math.min(pxMin, point.px);
            pxMax = Math.max(pxMax, point.px);
            pyMin = Math.min(pyMin, point.py);
            pyMax = Math.max(pyMax, point.py);
        }
    }

    private void outputPointsView(List<Point> pointsToOutput)
    {
        Map<Integer, Map<Integer, String>> pointsView = new HashMap<>();
        Map<Integer, String> row;

        for (int y = pyMin; y <= pyMax; y++)
        {
            row = new HashMap<>();
            for (int x = pxMin; x <= pxMax; x++) { row.put(x, "."); }
            pointsView.put(y, row);
        }

        for (Point point: pointsToOutput)
        {
            pointsView.get(point.getPy()).put(point.getPx(), "#");
        }

        for (int y = pyMin; y <= pyMax; y++)
        {
            for (int x = pxMin; x <= pxMax; x++) { System.out.print(pointsView.get(y).get(x)); }
            System.out.println();
        }

        System.out.println();
    }

    class Point
    {
        private int px;
        private int py;
        private int vx;
        private int vy;

        Point(int px, int py, int vx, int vy)
        {
            this.px = px;
            this.py = py;
            this.vx = vx;
            this.vy = vy;
        }

        int getPx() { return px; }
        int getPy() { return py; }

        void move()
        {
            px += vx;
            py += vy;
        }

        protected Point clone()
        {
            return new Point(px, py, vx, vy);
        }
    }
}
