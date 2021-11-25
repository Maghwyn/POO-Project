package rpg.game;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

public class Menu {
    Character character = new Character();

    public void MenuWelcome() {
        out.println("Welcome to the game !\n" +
                "Please select the options below to start the adventure.");
        displayMenu();
    }

    public void displayMenu(){
        out.print(
                """
                                        
                +-------------------------------+------------------------------+\s
                |  1. Create Default Character  |  7. Fight For Glory          |
                |  2. Create Warrior Character  |                              |
                |  3. Create Mage Character     |  8. Display Menu             |
                |  4. Create Thief Character    |  9. Save The Game            |
                |  5. Remove Character          |  10. Load Game Save          |
                |  6. Display Characters List   |  11. Exit The Game           |
                +-------------------------------+------------------------------+\s
                """
        );

        // !input.hasNext("^[A-Za-z]+$")
        awaitChoice();
    }

    public int awaitChoice() {
        out.print("\nYour choice : ");
        Scanner input = new Scanner(in);
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
            case 9 -> saveGame();
            case 10 -> loadGame();
            case 11 -> exit(0);
            case default -> out.println("Error.");
        }
        return awaitChoice();
    }

    public void saveGame() {
        SaveManager.saveForEach(character);
    }

    public void loadGame() {
        String JSON = SaveManager.readFile();
        SaveManager.processingExtraction(JSON, character);
    }

    private void createCharacter(String role) {
        System.out.print("Do you want to custom your character ? Y/N : ");
        Scanner choice = new Scanner(in);
        String iscustom = choice.nextLine();

        System.out.print("Enter the name of the new " + role + " : ");
        Scanner new_character = new Scanner(in);
        String name = new_character.nextLine();

        if (iscustom.matches("(?i).*" + "yes|y" + ".*") ) {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the attack damages of the character " + name + " : ");
            int AD = input.nextInt();
            System.out.print("Enter the health points of the character " + name + " : ");
            int HP = input.nextInt();
            System.out.print("Enter the initiative of the character " + name + " : ");
            int speed = input.nextInt();

            if(role.equals("Peasant")) character.add_character(new Peasant(name, AD, HP, speed));
            if(role.equals("Warrior")) {
                System.out.print("Enter the shield strength of the character " + name + " : ");
                int shield = input.nextInt();
                character.add_character(new Warrior(name, AD, shield, HP, speed));
            }
            if(role.equals("Mage")) {
                System.out.print("Enter the magic damages of the character " + name + " : ");
                int magic = input.nextInt();
                character.add_character(new Mage(name, AD, magic, HP, speed));
            }
            if(role.equals("Thief")) {
                System.out.print("Enter the critical chance (0 to 1) of the character " + name + " : ");
                float criticalChance = Float.parseFloat(String.valueOf(input.nextFloat()));
                System.out.print("Enter the agility (0 to 1) of the character " + name + " : ");
                float agility = Float.parseFloat(String.valueOf(input.nextFloat()));
                character.add_character(new Thief  (name, AD, criticalChance, HP, agility, speed));
            }
        }
        else if (iscustom.matches("(?i).*" + "no|n" + ".*")) {
            if(role.equals("Peasant")) character.add_character(new Peasant(name, 3, 10, 3));
            if(role.equals("Warrior")) character.add_character(new Warrior(name, 5, 3, 100, 1));
            if(role.equals("Mage"))    character.add_character(new Mage   (name, 2, 8, 13, 2));
            if(role.equals("Thief"))   character.add_character(new Thief  (name, 4, 1f,15, 0.9f, 4));
        }
    }

    public int menuRemove() {
        Scanner input = new Scanner(in);
        out.print("If you wish to cancel the operation, please type : exit\n" +
                "Otherwise please select the ID of the character you want to remove : ");

        String option = input.nextLine();
        if(option.matches("(?i).*" + "exit" + ".*")) {
            out.println("Exiting the remove option..");
            return awaitChoice();
        }

        int characterID = Integer.parseInt(option);
        int verifiedID = character.doesCharacterExist(characterID);
        if(verifiedID == -1) {
            out.println("The character ID provided doesn't seem to exist.\n");
            return menuRemove();
        }

        Scanner warningConfirmation = new Scanner(in);
        out.print("Are you sure you want to delete this character ? Y/N : ");

        String confirmChoice = warningConfirmation.nextLine();
        if(confirmChoice.matches("(?i).*" + "no|n" + ".*")) return menuRemove();
        else if (confirmChoice.matches("(?i).*" + "yes|y" + ".*")) {
            out.println("Removing " + character.list.get(verifiedID) + " from the list..");
            character.removeCharacter(verifiedID);
        }
        return awaitChoice();
    }

    public int display_submenu() {
        out.print("\nWould you like to get detailed information about a specific character ? Y/N : ");
        Scanner confirmChoice = new Scanner(in);
        String choice = confirmChoice.nextLine();

        if (choice.matches("(?i).*" + "yes|y" + ".*") ) getCharacterInformation();
        else if (choice.matches("(?i).*" + "no|n" + ".*")) out.println("Exiting the list menu..");
        return awaitChoice();
    }

    public int getCharacterInformation() {
        out.print("Please select the ID of the character you seek details : ");
        Scanner optionID = new Scanner(in);

        int characterID = Integer.parseInt(optionID.nextLine());
        int verifiedID = character.doesCharacterExist(characterID);
        if(verifiedID == -1) {
            out.println("The character ID provided doesn't seem to exist.\n");
            return getCharacterInformation();
        }

        out.println(character.list.get(verifiedID));
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
            if(selectedFighters == 0) out.print("Chose your first fighter : ");
            if(selectedFighters == 1) out.print("Chose your second fighter : ");

            Scanner thisFighter = new Scanner(in);
            int characterID = Integer.parseInt(thisFighter.nextLine());
            verifiedID[selectedFighters] = character.doesCharacterExist(characterID);
            if(verifiedID[selectedFighters] == -1) {
                out.println("The character ID provided doesn't seem to exist.\n");
                continue;
            }

            fighter[selectedFighters] = character.getFighter(characterID);
            healthPoints[selectedFighters] = fighter[selectedFighters].getHealthPoints();
            initiative[selectedFighters] = fighter[selectedFighters].getInitiative();
            name[selectedFighters] = fighter[selectedFighters].getCharacterName();
            className[selectedFighters] = fighter[selectedFighters].getClassName();
            selectedFighters++;
        }

        out.println(className[0] + " " + className[1]);
        out.print("\n" + name[0] + " VS " + name[1] + "\n"+ "Are-You-Ready ? FIGHT !\n" +
                "-------------------------------------------------");

        boolean turn = initiative[0] > initiative[1];

        while(healthPoints[0] > 0 || healthPoints[1] > 0) {
            int attacker = turn ? 0 : 1;
            int defender = turn ? 1 : 0;
            int damage = 0;

            if     (Objects.equals(className[attacker], "Peasant")) damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Warrior")) damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Mage"))    damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "Thief")) {
                float criticalChance = fighter[attacker].getCriticalChance();
                boolean chance = fighter[attacker].isCritical();
                damage = fighter[attacker].getAttackDamages();

                if((new Random().nextFloat() <= criticalChance) && chance) {
                    fighter[attacker].disableCritical();
                    damage = damage*2;

                } else fighter[attacker].enableCritical();
            }

            if(Objects.equals(className[defender], "Warrior")) {
                int shield = fighter[defender].getShield();
                damage = damage - shield;
                if(damage <= 0) out.println("Warrior " + name[defender] + " nullified the damages with his shield.");
                else {
                    fighter[defender].takeDamages(damage);
                    out.println("\n" + name[defender] + " took " + damage + " damages");
                }
            } else if(Objects.equals(className[defender], "Thief")) {
                float agility = fighter[defender].getAgility();
                boolean miss = new Random().nextFloat() <= agility;

                if(miss) out.println(className[attacker] + " " + name[attacker] + " missed");
                else {
                    fighter[defender].takeDamages(damage);
                    out.println(name[defender] + " took " + damage + " damages");
                }
            } else {
                fighter[defender].takeDamages(damage);
                out.println("\n" + name[defender] + " took " + damage + " damages");
            }

            int currentHealthPoints = fighter[defender].getHealthPoints();
            out.println(name[defender] + " remaining hp is " + currentHealthPoints);

            if(currentHealthPoints <= 0) {
                out.println("-------------------------------------------------");
                out.print("The winner of the confrontation is " + name[attacker] + ", congratulation ! For now..\n");
                character.removeCharacter(verifiedID[defender]);
                break;
            }

            turn = !turn;
        }
    }
}
