package battleship;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Class Battlefield describe the sea as an array of fields and provide all methods for game.
 */
public class Battlefield {
    /**
     * Number of the rows of battlefield.
     */
    private int rows;
    /**
     * Number of the columns of battlefield.
     */
    private int columns;
    /**
     * Number of a sunk ships.
     */
    private int amountOfSunk;
    /**
     * ArrayList of all ships on the battlefield.
     */
    private ArrayList<BaseShip> ships;
    /**
     * Battlefield as an array of fields.
     */
    private Field[][] battlefield;
    /**
     * The score of gamer.
     */
    private int score;

    /**
     * Method prints the sea and information for understanding the signs.
     */
    public void print() {
        System.out.println("Signs: ? - NOT FIRED    * - FIRED MISS    X - FIRED HIT    # - SUNK\n");
        int i;
        for (i = 0; i < rows; ++i) {
            System.out.print(i + "\t");
            for (int j = 0; j < columns; ++j) {
                System.out.print("\t" + battlefield[i][j].GetSign());
            }
            System.out.println();
        }
        System.out.println();
        int lengthOfTab = Integer.toString(i).length();
        while (lengthOfTab >= 0) {
            System.out.print(" ");
            lengthOfTab--;
        }
        System.out.print("\t");
        for (int j = 0; j < columns; ++j) {
            System.out.print("\t" + j);
        }
    }

    /**
     * Constructor of the battlefield from data, that was got from user.
     *
     * @param args describe the parameters of the battlefield.
     */
    public Battlefield(String[] args) throws IllegalArgumentException {
        if (args == null || args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("m - number of rows, n - number of columns.");
            System.out.println("x y z t s - number of carriers, battleships, " +
                    "cruisers, destroyers and submarines respectively.\n");
            System.out.println("Enter the configuration like that: \"m n x y z t s\"");
            String input = scanner.nextLine();
            args = input.split(" ");
        }
        if (args.length != 7) {
            throw new IllegalArgumentException("""
                    Incorrect number of arguments!\s
                    Please retry with other arguments! Type "exit".
                    """);
        }
        int[] data = new int[args.length];
        for (int i = 0; i < data.length; ++i) {
            try {
                data[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("""
                        Incorrect arguments in input!\s
                        Maybe something is not integer!
                        """);
            }
        }
        if (data[0] < 1 || data[1] < 1) {
            throw new IllegalArgumentException("""
                    Incorrect size of sea!\s
                    Number of rows and columns should be greater than 0!
                    """);
        }
        if (data[2] < 0 || data[3] < 0 || data[4] < 0 || data[5] < 0 || data[6] < 0) {
            throw new IllegalArgumentException("""
                    Incorrect number of ships!\s
                    Number of ships can not be less than 0!
                    """);
        }
        if (data[2] == 0 && data[3] == 0 && data[4] == 0 && data[5] == 0 && data[6] == 0) {
            throw new IllegalArgumentException("""
                    Incorrect number of ships!\s
                    Number of all ships can not be 0!
                    """);
        }
        InitialiseShips(data);
        InitialiseSea();
        if (!DistributeShips()) {
            throw new IllegalArgumentException("Unfortunately, computer can not distribute the ships. Try again!\n");
        }
        amountOfSunk = 0;
        score = 0;
    }

    /**
     * Method for creating objects of ships and setting the size of the battleship.
     *
     * @param data contains integer numbers of rows, columns and every kind of ship.
     */
    private void InitialiseShips(int[] data) {
        rows = data[0];
        columns = data[1];
        int countOfShips = data[2] + data[3] + data[4] + data[5] + data[6];
        ships = new ArrayList<>();
        ships.ensureCapacity(countOfShips);
        for (int i = 0; i < data[2]; ++i) {
            ships.add(new Carrier());
        }
        for (int i = 0; i < data[3]; ++i) {
            ships.add(new Battleship());
        }
        for (int i = 0; i < data[4]; ++i) {
            ships.add(new Cruiser());
        }
        for (int i = 0; i < data[5]; ++i) {
            ships.add(new Destroyer());
        }
        for (int i = 0; i < data[6]; ++i) {
            ships.add(new Submarine());
        }
    }

    /**
     * Method creates the battlefield as an array of fields and set these fields.
     */
    private void InitialiseSea() {
        battlefield = new Field[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                battlefield[i][j] = new Field(i, j);
                battlefield[i][j].SetShip(null);
            }
        }
    }

