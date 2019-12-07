package advent.twentynineteen;

import advent.AdventOfCode;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DaySix extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DaySix daySix1 = new DaySix(new File("input/2019/DaySix.txt"));
        System.out.println("Day Six - part 1: " + daySix1.partOne());
        DaySix daySix2 = new DaySix(new File("input/2019/DaySix.txt"));
        System.out.println("Day Six - part 2: " + daySix2.partTwo());
    }

    private static class Satellite
    {
        private Satellite orbits = null;
        private int depth = 0;
        Set<Satellite> orbitedBy = new HashSet<>();
        void setOrbits(Satellite orbits) { this.orbits = orbits; setDepth(orbits.depth + 1); }
        Satellite getOrbits() { return orbits; }
        void setDepth(int depth) { this.depth = depth; for(Satellite satellite:orbitedBy) { satellite.setDepth(depth+1); } }
        int getDepth() { return depth; }
        int getNumOrbits() { return (orbits != null ? 1 + orbits.getNumOrbits() : 0); }
    }

    private Map<String, Satellite> satellites = new HashMap<>();

    private DaySix(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DaySix(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    int partOne()
    {
        int totalOrbits = 0;
        for (Satellite satellite: satellites.values()) { totalOrbits += satellite.getNumOrbits(); }
        return totalOrbits;
    }

    int partTwo()
    {
        Satellite youOrbitDirect = satellites.get("YOU").getOrbits();
        Satellite santaOrbitsDirect = satellites.get("SAN").getOrbits();
        Satellite youOrbit = youOrbitDirect;
        Satellite santaOrbits = santaOrbitsDirect;

        while (youOrbit != santaOrbits)
        {
            if (youOrbit.getDepth() > santaOrbits.getDepth()) { youOrbit = youOrbit.getOrbits(); }
            else if (santaOrbits.getDepth() > youOrbit.getDepth()) { santaOrbits = santaOrbits.getOrbits(); }
            else { youOrbit = youOrbit.getOrbits(); santaOrbits = santaOrbits.getOrbits(); }
        }

        return (youOrbitDirect.getDepth() - santaOrbits.getDepth()) + (santaOrbitsDirect.getDepth() - santaOrbits.getDepth());
    }

    private void init()
    {
        Satellite com = new Satellite();
        satellites.put("COM", com);
        String[] orbit;
        Satellite orbited, orbiting;

        for (String line: inputs)
        {
            orbit = line.split("\\)");

            if (!satellites.containsKey(orbit[0])) { satellites.put(orbit[0], new Satellite()); }
            if (!satellites.containsKey(orbit[1])) { satellites.put(orbit[1], new Satellite()); }

            orbited = satellites.get(orbit[0]);
            orbiting = satellites.get(orbit[1]);

            orbited.orbitedBy.add(orbiting);
            orbiting.setOrbits(orbited);
        }
    }
}
