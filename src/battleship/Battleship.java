package battleship;

public class Battleship extends BaseShip {
    Battleship() {
        super();
        size = 4;
        parts = new Field[size];
    }
}