    /**
     * Number of the rows of battlefield.
     *
     * @param x           row for start the research.
     * @param y           column for start the research.
     * @param ship        which computer should place.
     * @param orientation true - vertical; false - horizontal
     * @return false, if ship can not be located in the area around the start field.
     */
    private boolean TryPlaceShip(boolean orientation, BaseShip ship, int x, int y) {
        // Список для хранения потенциальных ячеек.
        ArrayList<Field> place = new ArrayList<>();
        // Ориентация приходит в метод случайная, если true, то пробуем поставить вертикально.
        if (orientation) {
            // Проверяем, что ни одна ячейка вокруг выбранной (в вертикальном диапазоне size+2 х 3) не занята.
            for (int i = x - 1; i <= x + ship.GetSize(); ++i) {
                for (int j = y - 1; j <= y + 1; ++j) {
                    if (i >= 0 && i < rows && j >= 0 && j < columns) {
                        if (!battlefield[i][j].ShipIsEmpty()) {
                            return false;
                        }
                    }
                }
            }
            // Если ячейки свободны, пробуем сделать серединные частю корабля.
            for (int i = x; i < x + ship.GetSize(); i++) {
                if (i < rows) {
                    place.add(battlefield[i][y]);
                }
            }
        } else {
            // Проверка ячеек для горизонтального диапазона.
            for (int i = x - 1; i <= x + 1; ++i) {
                for (int j = y - 1; j <= y + ship.GetSize(); ++j) {
                    if (i >= 0 && i < rows && j >= 0 && j < columns) {
                        if (!battlefield[i][j].ShipIsEmpty()) {
                            return false;
                        }
                    }
                }
            }
            // Добавляем ячейки к временному массиву.
            for (int j = y; j < y + ship.GetSize(); j++) {
                if (j < columns) {
                    place.add(battlefield[x][j]);
                }
            }
        }
        // Если свободных ячеек меньше, чем нужно кораблю, вернём false.
        if (place.size() != ship.size) {
            return false;
        } else {
            //Иначе присваиваем ячейки кораблю, а ячейкам корабль.
            ship.SetParts(place);
            return true;
        }
    }

    /**
     * Method for place all ships all over the sea.
     *
     * @return true, if computer distribute all ships all over the sea.
     */
    private boolean DistributeShips() {
        Random random = new Random();
        ArrayList<Field> temp = new ArrayList<>();
        int countShips = ships.size();
        int indexOfLastShip = 0;
        boolean first;
        boolean second;
        while (temp.size() != rows * columns && countShips != 0) {
            second = false;
            // Выбираем случайную ячейку в таблице.
            Field check = battlefield[random.nextInt(rows)][random.nextInt(columns)];
            // Проверяем, рассматривали ли мы её.
            if (!temp.contains(check)) {
                // Берём корабли, начиная с начала.
                var ship = ships.get(indexOfLastShip);
                // Случайным образом выбираем, какую ориентацию выбрать первой.
                // Если один вариант не сработал, пробуем повернуть корабль.
                if (random.nextBoolean()) {
                    first = TryPlaceShip(true, ship, check.GetX(), check.GetY());
                    if (!first) {
                        second = TryPlaceShip(false, ship, check.GetX(), check.GetY());
                    }
                } else {
                    // horiz
                    first = TryPlaceShip(false, ship, check.GetX(), check.GetY());
                    if (!first) {
                        second = TryPlaceShip(true, ship, check.GetX(), check.GetY());
                    }
                }
                // Если корабль поставлен, обновляем индексы для следующего.
                if (first || second) {
                    indexOfLastShip++;
                    countShips--;
                }
                // Независимо от успеха отмечаем клетку исследованной, чтобы более её не проверять.
                temp.add(check);
            }
        }
        // Если непристроенных кораблей не осталось, вернётся true.
        return countShips == 0;
    }

    /**
     * Method for printing the start information and calling methods for game.
     */
    public void PlayTheGame() {
        System.out.println("\nNice to meet you here:)\n");
        System.out.println("Computer will draw the sea, your task is type coordinates for fire-attack on ships.\n");
        System.out.println("Press enter to continue.\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // Пока все корабли не будут уничтожены, программа будет требовать координаты и обновлять поле.
        while (amountOfSunk != ships.size()) {
            print();
            if (Fire(GetCoordinates())) {
                amountOfSunk++;
            }
        }
    }

    /**
     * Method for handling process coordinates from user's input to getting the field.
     *
     * @return field from user's input.
     */
    private Field GetCoordinates() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter coordinates of field one by one. x - row, y - column.");
        int x = 0;
        int y = 0;
        String ans;
        boolean flag = true;
        do {
            try {
                System.out.print("\nx: ");
                ans = scanner.next();
                x = Integer.parseInt(ans);
                System.out.print("y: ");
                ans = scanner.next();
                y = Integer.parseInt(ans);
                if (x >= 0 && x < rows && y >= 0 && y < columns) {
                    flag = false;
                } else {
                    System.out.println("Sea does not contains this field!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Type INTEGER coordinates one by one");
            }
        } while (flag);
        return battlefield[x][y];
    }

    /**
     * Method for check and change the status of the field.
     *
     * @param temp field, which is selected by user.
     * @return true, when user sank the ship.
     */
    private boolean Fire(Field temp) {
        score++;
        if (temp.GetStatus() == Status.NOT_FIRED) {
            if (temp.ShipIsEmpty()) {
                temp.SetStatus(Status.FIRED_MISS);
                System.out.println("You missed :(\n");
            } else {
                temp.SetStatus(Status.FIRED_HIT);
                if (temp.GetShip().IsAlreadySunk()) {
                    temp.GetShip().ChangeStatusOfParts(Status.SUNK);
                    System.out.println("You just have sunk a " + temp.GetShip().GetName() + "\n");
                    return true;
                } else {
                    System.out.println("You hit the ship!\n");
                }
            }
        }
        return false;
    }

    /**
     * Method prints the score to console.
     */
    public void PrintResult() {
        System.out.println("Your score = " + score);
    }
}
