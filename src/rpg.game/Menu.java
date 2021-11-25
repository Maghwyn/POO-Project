package rpg.game;

import java.util.Objects;
import java.util.Scanner;

public class Menu {
    Character character = new Character();

    public void MenuWelcome() {
        System.out.println("Welcome to the game !\n" +
                "Please select the options below to start the adventure.");
        displayMenu();
    }

    public void displayMenu(){
        System.out.print(
                """
                                        
                +-------------------------------+------------------------------+\s
                |  1. Create Default Character  |  6. Display Characters List  |
                |  2. Create Warrior Character  |  7. Fight For Glory          |
                |  3. Create Mage Character     |                              |
                |  4. Create Thief Character    |  8. Display Menu             |
                |  5. Remove Character          |  9. Exit the Game            |
                +-------------------------------+------------------------------+\s
                """
        );

        awaitChoice();
    }

    public int awaitChoice() {
        System.out.print("\nYour choice : ");
        Scanner input = new Scanner(System.in);
        int inputChoice = Integer.parseInt(input.nextLine());
        return choseOption(inputChoice);
    }

    public int choseOption(int choice) {
        switch (choice){
            case 1 -> createCharacter("Peasant");
            case 2 -> createCharacter("Warrior");
            case 3 -> createCharacter("Mage");
            case 4 -> createCharacter("Thief");
            case 5 -> menuRemove();
            case 6 -> { character.display_list(); display_submenu(); }
            case 7 -> fightForGlory();
            case 8 -> displayMenu();
            case 9 -> System.exit(0);
            case default -> System.out.println("Error.");
        }
        return awaitChoice();
    }

    private void createCharacter(String role) {
        System.out.print("Enter the name of the new " + role + " : ");
        Scanner new_character = new Scanner(System.in);
        String name = new_character.nextLine();

        if(role.equals("Peasant")) character.add_character(new Peasant(name, 3, 10, 3));
        if(role.equals("Warrior")) character.add_character(new Warrior(name, 5, 3, 20, 1));
        if(role.equals("Mage"))    character.add_character(new Mage   (name, 2, 8, 13, 2));
        if(role.equals("Thief"))   character.add_character(new Thief  (name, 4, 0.7f,15, 0.3f, 4));
    }

    public int menuRemove() {
        Scanner input = new Scanner(System.in);
        System.out.print("If you wish to cancel the operation, please type : exit\n" +
                "Otherwise please select the ID of the character you want to remove : ");

        String option = input.nextLine();
        if(option.matches("(?i).*" + "exit" + ".*")) {
            System.out.println("Exiting the remove option..");
            return awaitChoice();
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
        return awaitChoice();
    }

    public int display_submenu() {
        System.out.print("\nWould you like to get detailed information about a specific character ? Y/N : ");
        Scanner confirmChoice = new Scanner(System.in);
        String choice = confirmChoice.nextLine();

        if (choice.matches("(?i).*" + "yes|y" + ".*") ) getCharacterInformation();
        else if (choice.matches("(?i).*" + "no|n" + ".*")) System.out.println("Exiting the list menu..");
        return awaitChoice();
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

    public void fightForGlory() {
        int selectedFighters = 0;
        int[] verifiedID    = new int[2];
        int[] healthPoints  = new int[2];
        int[] initiative    = new int[2];
        String[] name       = new String[2];
        String[] className  = new String[2];
        Character[] fighter = new Character[2];

        while(selectedFighters != 2) {
            if(selectedFighters == 0) System.out.print("Chose your first fighter : ");
            if(selectedFighters == 1) System.out.print("Chose your second fighter : ");

            Scanner thisFighter = new Scanner(System.in);
            int characterID = Integer.parseInt(thisFighter.nextLine());
            verifiedID[selectedFighters] = character.doesCharacterExist(characterID);
            if(verifiedID[selectedFighters] == -1) {
                System.out.println("The character ID provided doesn't seem to exist.\n");
                continue;
            }

            fighter[selectedFighters] = character.getFighter(characterID);
            healthPoints[selectedFighters] = fighter[selectedFighters].getHealthPoints();
            initiative[selectedFighters] = fighter[selectedFighters].getInitiative();
            name[selectedFighters] = fighter[selectedFighters].getCharacterName();
            className[selectedFighters] = fighter[selectedFighters].getClassName();
            selectedFighters++;
        }

        System.out.println(className[0] + " " + className[1]);
        System.out.print("\n" + name[0] + " VS " + name[1] + "\n"+ "Are-You-Ready ? FIGHT !\n" +
                "-------------------------------------------------");

        boolean turn = initiative[0] > initiative[1];

        while(healthPoints[0] > 0 || healthPoints[1] > 0) {
            int attacker = turn ? 0 : 1;
            int defender = turn ? 1 : 0;
            int damage = 0;

            if     (Objects.equals(className[attacker], "Peasant")) damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Warrior")) damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Mage"))    damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "Thief"))   damage = fighter[attacker].getAttackDamages();

            if(Objects.equals(className[defender], "Warrior")) {
                int shield = fighter[defender].getShield();
                damage = damage - shield;
                if(damage <= 0) System.out.println("Warrior " + name[defender] + " nullified the damages with his shield.");
                else {
                    fighter[defender].takeDamages(damage);
                    System.out.println("\n" + name[defender] + " took " + damage + " damages");
                }
            } else {
                fighter[defender].takeDamages(damage);
                System.out.println("\n" + name[defender] + " took " + damage + " damages");
            }

            int currentHealthPoints = fighter[defender].getHealthPoints();
            System.out.println(name[defender] + " remaining hp is " + currentHealthPoints);

            if(currentHealthPoints <= 0) {
                System.out.println("-------------------------------------------------");
                System.out.print("The winner of the confrontation is " + name[attacker] + ", congratulation ! For now..\n");
                character.removeCharacter(verifiedID[defender]);
                break;
            }
            
            turn = !turn;
        }
    }
}
