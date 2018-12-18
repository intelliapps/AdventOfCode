package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DaySixteen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DaySixteen daySixteen = new DaySixteen(
                new File("input/2018/DaySixteen1.txt"),
                new File("input/2018/DaySixteen2.txt")
        );
        System.out.println("Day Sixteen - part 1: " + daySixteen.partOne());
        System.out.println("Day Sixteen - part 2: " + daySixteen.partTwo());
    }

    private Map<Instruction, Set<OperationType>> samplesBehaveLike = new HashMap<>();
    private String[] inputs2;

    private DaySixteen(File inputFile, File inputFile2) throws Exception
    {
        super(inputFile);
        inputs2 = readLines(inputFile2);
    }

    DaySixteen(String inputText, String separator)
    {
        super(inputText, separator);
    }

    int partOne()
    {
        Set<OperationType> sampleBehavesLike;
        int index = 0, numMatchingSamples = 0;
        int[] before, after, instrVals, result;
        Instruction sample;

        while (index < inputs.length)
        {
            before = Arrays.stream(inputs[index++].substring(9, 19).split(", ")).mapToInt(Integer::parseInt).toArray();
            instrVals = Arrays.stream(inputs[index++].split(" ")).mapToInt(Integer::parseInt).toArray();
            after = Arrays.stream(inputs[index++].substring(9, 19).split(", ")).mapToInt(Integer::parseInt).toArray();
            index++;

            sample = new Instruction(instrVals);
            sampleBehavesLike = new HashSet<>();

            for (OperationType opType: OperationType.operations)
            {
                result = OperationType.execute(opType, sample, before);
                if (Arrays.equals(result, after)) { sampleBehavesLike.add(opType); }
            }

            if (sampleBehavesLike.size() >= 3) { numMatchingSamples++; }
            samplesBehaveLike.put(sample, sampleBehavesLike);
        }

        return numMatchingSamples;
    }

    int partTwo()
    {
        Set<OperationType> sampleBehavesLike;
        OperationType[] operationsByCode = new OperationType[16];
        OperationType opType;
        int numOpCodesFound = 0;

        do
        {
            for (Instruction sample : samplesBehaveLike.keySet())
            {
                sampleBehavesLike = samplesBehaveLike.get(sample);

                if (sampleBehavesLike.size() == 1)
                {
                    opType = sampleBehavesLike.iterator().next();
                    opType.code = sample.opCode;
                    operationsByCode[opType.code] = opType;
                    setOpCodeFound(opType);
                    numOpCodesFound++;
                }
            }
        }
        while (numOpCodesFound < 16);

        int[] instrVals, registers = new int[] {0, 0, 0, 0};
        Instruction instr;

        for (String input: inputs2)
        {
            instrVals = Arrays.stream(input.split(" ")).mapToInt(Integer::parseInt).toArray();
            instr = new Instruction(instrVals);
            opType = operationsByCode[instr.opCode];
            registers = OperationType.execute(opType, instr, registers);
        }

        return registers[0];
    }

    private void setOpCodeFound(OperationType opType)
    {
        System.out.println ("Operation code for " + opType.name + " = " + opType.code);
        for (Instruction sample: samplesBehaveLike.keySet()) { samplesBehaveLike.get(sample).remove(opType); }
    }
}

class Instruction
{
    int opCode;
    int inputA;
    int inputB;
    int outputC;

    Instruction(int[] instrVals)
    {
        this.opCode = instrVals[0];
        this.inputA = instrVals[1];
        this.inputB = instrVals[2];
        this.outputC = instrVals[3];
    }
}

class OperationType
{
    static OperationType[] operations = new OperationType[16];

    static
    {
        operations[0] = new OperationType("addr"); // c = r1 + r2
        operations[1] = new OperationType("addi"); // c = r + v
        operations[2] = new OperationType("mulr"); // c = r1 * r2
        operations[3] = new OperationType("muli"); // c = r * v
        operations[4] = new OperationType("banr"); // c = r1 & r2
        operations[5] = new OperationType("bani"); // c = r & v
        operations[6] = new OperationType("borr"); // c = r1 | r2
        operations[7] = new OperationType("bori"); // c = r | v

        operations[8] = new OperationType("setr"); // c = r
        operations[9] = new OperationType("seti"); // c = v

        operations[10] = new OperationType("gtir"); // c = v > r ? 1 : 0
        operations[11] = new OperationType( "gtri"); // c = r > v ? 1 : 0
        operations[12] = new OperationType("gtrr"); // c = r1 > r2 ? 1 : 0
        operations[13] = new OperationType("eqir"); // c = v == r ? 1 : 0
        operations[14] = new OperationType("eqri"); // c = r == v ? 1 : 0
        operations[15] = new OperationType("eqrr"); // c = r1 == r2 ? 1 : 0
    }

    static int[] execute(OperationType opType, Instruction instr, int[] registers)
    {
        registers = registers.clone();

        switch (opType.name)
        {
            case "addr": registers[instr.outputC] = registers[instr.inputA] + registers[instr.inputB]; break;
            case "addi": registers[instr.outputC] = registers[instr.inputA] + instr.inputB; break;
            case "mulr": registers[instr.outputC] = registers[instr.inputA] * registers[instr.inputB]; break;
            case "muli": registers[instr.outputC] = registers[instr.inputA] * instr.inputB;break;
            case "banr": registers[instr.outputC] = registers[instr.inputA] & registers[instr.inputB]; break;
            case "bani": registers[instr.outputC] = registers[instr.inputA] & instr.inputB; break;
            case "borr": registers[instr.outputC] = registers[instr.inputA] | registers[instr.inputB]; break;
            case "bori": registers[instr.outputC] = registers[instr.inputA] | instr.inputB; break;
            case "setr": registers[instr.outputC] = registers[instr.inputA]; break;
            case "seti": registers[instr.outputC] = instr.inputA; break;
            case "gtir": registers[instr.outputC] = instr.inputA > registers[instr.inputB] ? 1 : 0; break;
            case "gtri": registers[instr.outputC] = registers[instr.inputA] > instr.inputB ? 1 : 0; break;
            case "gtrr": registers[instr.outputC] = registers[instr.inputA] > registers[instr.inputB] ? 1 : 0; break;
            case "eqir": registers[instr.outputC] = instr.inputA == registers[instr.inputB] ? 1 : 0; break;
            case "eqri": registers[instr.outputC] = registers[instr.inputA] == instr.inputB  ? 1 : 0; break;
            case "eqrr": registers[instr.outputC] = registers[instr.inputA] == registers[instr.inputB] ? 1 : 0; break;
        }

        return registers;
    }

    int code = -1;
    String name;

    private OperationType(String name)
    {
        this.name = name;
    }
}

