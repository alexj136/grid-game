package GameLogic;

public class Grid {

    public static final byte UP = 0;
    public static final byte RIGHT = 1;
    public static final byte DOWN = 2;
    public static final byte LEFT = 3;

    private Cell[][] grid;

    private final int startRow;
    private final int startCol;

    private int curRow;
    private int curCol;

    public final int endRow;
    public final int endCol;

    /**
     * Construct a new Grid from the given template.
     * @param template A boolean array representing the structure of the Grid to
     * be constructed.
     * @param startRow The row number of the starting Cell. Verified to be a
     * sensible value (Not out of bounds or an EmptySpace).
     * @param startCol The column number of the starting Cell
     * @param endRow The row number of the ending Cell
     * @param endCol The column number of the ending Cell
     */
    public Grid(boolean[][] template,
            int startRow, int startCol, int endRow, int endCol) {

        int rows = template.length;
        int cols = template[0].length;

        // Check that the start and end coordinates fall within the Grid
        // boundaries on non-empty Cells.
        if(!((startRow < template.length)
                && (startCol < template[0].length)
                && (endRow < template.length)
                && (endCol < template[0].length)
                && (template[startRow][startCol])
                && (template[endRow][endCol]))) {

            throw new IllegalArgumentException("Invalid start/end point "
                    + "coordinates");
        }

        this.startRow = startRow;
        this.startCol = startCol;

        this.curRow = startRow;
        this.curCol = startCol;

        this.endRow = endRow;
        this.endCol = endCol;

        // Initialise the grid member array of Cells
        this.grid = new Cell[rows][cols];
        for(int rw = 0; rw < rows; rw++) {
            for(int cl = 0; cl < cols; cl++) {
                this.grid[rw][cl] =
                    template[rw][cl] ? new FloorTile() : new EmptySpace();
            }
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
     * Get the currently occupied row.
     * @return the currently occupied row
     */
    public int curRow() {
        return this.curRow;
    }

    /**
     * Get the currently occupied column.
     * @return the currently occupied column
     */
    public int curCol() {
        return this.curCol;
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
                curRow--;
                ((FloorTile)this.grid[curRow][curCol]).visit();
            }
            else if(direction == Grid.RIGHT) {
                curCol++;
                ((FloorTile)this.grid[curRow][curCol]).visit();
            }
            else if(direction == Grid.DOWN) {
                curRow++;
                ((FloorTile)this.grid[curRow][curCol]).visit();
            }
            else /*direction == Grid.LEFT*/ {
                curCol--;
                ((FloorTile)this.grid[curRow][curCol]).visit();
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
