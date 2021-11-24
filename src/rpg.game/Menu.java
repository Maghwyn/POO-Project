package rpg.game;

import java.util.Scanner;

public class Menu {
    Character character = new Character();

    public void MenuWelcome() {
        System.out.println("Welcome to the game !\nPlease select the options below to start the adventure.");
        displayMenu();
    }

    public int displayMenu(){
        System.out.print(
                """
                                        
                +-------------------------------+------------------------------+\s
                |  1. Create Default Character  |  5. Remove A Character       |
                |  2. Create Warrior Character  |  6. Display Characters List  |
                |  3. Create Mage Character     |  7. Fight For Glory          |
                |  4. Create Thief Character    |  8. Exit The Game            |
                +-------------------------------+------------------------------+\s
                """
        );

        System.out.print("Your choice : ");
        Scanner input = new Scanner(System.in);
        int inputChoice = Integer.parseInt(input.nextLine());
        return choseOption(inputChoice);
    }

    public int choseOption(int input){
        switch (input){
            case 1 -> create_Peasant();
            case 2 -> create_Peasant();
            case 3 -> create_Mage();
            case 4 -> create_Peasant();
            case 5 -> menuRemove();
            case 6 -> {
                System.out.println("\nDisplaying the characters list..");
                character.display_list();
                display_submenu();
            }
            case 7 -> fightToTheDeath();
            case 8 -> System.exit(0);
            case default -> System.out.println("Error.");
        }
        return displayMenu();
    }

    private void create_Peasant() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the new Peasant : ");
        String name = input.nextLine();
        character.add_character(new Peasant(name, 2, 10, 4));
    }

    private void create_Mage() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the new Mage : ");
        String name = input.nextLine();
        character.add_character(new Mage(name, 2, 10, 4));
    }

    public int display_submenu() {
        System.out.print("\nWould you like to get detailed information about a specific character ? Y/N : ");
        Scanner confirmChoice = new Scanner(System.in);
        String choice = confirmChoice.nextLine();

        if (choice.matches("(?i).*" + "yes|y" + ".*") ) getCharacterInformation();
        else if (choice.matches("(?i).*" + "no|n" + ".*")) System.out.println("Exiting the list menu..");
        return displayMenu();
    }

    public int getCharacterInformation() {
        System.out.print("Please select the ID of the character you seek details : ");
        Scanner optionID = new Scanner(System.in);

        int characterID = Integer.parseInt(optionID.nextLine());
        int verifiedID = character.doesCharacterExist(characterID);
        if(verifiedID == -1) {
            System.out.println("The character ID provided doesn't seem to exist.\n");
            return getCharacterInformation();
        }

        System.out.println(character.list.get(verifiedID));
        return display_submenu();
    }

    public int menuRemove() {
        Scanner input = new Scanner(System.in);
        System.out.print("If you wish to cancel the operation, please type : exit\n" +
                "Otherwise please select the ID of the character you want to remove : ");

        String option = input.nextLine();
        if(option.matches("(?i).*" + "exit" + ".*")) {
            System.out.println("Exiting the remove option..");
            return displayMenu();
        }

        int characterID = Integer.parseInt(option);
        int verifiedID = character.doesCharacterExist(characterID);
        if(verifiedID == -1) {
            System.out.println("The character ID provided doesn't seem to exist.\n");
            return menuRemove();
        }

        Scanner warningConfirmation = new Scanner(System.in);
        System.out.print("Are you sure you want to delete this character ? Y/N : ");

        String confirmChoice = warningConfirmation.nextLine();
        if(confirmChoice.matches("(?i).*" + "no|n" + ".*")) return menuRemove();
        else if (confirmChoice.matches("(?i).*" + "yes|y" + ".*")) {
            System.out.println("Removing " + character.list.get(verifiedID) + " from the list..");
            character.removeCharacter(verifiedID);
        }
        return displayMenu();
    }

    public void fightToTheDeath() {
        Scanner input1 = new Scanner(System.in);
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
