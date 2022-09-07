package battleship;

import java.util.Scanner;

/**
 * Class with the main, which set the sea and calls methods for interaction.
 */
public class GamePlay {
    /**
     * Start-point in a program.
     *
     * @param args is the array of arguments from command line.
     */
    public static void main(String[] args) {
        Battlefield battlefield;
        boolean flag = true;
        while (flag) {
            try {
                battlefield = new Battlefield(args);
                battlefield.PlayTheGame();
                battlefield.PrintResult();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Some fatal error handled\n");
            }
            System.out.println("\nDo you want to retry or exit? (put \"exit\" to stop or type enter to retry)");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                flag = false;
            }
        }
    }
}
