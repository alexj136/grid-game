package GameLogic;

public class Grid {

    private Cell[][] grid;

    private int curRow;
    private int curCol;

    private int endRow;
    private int endCol;

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
        assert((startRow < template.length)
            && (startCol < template[0].length)
            && (endRow < template.length)
            && (endCol < template[0].length)
            && (template[startRow][startCol])
            && (template[endRow][endCol])
        );

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
    
    /**
     * A little test of Grid objects.
     * TODO remove this method from final build
     * TODO fix whatever bug allows this code to run without failing the
     * assertion in Grid()
     */
    public static void main(String[] args) {
        boolean[][] arr = new boolean[][] {{true, false}, {false, true}};
        Grid g = new Grid(arr, 0, 0 , 1, 5);
        System.out.print(g);
    }
}
