package rpg.game;
import java.util.Scanner;

public class Menu {
    public void displayMenu() {
        System.out.println("Chose :");
        System.out.println("1. Quit");
        System.out.println("2. Default");
        System.out.println("3. Custom");

        Scanner input = new Scanner(System.in);
        System.out.print("\n-> ");
        int input_choice = Integer.parseInt(input.nextLine());
        choseOption(input_choice);
    }

    private void choseOption(int input_choice) {
        switch (input_choice) {
            case 1 -> System.out.print("Quitting the game..\n");
            case 2 -> System.out.print("Default settings..\n");
            case 3 -> System.out.print("Custom settings..\n");
            case default -> System.out.println("Error\n");
        }
    }
}
