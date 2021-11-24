package rpg.game;

import java.util.Scanner;

public class Menu {
    Character character = new Character();

    public void displayMenu(){
        System.out.println("Chose :");
        System.out.println("1. Quit");
        System.out.println("2. Create Default Character");
        System.out.println("3. Create Warrior Character");
        System.out.println("4. Create Mage Character");
        System.out.println("5. Create Thief Character");
        System.out.println("6. Display List");
        System.out.println("7. Fight.");

        Scanner input = new Scanner(System.in);
        int inputChoice = Integer.parseInt(input.nextLine());
        choseOption(inputChoice);

    }

    public void choseOption(int input){
        switch (input){
            case 1 -> System.exit(0);
            case 2 -> create_Peasant();
            case 3 -> create_Peasant();
            case 4 -> create_Mage();
            case 5 -> create_Peasant();
            case 6 -> {
                character.display_list();
                display_submenu();
            }
            case 7 -> { fightToTheDeath(); }
            case default -> System.out.println("Error.");
        }
    }

    private void create_Peasant() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the new character : ");
        String name = input.nextLine();
        character.add_character(new Peasant(name, 2, 10, 4));
    }

    private void create_Mage() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the new character : ");
        String name = input.nextLine();
        character.add_character(new Mage(name, 2, 10, 4));
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


    public void fightToTheDeath() {
        Scanner input1= new Scanner(System.in);
        System.out.print("Chose your first fighter : ");
        int fighter_index1 = Integer.parseInt(input1.nextLine());
        Character fighter1 = character.choseWhoWillFight(fighter_index1);

        Scanner input2 = new Scanner(System.in);
        System.out.print("Chose your second fighter : ");
        int fighter_index2 = Integer.parseInt(input2.nextLine());
        Character fighter2 = character.choseWhoWillFight(fighter_index2);


        boolean turn = true;
        while(fighter1.healthPoints > 0 || fighter2.healthPoints > 0) {
            if(turn) {
                int AD = fighter1.getAttackDamages();
                fighter2.takeDamages(AD);
                System.out.println("Fighter 2 remaining hp is " + fighter2.getHealthPoints());
                turn = false;

                if(fighter2.healthPoints <= 0) {
                    System.out.println("The winner is fighter 1.");
                    break;
                }
            }else {
                int AD = fighter2.getAttackDamages();
                fighter1.takeDamages(AD);
                System.out.println("Fighter 1 remaining hp is " + fighter1.getHealthPoints());
                turn = true;

                if(fighter1.healthPoints <= 0) {
                    System.out.println("The winner is fighter 2.");
                    break;
                }
            }
        }
    }
}
