package advent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdventOfCode
{
    protected String[] inputs;

    public AdventOfCode(File inputFile) throws Exception
    {
        this.inputs = readLines(inputFile);
    }

    public AdventOfCode(String inputText, String separator)
    {
        inputs = inputText.split(separator);
    }

    protected String[] readLines(File inputFile) throws Exception
    {
        Scanner sc = new Scanner(inputFile);
        List<String> linesRead = new ArrayList<>();

        while(sc.hasNext())
        {
            linesRead.add(sc.nextLine());
        }

        return linesRead.toArray(new String[]{});
    }
}
