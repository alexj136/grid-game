package Levels;

import java.util.ArrayList;
import java.util.Iterator;

import GameLogic.Coord;

/**
 * A representation of a game level, used to store the premade levels.
 */
public class Level {

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
    private final ArrayList<Coord> ends;

    /**
     * Determine if the given Coord is a valid end Coord.
     * @param maybeEnd the Coord that may be an end
     * @return true if the given Coord is an end, false otherwise
     */
    public boolean isEndCoord(Coord maybeEnd) {
        boolean found = false;
        Iterator<Coord> iter = this.ends.iterator();
        while((!found) && iter.hasNext()) {
            Coord nextEnd = iter.next();
            found = Coord.areSame(nextEnd, maybeEnd);
        }
        return found;
    }

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

        // Level 0
        boolean[][] level0Grid = new boolean[][] {
            {true, true, true},
            {true, true, true},
            {true, true, true}
        };
        Coord level0Start = new Coord(0, 0);
        ArrayList<Coord> level0Ends = new ArrayList<Coord>();
        level0Ends.add(new Coord(2, 2));
        Level.levels.add(new Level(level0Grid, level0Start, level0Ends));

        // Level 1
        boolean[][] level1Grid = new boolean[][] {
            {true, true, true, true},
            {true, false, true, true},
            {true, true, true, false}
        };
        Coord level1Start = new Coord(0, 0);
        ArrayList<Coord> level1Ends = new ArrayList<Coord>();
        level1Ends.add(new Coord(0, 1));
        Level.levels.add(new Level(level1Grid, level1Start, level1Ends));

        // More level code goes here...
    }
}
