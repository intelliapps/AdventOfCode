package advent.twentyeighteen;

import java.io.File;
import java.util.*;

public class DayThirteen extends AdventOfCode
{
    public static void main(String[] args) throws Exception
    {
        DayThirteen dayThirteen1 = new DayThirteen(new File("input/2018/DayThirteen.txt"));
        System.out.println("Day Thirteen - part 1: " + dayThirteen1.partOne());
        DayThirteen dayThirteen2 = new DayThirteen(new File("input/2018/DayThirteen.txt"));
        System.out.println("Day Thirteen - part 2: " + dayThirteen2.partTwo());
    }

    private char[][] network;
    private SortedMap<Integer, Cart> carts = new TreeMap<>();

    private DayThirteen(File inputFile) throws Exception
    {
        super(inputFile);
        init();
    }

    DayThirteen(String inputText, String separator)
    {
        super(inputText, separator);
        init();
    }

    String partOne()
    {
        return calculate(true);
    }

    String partTwo()
    {
        return calculate(false);
    }

    private String calculate(boolean stopOnCrash)
    {
        SortedMap<Integer, Cart> newCarts;
        Integer newCartLocation;
        int tickNumber = 1;

        do
        {
            Set<Integer> cartLocations = new HashSet<>(carts.keySet());
            newCarts = new TreeMap<>();
            Cart cart;

            for (Integer oldCartLocation: carts.keySet())
            {
                // An earlier cart crashed into this later one before it had a chance to move, so skip it...
                if (!cartLocations.contains(oldCartLocation)) { continue; }

                cart = carts.get(oldCartLocation);
                cart.move();
                newCartLocation = cart.r * 1000 + cart.c;

                if (cartLocations.contains(newCartLocation))
                {
                    if (stopOnCrash) { return cart.toString(); }
                    cartLocations.remove(oldCartLocation);
                    cartLocations.remove(newCartLocation);
                    newCarts.remove(newCartLocation);
                    System.out.println("" + tickNumber + ": Crash at " + cart + ", " + cartLocations.size() + " carts left");
                }
                else
                {
                    cartLocations.remove(oldCartLocation);
                    cartLocations.add(newCartLocation);
                    newCarts.put(newCartLocation, cart);
                }
            }

            carts = newCarts;
            tickNumber++;
        }
        while(carts.size() > 1);

        return carts.values().iterator().next().toString();
    }

    private void init()
    {
        int maxLineLength = 0, locationOffset;
        for (String input: inputs) { maxLineLength = Math.max(maxLineLength, input.length()); }
        network = new char[inputs.length][maxLineLength];
        char inputChar;
        for (int row = 0; row < inputs.length; row++)
        {
            locationOffset = row * 1000;
            for (int column = 0; column < maxLineLength; column++)
            {
                inputChar = column < inputs[row].length() ? inputs[row].charAt(column) : ' ';
                network[row][column] = inputChar;

                if (inputChar == '<' || inputChar == '>' || inputChar == '^' || inputChar == 'v')
                {
                    carts.put(locationOffset + column, new Cart(row, column, inputChar));
                    network[row][column] = (inputChar == '<' || inputChar == '>') ? '-' : '|';
                }
            }
        }
    }

    private void printNetwork()
    {
        char[][] networkCopy = new char[network.length][network[0].length];
        for (int row = 0; row < network.length; row++)
        { for (int col = 0; col < network[row].length; col++) { networkCopy[row][col] = network[row][col]; } }
        for (Cart aCart: carts.values()) { networkCopy[aCart.r][aCart.c] = aCart.facing; }
        for (char[] row: networkCopy) { System.out.println(row); }
        System.out.println();
    }

    class Cart
    {
        int c;
        int r;
        char facing;
        int turnNumber = 0;

        Cart(int row, int column, char facing) { this.c = column; this.r = row; this.facing = facing; }
        public String toString() { return "" + c + "," + r; }

        void move()
        {
            switch (facing)
            {
                case '<': c -= 1; break;
                case '>': c += 1; break;
                case '^': r -= 1; break;
                case 'v': r += 1; break;
            }

            if (network[r][c] == '+')
            {
                if (turnNumber == 0) { turnLeft(); }
                else if (turnNumber == 2) { turnRight(); }
                turnNumber = turnNumber < 2 ? turnNumber+1 : 0;
            }
            else if ((network[r][c] == '/' && (facing == '<' || facing == '>'))
                    || (network[r][c] == '\\' && (facing == '^' || facing == 'v'))) { turnLeft(); }
            else if ((network[r][c] == '/' && (facing == '^' || facing == 'v'))
                    || (network[r][c] == '\\' && (facing == '<' || facing == '>'))) { turnRight(); }
        }

        private void turnLeft()
        {
            switch (facing)
            {
                case '<': facing = 'v'; break;
                case '>': facing = '^'; break;
                case '^': facing = '<'; break;
                case 'v': facing = '>'; break;
            }
        }

        private void turnRight()
        {
            switch (facing)
            {
                case '<': facing = '^'; break;
                case '>': facing = 'v'; break;
                case '^': facing = '>'; break;
                case 'v': facing = '<'; break;
            }
        }
    }
}
