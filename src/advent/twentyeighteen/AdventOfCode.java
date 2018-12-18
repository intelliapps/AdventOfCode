package advent.twentyeighteen;

import java.io.File;
import java.util.*;

class AdventOfCode
{
    String[] inputs;

    AdventOfCode(File inputFile) throws Exception
    {
        this.inputs = readLines(inputFile);
    }

    AdventOfCode(String inputText, String separator)
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
