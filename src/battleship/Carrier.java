package battleship;

/**
 * Class of the carrier.
 */
public class Carrier extends BaseShip {
    /**
     * Constructor of the carrier.
     */
    Carrier() {
        super();
        size = 5;
        parts = new Field[size];
    }

}
