/**
 * Class representing the status of a game.
 */
public class GameStatus {

    /**
     * Indicates an ongoing game
     */
    public static final byte ONGOING = 0;

    /**
     * Indicates a win
     */
    public static final byte WON = 1;

    /**
     * Indicates a loss
     */
    public static final byte LOST = 2;

    /**
     * The status of this game (ONGOING/WON/LOST)
     */
    public final byte status;

    /**
     * The number of bonuses that were collected
     */
    public final int bonusesCollected;

    /**
     * Construct a new GameResult.
     * @param status The status of this game
     * @param bonusesCollected The number of bonuses thus far acquired
     * @return a GameResult object with the specified properties
     */
    public GameResult(byte status, int bonusesCollected) {
        this.status = status;
        this.bonusesCollected = bonusesCollected;
    }
}
