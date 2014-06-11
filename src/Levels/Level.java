package Levels;

import java.util.ArrayList;

import GameLogic.Coord;

/**
 * A representation of a game level, used to store the premade levels.
 */
public class Level {

    /**
     * Represents a FloorTile location
     */
    public static final boolean Y = true;

    /**
     * Represents an EmptySpace location
     */
    public static final boolean N = false;

    /**
     * The grid layout for this Level
     */
    public final boolean[][] grid;

    /**
     * The starting coordinate for this Level
     */
    public final Coord start;

    /**
     * All possible end coordinates for this Level
     */
    public final ArrayList<Coord> ends;

    /**
     * Construct a new Level object.
     * @param grid The grid layout for this Level
     * @param start The starting coordinate for this Level
     * @param ends All possible end coordinates for this Level
     * @return a new Level object with the specified properties
     */
    public Level(boolean[][] grid, Coord start, ArrayList<Coord> ends) {
        this.grid = grid;
        this.start = start;
        this.ends = ends;
    }

    public static final ArrayList<Level> levels = new ArrayList<Level>();
    static {

        // Level 1
        boolean[][] level1Grid = new boolean[][] {
            {true, true, true},
            {true, true, true},
            {true, true, true}
        };
        Coord level1Start = new Coord(0, 0);
        ArrayList<Coord> level1Ends = new ArrayList<Coord>();
        level1Ends.add(new Coord(2, 2));
        Level.levels.add(new Level(level1Grid, level1Start, level1Ends));

        // Level 2
    }
}
