package battleship;


import java.util.ArrayList;

/**
 * Base class of ships.
 */
public abstract class BaseShip {
    /**
     * Array of cells that are occupied by the ship.
     */
    protected Field[] parts;
    /**
     * Name of the ship.
     */
    protected final String name;
    /**
     * Number of cells that are occupied by the ship.
     */
    protected byte size;

    /**
     * Base-class constructor.
     */
    public BaseShip() {
        name = this.getClass().getSimpleName();
    }

    public void SetParts(ArrayList<Field> fields) {
        for (int i = 0; i < fields.size(); ++i) {
            fields.get(i).SetShip(this);
            parts[i] = fields.get(i);
        }
    }

    /**
     * Method, which checks hp of ship.
     *
     * @return false, if ship is sunk.
     */
    public boolean IsAlreadySunk() {
        for (var item : parts) {
            if (item.GetStatus() != Status.FIRED_HIT) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method, which changes statuses of all parts.
     *
     * @param aStatus new status for cells that are occupied by the ship.
     */
    public void ChangeStatusOfParts(Status aStatus) {
        for (var item : parts) {
            item.SetStatus(aStatus);
        }
    }

    /**
     * Method, which returns name of the ship.
     *
     * @return name of the ship.
     */
    public String GetName() {
        return name;
    }

    /**
     * Method for getting the size.
     *
     * @return size of the ship.
     */
    public byte GetSize() {
        return size;
    }
}
