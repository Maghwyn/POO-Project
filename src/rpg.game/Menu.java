package rpg.game;

import java.util.Scanner;

public class Menu {
    Character character = new Character();

    public void displayMenu(){
        System.out.println("Chose :");
        System.out.println("1. Quit");
        System.out.println("2. Create Default Character");
        System.out.println("3. Display List");

        Scanner input = new Scanner(System.in);
        int inputChoice = Integer.parseInt(input.nextLine());
        choseOption(inputChoice);

    }

    public void choseOption(int input){
        switch (input){
            case 1 -> System.exit(0);
            case 2 -> create_Peasant();
            case 3 -> {
                character.display_list();
                display_submenu();
            }
            case default -> System.out.println("Error.");
        }
    }

    private void create_Peasant() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the new character : ");
        String name = input.nextLine();
        character.add_character(new Peasant(name, 2, 10, 4));
    }

    public void display_submenu() {
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();
        choseOptionSubMenu(choice);
    }

    public void choseOptionSubMenu(String choice) {
        switch(choice) {
            case "Yes" -> {
                Scanner input = new Scanner(System.in);
                int index = Integer.parseInt(input.nextLine());
                character.display_info(index);
                display_submenu();
            }
            case "No" -> System.out.println("Exiting the list..");
        }
    }
}
