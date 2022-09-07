package battleship;

/**
 * Submarine's class.
 */
public class Submarine extends BaseShip {
    /**
     * Constructor of the submarine.
     */
    Submarine() {
        super();
        size = 1;
        parts = new Field[size];
    }
}
