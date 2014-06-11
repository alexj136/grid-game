package GameLogic;

/**
 * Simple representation of a coordinate within a Grid, such as a start or end
 * point.
 */
public class Coord {

    public final int row;
    public final int col;

    /**
     * Construct a new Coord
     * @param row the row number
     * @param col the column number
     * @return a new Coord with specified row and column number
     */
    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Determine if two Coords are the same.
     * @param a the first Coord to compare
     * @param b the second Coord to compare
     * @return true if a and b describe the same location, false otherwise
     */
    public static boolean areSame(Coord a, Coord b) {
        return (a.row == b.row) && (a.col == b.col);
    }
}
