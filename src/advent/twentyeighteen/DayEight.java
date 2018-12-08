package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayEight extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayEight dayEight = new DayEight(new File("input/2018/DayEight.txt"));
        System.out.println("Day Eight - part 1: " + dayEight.partOne());
        System.out.println("Day Eight - part 2: " + dayEight.partTwo());
    }

    private Node root;

    private DayEight(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayEight(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne()
    {
        return root.getMetadataTotal();
    }

    int partTwo()
    {
        return root.getValue();
    }

    private void init()
    {
        String[] license = inputs[0].split(" ");
        root = new Node('A', license, 0);
    }

    private class Node
    {
        char id;
        List<Node> children = new ArrayList<>();
        int[] metadata;

        Node(char id, String[] license, int licenseIndex)
        {
            this.id = id;
            int numChildren = Integer.parseInt(license[licenseIndex++]);
            int numMetadata = Integer.parseInt(license[licenseIndex++]);
            char childId;
            Node child;

            for (int childIndex = 0; childIndex < numChildren; childIndex++)
            {
                childId = (char) ((int)id + childIndex + 1);
                child = new Node(childId, license, licenseIndex);
                licenseIndex += child.getLicenseLength();
                children.add(child);
            }

            metadata = new int[numMetadata];

            for (int i = 0; i < numMetadata; i++)
            {
                metadata[i] = Integer.parseInt(license[licenseIndex++]);
            }
        }

        private int getLicenseLength()
        {
            int length = 2 + metadata.length;
            for (Node child: children) { length += child.getLicenseLength(); }
            return length;
        }

        int getMetadataTotal()
        {
            int total = metadata.length > 0 ? Arrays.stream(metadata).sum() : 0;
            for (Node child: children) { total += child.getMetadataTotal(); }
            return total;
        }

        int getValue()
        {
            int value = 0;

            if (children.size() == 0)
            {
                value = metadata.length > 0 ? Arrays.stream(metadata).sum() : 0;
            }
            else
            {
                for (int i = 0; i < metadata.length; i++)
                {
                    if (metadata[i] > 0)
                    {
                        int childIndex = metadata[i]-1;
                        if (childIndex < children.size()) { value += children.get(childIndex).getValue(); }
                    }
                }
            }

            return value;
        }
    }
}
