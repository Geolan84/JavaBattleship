package battleship;

/**
 * Destroyer's class.
 */
public class Destroyer extends BaseShip {
    /**
     * Constructor of the destroyer.
     */
    Destroyer() {
        super();
        size = 2;
        parts = new Field[size];
    }
}
