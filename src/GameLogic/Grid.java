package GameLogic;

public class Grid {

    private Cell[][] grid;

    public Grid(int rows, int cols) {
        this.grid = new Cell[rows][cols];
        for(int rw = 0; rw < rows; rw++) {
            for(int cl = 0; cl < cols; cl++) {
                this.grid[rw][cl] = new Cell();
            }
        }
    }

}
