package GameLogic;

import java.util.ArrayList;

import Levels.Level;

public class Grid {

    public static final byte UP = 0;
    public static final byte RIGHT = 1;
    public static final byte DOWN = 2;
    public static final byte LEFT = 3;

    private Cell[][] grid;

    private int curRow;
    private int curCol;

    /**
     * Construct a new Grid from the given template.
     * @param lvl a Level object, which is a static description of a level
     * @return a new Grid object as specified by the given Level
     */
    public Grid(Level lvl) {

        int rows = lvl.grid.length;
        int cols = lvl.grid[0].length;

        this.curRow = lvl.start.row;
        this.curCol = lvl.start.row;

        // Initialise the grid member array of Cells
        this.grid = new Cell[rows][cols];
        for(int rw = 0; rw < rows; rw++) {
            for(int cl = 0; cl < cols; cl++) {

                if(lvl.grid[rw][cl]) {
                    this.grid[rw][cl] =
                        lvl.isEndCoord(new Coord(rw, cl)) ?
                                new EndFloorTile() : new FloorTile();
                }
                else {
                    this.grid[rw][cl] = new EmptySpace();
                }
            }
        }

        try {
            ((FloorTile) this.grid[lvl.start.row][lvl.start.col]).visit();
        }
        catch(MultipleFloorTileVisitsException e) {
            System.out.println("MultipleFloorTileVisitsException thrown" +
                    " visiting starting FloorTile in Grid() constructor");
        }
    }

    /**
     * Get the number of rows in this Grid.
     * @return the number of rows in this Grid
     */
    public int numRows() {
        return this.grid.length;
    }

    /**
     * Get the number of columns in this Grid.
     * @return the number of columns in this Grid
     */
    public int numCols() {
        return this.grid[0].length;
    }

    /**
     * Get the currently occupied Coord.
     * @return a Coord object representing the currently occupied Cell
     */
    public Coord curCoord() {
        return new Coord(this.curRow, this.curCol);
    }

    /**
     * Get the Cell at the specified location.
     * @param row The row of the cell to access
     * @param col The column of the cell to access
     * @return the specified cell
     */
    public Cell cellAt(int row, int col) {
        return this.grid[row][col];
    }

    /**
     * Do a move in the specified direction. If the move is not valid, throw an
     * exception.
     * @param direction The direction of the move
     */
    public void doMove(byte direction) throws InvalidMoveException {
        // Error conditions
        if(!(direction == Grid.UP || direction == Grid.RIGHT
                    || direction == Grid.DOWN || direction == Grid.LEFT)) {
            throw new IllegalArgumentException("Invalid direction value");
        }
        boolean[] dirs = this.availableMoves();
        if(!dirs[direction]) {
            throw new InvalidMoveException();
        }

        try {
            if(direction == Grid.UP) {
                ((FloorTile)this.grid[curRow - 1][curCol]).visit();
                curRow--;
            }
            else if(direction == Grid.RIGHT) {
                ((FloorTile)this.grid[curRow][curCol + 1]).visit();
                curCol++;
            }
            else if(direction == Grid.DOWN) {
                ((FloorTile)this.grid[curRow + 1][curCol]).visit();
                curRow++;
            }
            else /*direction == Grid.LEFT*/ {
                ((FloorTile)this.grid[curRow][curCol - 1]).visit();
                curCol--;
            }
        }
        catch(MultipleFloorTileVisitsException mftve) {
            throw new InvalidMoveException();
        }
    }

    /**
     * Show which Cells are available to move into with respect to the current
     * Cell.
     * @return a boolean array A of length 4 where A[0] = true if UP is an
     * available direction, false otherwise, and the same for A[1]: RIGHT, A[2]:
     * DOWN and A[3]: LEFT.
     */
    public boolean[] availableMoves() {
        boolean[] dirs = new boolean[4];
        dirs[0] = this.curRow > 0
            && this.grid[curRow - 1][curCol] instanceof FloorTile; // UP
        dirs[1] = this.curCol < this.grid[0].length - 1
            && this.grid[curRow][curCol + 1] instanceof FloorTile; // RIGHT
        dirs[2] = this.curRow < this.grid.length - 1
            && this.grid[curRow + 1][curCol] instanceof FloorTile; // DOWN
        dirs[3] = this.curCol > 0
            && this.grid[curRow][curCol - 1] instanceof FloorTile; // LEFT
        return dirs;
    }

    /**
     * Is this game over?
     * @return true if no more moves can be made, false otherwise
     */
    public boolean hasFinished() {
        boolean[] moveDirs = this.availableMoves();
        return (!moveDirs[Grid.UP]) && (!moveDirs[Grid.RIGHT])
            && (!moveDirs[Grid.DOWN]) && (!moveDirs[Grid.LEFT]);
    }

    /**
     * Determine if this is grid has been completed successfully.
     * @return true if the grid has been completed and has been completed
     * successfully, false if it has not been completed or been completed
     * unsuccessfully.
     */
    public boolean hasWon() {
        System.out.println("WARNING: hasWon() not yet implemented");
        return false;
    }

    /**
     * Get a string representation of this Grid, ususally for debugging
     * purposes.
     * @return a string representation of this Grid, ususally for debugging
     * purposes
     */
    @Override
    public String toString() {
        String s = "";
        for(int rw = 0; rw < this.grid.length; rw++) {
            for(int cl = 0; cl < this.grid[0].length; cl++) {

                Cell c = this.grid[rw][cl];

                if(c instanceof FloorTile) {
                    if(((FloorTile) c).visited()) {
                        s += "#";
                    }
                    else {
                        assert(!(((FloorTile) c).visited()));
                        s += "O";
                    }
                }

                else {
                    assert(c instanceof EmptySpace);
                    s += " ";
                }
            }
            s += "\n";
        }
        return s;
    }
}
