package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DaySeven extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("input/2019/DaySeven.txt");
        Scanner sc = new Scanner(inputFile);
        String inputText = sc.nextLine();

        DaySeven daySeven1 = new DaySeven(inputText, ",");
        System.out.println("Day Seven - part 1: " + daySeven1.partOne());
        DaySeven daySeven2 = new DaySeven(inputText, ",");
        System.out.println("Day Seven - part 2: " + daySeven2.partTwo());
    }

    private int[][] phaseSettingPermutations = new int[120][5];
    private int permutationNo = 0;
    private int[] program;

    DaySeven(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne() throws Exception
    {
        generatePhaseSettingPermutations(new int[]{}, new int[] {0,1,2,3,4});
        int maxThrusterSignal = 0;

        for (int[] phaseSetting: phaseSettingPermutations)
        {
            System.out.println("Trying phase setting " + Arrays.toString(phaseSetting));
            maxThrusterSignal = Math.max(maxThrusterSignal, tryPhaseSetting(phaseSetting));
        }

        return maxThrusterSignal;
    }

    int partTwo()
    {
        generatePhaseSettingPermutations(new int[]{}, new int[] {5,6,7,8,9});
        return 0;
    }

    int tryPhaseSetting(int[] phaseSetting) throws Exception
    {
        BlockingQueue<Integer> ampInput, ampOutput = null;
        IntCodeComputer amp;

        for (int index = 0; index < 5; index++)
        {
            ampInput = new LinkedBlockingQueue<>();
            ampInput.add(phaseSetting[index]);
            ampInput.add(ampOutput == null ? 0 : ampOutput.remove());
            ampOutput = new LinkedBlockingQueue<>();
            amp = new IntCodeComputer("Amp" + (index+1), program.clone());
            try { amp.runProgram(ampInput, ampOutput); } catch (Exception ex) { ex.printStackTrace(); }
        }

        return ampOutput.take();
    }

    private void init()
    {
        program = new int[inputs.length];
        for (int index = 0; index < inputs.length; index++) { program[index] = Integer.parseInt(inputs[index]); }
    }

    private void generatePhaseSettingPermutations(int[] digitsUsed, int[] digitsRemaining)
    {
        if (digitsRemaining.length == 0)
        {
            System.arraycopy(digitsUsed, 0, phaseSettingPermutations[permutationNo++], 0, digitsUsed.length);
        }

        int[] digitsUsedNext, digitsRemainingNext;
        int drNextIndex;

        for (int nextDigitIndex = 0; nextDigitIndex < digitsRemaining.length; nextDigitIndex++)
        {
            digitsUsedNext = new int[digitsUsed.length+1];
            digitsRemainingNext = new int[digitsRemaining.length-1];
            System.arraycopy(digitsUsed, 0, digitsUsedNext, 0, digitsUsed.length);
            drNextIndex = 0;

            for (int remainingDigitIndex = 0; remainingDigitIndex < digitsRemaining.length; remainingDigitIndex++)
            {
                if (remainingDigitIndex == nextDigitIndex) { digitsUsedNext[digitsUsed.length] = digitsRemaining[nextDigitIndex]; }
                else { digitsRemainingNext[drNextIndex++] = digitsRemaining[remainingDigitIndex]; }
            }

            generatePhaseSettingPermutations(digitsUsedNext, digitsRemainingNext);
        }
    }
}
