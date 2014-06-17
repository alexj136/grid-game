public class BonusTile extends FloorTile {

    public final byte entryDirection;
    public final byte exitDirection;
    
    public BonusTile(byte entryDirection, byte exitDirection) {
        super();
        this.entryDirection = entryDirection;
        this.exitDirection = exitDirection;
    }
}
