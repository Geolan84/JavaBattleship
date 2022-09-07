package battleship;

/**
 * Class of the field of the sea.
 */
public class Field {
    /**
     * Ship,which occupy the field.
     */
    private BaseShip ship;
    /**
     * X-coordinate of the field (row).
     */
    private final int x;
    /**
     * Y-coordinate of the field (column).
     */
    private final int y;
    /**
     * Status of the field.
     */
    private Status status;

    /**
     * Constructor of the field (field is the cell in sea).
     *
     * @param x is number of row.
     * @param y is number of column.
     */
    Field(int x, int y) {
        this.x = x;
        this.y = y;
        ship = null;
        status = Status.NOT_FIRED;
    }

    /**
     * Method checks: the field is a part of a ship or no.
     *
     * @return true, if field is not a part of a ship.
     */
    public boolean ShipIsEmpty() {
        return ship == null;
    }

    /**
     * Method gives the x-coordinate of the field.
     *
     * @return number of the row, which contains the field.
     */
    public int GetX() {
        return x;
    }

    /**
     * Method gives the status of the field from enum Status.
     *
     * @return status of the field.
     */
    public Status GetStatus() {
        return status;
    }

    /**
     * Method gives the y-coordinate of the field.
     *
     * @return number of the column, which contains the field.
     */
    public int GetY() {
        return y;
    }

    /**
     * Method sets the ship of the field.
     *
     * @param aShip is the ship, which occupy the field.
     */
    public void SetShip(BaseShip aShip) {
        ship = aShip;
    }

    /**
     * Method gives the ship of the field.
     *
     * @return ship, which occupy the field.
     */
    public BaseShip GetShip() {
        return ship;
    }

    /**
     * Method sets the status of the field.
     *
     * @param aStatus is the new status of the field.
     */
    public void SetStatus(Status aStatus) {
        status = aStatus;
    }

    /**
     * Method returns the string imagination of the status of the field.
     *
     * @return symbol of the field.
     */
    public String GetSign() {
        return switch (status) {
            case NOT_FIRED -> "?";
            case FIRED_MISS -> "*";
            case FIRED_HIT -> "X";
            case SUNK -> "#";
        };
    }
}

/**
 * Enum with 4 statuses of the cell.
 */
enum Status {
    /**
     * This cell wasn't fired.
     */
    NOT_FIRED,
    /**
     * This cell was fired, but it isn't a part of ship.
     */
    FIRED_MISS,
    /**
     * This cell is a part of the ship, but ship isn't sunk.
     */
    FIRED_HIT,
    /**
     * This cell was fired, and it is a part of the sunk ship.
     */
    SUNK
}
