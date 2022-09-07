package battleship;

/**
 * Cruiser class.
 */
public class Cruiser extends BaseShip {
    /**
     * Cruiser's constructor.
     */
    Cruiser() {
        super();
        size = 3;
        parts = new Field[size];
    }
}
