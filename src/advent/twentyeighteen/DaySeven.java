package advent.twentyeighteen;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DaySeven extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Day Seven - part 1: " + new DaySeven(new File("input/2018/DaySeven.txt")).partOne());
        System.out.println("Day Seven - part 2: " + new DaySeven(new File("input/2018/DaySeven.txt"), 0, 5).partTwo());
    }

    private final Step IDLE = new Step('.', 0);
    private Map<Character, Step> steps = new HashMap<>();
    private List<Step> ready = new ArrayList<>();
    private Integer timeOffset = null;
    private int numWorkers = 0;

    private DaySeven(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DaySeven(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    private DaySeven(File inputFile, int timeOffset, int numWorkers) throws Exception
    {
        super(inputFile);
        this.timeOffset = timeOffset;
        this.numWorkers = numWorkers;
        init();
    }

    DaySeven(String inputText, String separator, int timeOffset, int numWorkers)
    {
        super(inputText, separator);
        this.timeOffset = timeOffset;
        this.numWorkers = numWorkers;
        init();
    }

    String partOne()
    {
        List<Step> executionOrder = new ArrayList<>();
        Step current;

        while (ready.size() > 0)
        {
            ready.sort((step1, step2) -> Character.compare(step1.id, step2.id));
            current = ready.remove(0);
            executionOrder.add(current.execute());

            for(Step dependant: current.dependants)
            {
                if (dependant.isReady()) { ready.add(dependant); }
            }
        }

        return executionOrder.stream().map(s->""+s.id).collect(Collectors.joining());
    }

    int partTwo()
    {
        List<Step> executionOrder = new ArrayList<>();
        Step[] workers = new Step[numWorkers];
        Arrays.fill(workers, IDLE);
        int secondsElapsed = 0;

        do
        {
            for (int i = 0; i < workers.length; i++)
            {
                if (workers[i] != IDLE)
                {
                    workers[i].execute();

                    if (workers[i].isComplete)
                    {
                        executionOrder.add(workers[i]);

                        for(Step dependant: workers[i].dependants)
                        {
                            if (dependant.isReady()) { ready.add(dependant); }
                        }

                        workers[i] = IDLE;
                    }
                }
            }

            for (int i = 0; i < workers.length; i++)
            {
                ready.sort((step1, step2) -> Character.compare(step1.id, step2.id));
                if (workers[i] == IDLE && ready.size() > 0) { workers[i] = ready.remove(0); }
            }

            String done = executionOrder.stream().map(s->""+s.id).collect(Collectors.joining());
            String workerIDs = Arrays.stream(workers).map(s -> ""+s.id).collect(Collectors.joining(" "));
            System.out.println("" + secondsElapsed + ' ' + workerIDs + ' ' + done);
            secondsElapsed++;
        }
        while (ready.size() > 0 || Arrays.stream(workers).anyMatch(p -> p != IDLE));

        return secondsElapsed-1;
    }

    private void init()
    {
        Step prerequisiteStep, dependantStep;

        for (String input: inputs)
        {
            prerequisiteStep = getOrCreateStep(input.charAt(5));
            dependantStep = getOrCreateStep(input.charAt(36));

            prerequisiteStep.addDependant(dependantStep);
            dependantStep.addPrerequisite(prerequisiteStep);

            if (!prerequisiteStep.hasPrerequisites()
                    && !ready.contains(prerequisiteStep)) { ready.add(prerequisiteStep); }
            ready.remove(dependantStep);
        }
    }

    private Step getOrCreateStep(char id)
    {
        boolean withTimeToExecute = timeOffset != null;
        int timeToExecute = withTimeToExecute ? (int) id-4-timeOffset : 1;
        Step step = steps.containsKey(id) ? steps.get(id) : new Step(id, timeToExecute);
        steps.put(id, step);
        return step;
    }

    private class Step
    {
        char id;
        int secondsToExecute = 1;
        Set<Step> prerequisites = new HashSet<>();
        Set<Step> dependants = new HashSet<>();
        boolean isComplete = false;

        Step(char id, int seconds) { this.id = id; this.secondsToExecute = seconds; }
        void addPrerequisite(Step step) { prerequisites.add(step); }
        void addDependant(Step step) { dependants.add(step); }
        boolean hasPrerequisites() { return prerequisites.size() > 0; }
        boolean isReady() { return prerequisites.stream().allMatch(p -> p.isComplete); }

        Step execute()
        {
            secondsToExecute--;
            isComplete = (secondsToExecute == 0);
            return this;
        }
    }
}
