package GameLogic;

/**
 * Represents a single floor tile in the grid through which the player must
 * navigate.
 * @author Alex Jeffery
 * @version 0.1
 */
public class FloorTile extends Cell {

    // Has this FloorTile been visited?
    private boolean visited;

    /**
     * Construct a new FloorTile, which will not have been visited.
     * @return an unvisited FloorTile
     */
    public FloorTile() {
        this.visited = false;
    }

    /**
     * Get the status (i.e. visited or not) of this FloorTile.
     * @return true if this FloorTile is visited, false otherwise
     */
    public boolean visited() {
        return this.visited;
    }

    /**
     * Set this FloorTile as visited.
     * @throws MultipleFloorTileVisitsException thrown when called on an already
     * visited tile, which should never ever happen
     */
    public void visit() throws MultipleFloorTileVisitsException {
        if(this.visited) {
            throw new MultipleFloorTileVisitsException();
        }
        else {
            this.visited = true;
        }
    }
}
