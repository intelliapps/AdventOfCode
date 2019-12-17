package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFourteen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayFourteen dayFourteen1 = new DayFourteen(new File("input/2019/DayFourteen.txt"));
        System.out.println("Day Fourteen - part 1: " + dayFourteen1.partOne());
        DayFourteen dayFourteen2 = new DayFourteen(new File("input/2019/DayFourteen.txt"));
        System.out.println("Day Fourteen - part 2: " + dayFourteen2.partTwo());
    }

    private DayFourteen(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayFourteen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    private void init()
    {
        for (String inputLine: inputs) { new Reaction(inputLine); }
    }

    int partOne()
    {
        chemicals.get("FUEL").produce();
        return chemicals.get("ORE").getNumConsumed();
    }

    int partTwo()
    {
        return 0;
    }

    private Map<String,Chemical> chemicals = new HashMap<>();

    private class Chemical
    {
        private String name;
        private List<Reaction> producedBy = new ArrayList<>();
        Chemical (String name) { this.name = name; }
        void isProducedBy(Reaction reaction) { producedBy.add(reaction); }
        List<Reaction> isProducedBy() { return producedBy; }
        int numProduced = 0;
        int numConsumed = 0;

        private int getNumAvailable()
        {
            return numProduced - numConsumed;
        }
        int getNumConsumed()
        {
            return numConsumed;
        }

        void produce()
        {
            if (name.equals("ORE")) { throw new RuntimeException("Cannot produce ORE"); }
            if (isProducedBy().size() > 1) { throw new RuntimeException("Chemical produced by multiple reactions"); }

            System.out.println("Producing " + name);

            Reaction reaction = isProducedBy().get(0);
            Chemical inputChemical;
            int numRequired;

            for (int index = 0; index < reaction.inputs.length; index++)
            {
                inputChemical = reaction.inputs[index];
                numRequired = reaction.inputAmounts[index];
                System.out.println("Require " + numRequired + " " + inputChemical.name);

                if (!inputChemical.name.equals("ORE"))
                {
                    while (inputChemical.getNumAvailable() < numRequired)
                    {
                        inputChemical.produce();
                    }
                }
                inputChemical.consume(numRequired);
            }

            System.out.println("Produced " + reaction.outputAmount + " " + name);
            numProduced += reaction.outputAmount;
        }

        void consume(int numRequired)
        {
            System.out.println("Consumed " + numRequired + " " + name);
            numConsumed += numRequired;
        }
    }

    private class Reaction
    {
        int[] inputAmounts;
        Chemical[] inputs;
        int outputAmount;
        Chemical output;

        Reaction (String equation)
        {
            String[] reactants = equation.split(" => ");

            String[] inputReactants = reactants[0].split(", ");
            inputAmounts = new int[inputReactants.length];
            inputs = new Chemical[inputReactants.length];

            for (int index = 0; index < inputReactants.length; index++)
            {
                String[] inputReactant = inputReactants[index].split(" ");
                inputAmounts[index] = Integer.parseInt(inputReactant[0]);
                inputs[index] = getOrCreateChemical(inputReactant[1]);
            }

            String[] outputReactant = reactants[1].split(" ");
            outputAmount = Integer.parseInt(outputReactant[0]);
            output = getOrCreateChemical(outputReactant[1]);
            output.isProducedBy(this);
        }
    }

    private Chemical getOrCreateChemical(String name)
    {
        Chemical chemical;

        if (!chemicals.containsKey(name))
        {
            chemical = new Chemical(name);
            chemicals.put(name, chemical);
        }
        else
        {
            chemical = chemicals.get(name);
        }

        return chemical;
    }
}
