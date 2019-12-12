package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

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
        return findMaxThrusterSignal(new int[] {0,1,2,3,4});
    }

    int partTwo() throws Exception
    {
        return findMaxThrusterSignal(new int[] {5,6,7,8,9});
    }

    int findMaxThrusterSignal(int[] phaseValues) throws Exception
    {
        generatePhaseSettingPermutations(new int[]{}, phaseValues);
        int maxThrusterSignal = 0;

        for (int[] phaseSetting: phaseSettingPermutations)
        {
            System.out.println("Trying phase setting " + Arrays.toString(phaseSetting));
            maxThrusterSignal = Math.max(maxThrusterSignal, tryPhaseSetting(phaseSetting));
        }

        return maxThrusterSignal;
    }

    int tryPhaseSetting(int[] phaseSetting) throws Exception
    {
        final SynchronousQueue<Integer> ampInput1, ampInput2, ampInput3, ampInput4, ampInput5;
        final SynchronousQueue<Integer> ampOutput1, ampOutput2, ampOutput3, ampOutput4, ampOutput5;
        final IntCodeComputer amp1, amp2, amp3, amp4, amp5;

        ampInput1 = new SynchronousQueue<>();
        ampOutput1 = new SynchronousQueue<>();
        amp1 = new IntCodeComputer("Amp1", program.clone());

        ampInput2 = ampOutput1;
        ampOutput2 = new SynchronousQueue<>();
        amp2 = new IntCodeComputer("Amp2", program.clone());

        ampInput3 = ampOutput2;
        ampOutput3 = new SynchronousQueue<>();
        amp3 = new IntCodeComputer("Amp3", program.clone());

        ampInput4 = ampOutput3;
        ampOutput4 = new SynchronousQueue<>();
        amp4 = new IntCodeComputer("Amp4", program.clone());

        ampInput5 = ampOutput4;
        ampOutput5 = ampInput1;
        amp5 = new IntCodeComputer("Amp5", program.clone());

        new Thread(() -> { try { amp1.runProgram(ampInput1, ampOutput1); } catch(Exception ex) { ex.printStackTrace();} }).start();
        new Thread(() -> { try { amp2.runProgram(ampInput2, ampOutput2); } catch(Exception ex) { ex.printStackTrace();}  }).start();
        new Thread(() -> { try { amp3.runProgram(ampInput3, ampOutput3); } catch(Exception ex) { ex.printStackTrace();}  }).start();
        new Thread(() -> { try { amp4.runProgram(ampInput4, ampOutput4); } catch(Exception ex) { ex.printStackTrace();}  }).start();
        new Thread(() -> { try { amp5.runProgram(ampInput5, ampOutput5); } catch(Exception ex) { ex.printStackTrace();}  }).start();

        ampInput1.put(phaseSetting[0]);
        ampInput2.put(phaseSetting[1]);
        ampInput3.put(phaseSetting[2]);
        ampInput4.put(phaseSetting[3]);
        ampInput5.put(phaseSetting[4]);

        ampInput1.put(0);

        Thread.sleep(10); // why does this work??!!

        return ampOutput5.take();
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
