package advent.twentyeighteen;

import java.io.File;
import java.util.*;

class AdventOfCode
{
    String[] inputs;

    AdventOfCode(File inputFile) throws Exception
    {
        Scanner sc = new Scanner(inputFile);
        List<String> linesRead = new ArrayList<>();

        while(sc.hasNext())
        {
            linesRead.add(sc.nextLine());
        }

        this.inputs = linesRead.toArray(new String[]{});
    }

    AdventOfCode(String inputText, String separator)
    {
        inputs = inputText.split(separator);
    }
}
