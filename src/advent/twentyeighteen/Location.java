package advent.twentyeighteen;

import java.util.ArrayList;
import java.util.List;

class Location
{
    int row;
    int column;

    Location(int row, int column) { this.row = row; this.column = column; }

    List<Location> getFourAdjacents(char[][] grid)
    {
        List<Location> adjacents = new ArrayList<>();
        if (row > 0) { adjacents.add(new Location(row-1, column)); }
        if (column > 0) { adjacents.add(new Location(row, column-1)); }
        if (column+1 < grid[0].length) { adjacents.add(new Location(row, column+1)); }
        if (row+1 < grid.length) { adjacents.add(new Location(row+1, column)); }
        return  adjacents;
    }

    List<Location> getEightAdjacents(char[][] grid)
    {
        List<Location> adjacents = getFourAdjacents(grid);
        if (row > 0 && column > 0) { adjacents.add(new Location(row-1, column-1)); }
        if (row > 0 && column+1 < grid[0].length) { adjacents.add(new Location(row-1, column+1)); }
        if (row+1 < grid.length && column > 0) { adjacents.add(new Location(row+1, column-1)); }
        if (row+1 < grid.length && column+1 < grid[0].length) { adjacents.add(new Location(row+1, column+1)); }
        return  adjacents;
    }
}
