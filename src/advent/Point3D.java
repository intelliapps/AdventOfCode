package advent;


public class Point3D
{
    private int px;
    private int py;
    private int pz;
    private int vx;
    private int vy;
    private int vz;

    public Point3D(int px, int py, int pz)
    {
        this (px, py, pz, 0, 0, 0);
    }

    public Point3D(int px, int py, int pz, int vx, int vy, int vz)
    {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public int getPx() { return px; }
    public int getPy() { return py; }
    public int getPz() { return pz; }

    /**
     * The total energy for a single moon is its potential energy multiplied by its kinetic energy.
     */
    public long getTotalEnergy()
    {
        return getPotentialEnergy() * getKineticEnergy();
    }

    /**
     * A moon's potential energy is the sum of the absolute values of its x, y, and z position coordinates.
     */
    public long getPotentialEnergy()
    {
        return Math.abs(px) + Math.abs(py) + Math.abs(pz);
    }

    /**
     * A moon's kinetic energy is the sum of the absolute values of its velocity coordinates.
     */
    public long getKineticEnergy()
    {
        return Math.abs(vx) + Math.abs(vy) + Math.abs(vz);
    }

    /**
     * To apply gravity, consider every pair of moons. On each axis (x, y, and z), the velocity of each moon changes
     * by exactly +1 or -1 to pull the moons together. For example, if Ganymede has an x position of 3, and Callisto
     * has a x position of 5, then Ganymede's x velocity changes by +1 (because 5 > 3) and Callisto's x velocity changes
     * by -1 (because 3 < 5). However, if the positions on a given axis are the same, the velocity on that axis does not
     * change for that pair of moons.
     */
    public void applyGravity(Point3D other)
    {
        vx += other.px > px ? 1 : other.px < px ? -1 : 0;
        vy += other.py > py ? 1 : other.py < py ? -1 : 0;
        vz += other.pz > pz ? 1 : other.pz < pz ? -1 : 0;
    }

    public void move()
    {
        px += vx;
        py += vy;
        pz += vz;
    }

    protected Point3D clone()
    {
        return new Point3D(px, py, pz, vx, vy, vz);
    }
}
