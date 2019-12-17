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
        System.out.println("Day Fourteen - part 1: " + dayFourteen1.partOne()); // 397771
        DayFourteen dayFourteen2 = new DayFourteen(new File("input/2019/DayFourteen.txt"));
        System.out.println("Day Fourteen - part 2: " + dayFourteen2.partTwo());
    }

    private Map<String,Chemical> chemicals;
    private boolean debug = false;

    private DayFourteen(File inputFile) throws Exception
    {
        super(inputFile);
    }

    DayFourteen(String inputText, String separator)
    {
        super(inputText, separator);
    }

    private void init()
    {
        chemicals = new HashMap<>();
        for (String inputLine: inputs) { new Reaction(inputLine); }
    }

    long partOne()
    {
        init();
        chemicals.get("FUEL").produce(1);
        return chemicals.get("ORE").getNumConsumed();
    }

    long partTwo()
    {
        long availableOre = Long.parseLong("1000000000000");
        long numFuel = 0;
        long oreConsumed = 0;
        double adjustment = 500000;

        do
        {
            init();
            numFuel += oreConsumed < availableOre ? Math.round(adjustment) : -Math.round(adjustment);
            chemicals.get("FUEL").produce(numFuel);
            oreConsumed = chemicals.get("ORE").getNumConsumed();
            System.out.println("Num fuel: " + numFuel + ", Ore consumed = " + oreConsumed + ", Adjustment:" + adjustment);
            if (oreConsumed > availableOre) { adjustment = adjustment / 2; }
            if (Math.round(adjustment) < 1) { break; }
        }
        while (oreConsumed != availableOre);

        return numFuel-1;
    }

    private class Chemical
    {
        private String name;
        private List<Reaction> producedBy = new ArrayList<>();
        Chemical (String name) { this.name = name; }
        void isProducedBy(Reaction reaction) { producedBy.add(reaction); }
        List<Reaction> isProducedBy() { return producedBy; }
        long numProduced = 0;
        long numConsumed = 0;

        long getNumAvailable()
        {
            return numProduced - numConsumed;
        }
        long getNumConsumed()
        {
            return numConsumed;
        }

       void produce(long numRequired)
        {
            if (numRequired == 0) { return; }
            if (name.equals("ORE")) { throw new RuntimeException("Cannot produce ORE"); }
            if (isProducedBy().size() > 1) { throw new RuntimeException("Chemical produced by multiple reactions"); }

            Reaction reaction = isProducedBy().get(0);
            Chemical inputChemical;
            long numInputRequired, numInputToProduce;

            long numToProduce = numRequired;
            while (numToProduce % reaction.outputAmount > 0) numToProduce++;
            long numReactions = numToProduce / reaction.outputAmount;

            if (debug) System.out.println("Producing " + numToProduce + " " + name);

            for (int index = 0; index < reaction.inputs.length; index++)
            {
                inputChemical = reaction.inputs[index];
                numInputRequired = reaction.inputAmounts[index] * numReactions;
                if (debug) System.out.println("Require " + numInputRequired + " " + inputChemical.name);

                if (!inputChemical.name.equals("ORE"))
                {
                    numInputToProduce = numInputRequired > inputChemical.getNumAvailable()
                            ? numInputRequired - inputChemical.getNumAvailable() : 0;
                    inputChemical.produce(numInputToProduce);
                }

                inputChemical.consume(numInputRequired);
            }

            if (debug) System.out.println("Produced " + numToProduce + " " + name);
            numProduced += numToProduce;
        }

        void consume(long numRequired)
        {
            if (debug) System.out.println("Consumed " + numRequired + " " + name);
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
