package rpg.game;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    Character character = new Character();

    public void MenuWelcome() throws InterruptedException {
        System.out.println("Welcome to the game !\n" +
                "Please select the options below to start the adventure.");
        displayMenu();
    }

    public void displayMenu() throws InterruptedException {
        System.out.print(
                """
                                        
                +------------------------------+-----------------------------+\s
                |  1. Create Default           |  8.  Fight For Glory         |
                |  2. Create Warrior           |                              |
                |  3. Create Mage              |                              |
                |  4. Create Thief             |  9.  Display the menu        |
                |  5. Create WarriorMage       |  10. Save The Game           |
                |  6. Remove Character         |  11. Load Game Save          |
                |  7. Display Characters List  |  12. Exit The Game           |
                +------------------------------+-----------------------------+\s
                """
        );

        awaitChoice();
    }

    public int awaitChoice() throws InterruptedException {
        // Await a menu choice.
        System.out.print("\nYour choice : ");
        Scanner input = new Scanner(System.in);
        int inputChoice = IntegerInput(input);
        return choseOption(inputChoice);
    }

    public int choseOption(int choice) throws InterruptedException {
        // Based on the input, do something related to the option.
        switch (choice){
            case 0 -> awaitChoice();
            case 1 -> createCharacter("Peasant");
            case 2 -> createCharacter("Warrior");
            case 3 -> createCharacter("Mage");
            case 4 -> createCharacter("Thief");
            case 5 -> createCharacter("WarriorMage");
            case 6 -> { isCharactersInList(0); character.display_list(); System.out.println(" "); menuRemove(); }
            case 7 -> { isCharactersInList(0); character.display_list(); display_submenu(); }
            case 8 -> { isCharactersInList(1); character.display_list(); System.out.println(" "); fightForGlory(); }
            case 9 -> displayMenu();
            case 10 -> saveGame();
            case 11 -> loadGame();
            case 12 -> System.exit(0);
            case default -> System.out.println("This command doesn't exist, please try again.");
        }
        return awaitChoice();
    }

    public void saveGame() {
        // This method will call the SaveManager class and write the characters list into the file JSON.txt.
        System.out.println("Saving the game..");
        SaveManager.saveForEach(character);
        System.out.println("Saved");
    }

    public void loadGame() throws InterruptedException {
        /* This method will call the SaveManager class and read the file JSON.txt.
         * @return : "NotFound" the save file do not have any content.
         * @return : "Invalid" the save file do not met the condition to be imported.
         */

        System.out.println("Loading the save file..");
        String JSON = SaveManager.readFile();
        if(Objects.equals(JSON, "NotFound")) {
            System.out.println("The save file is empty.\nCancelling..");
            awaitChoice();
            return;
        }else if(Objects.equals(JSON, "Invalid")) {
            System.out.println("The save file is not valid.\nCancelling..");
            return;
        }
        SaveManager.processingExtraction(JSON, character);
        System.out.println("Loaded");
    }

    private void createCharacter(String role) throws InterruptedException {
        /* This method will ask the user if he wants to create his own character or not.
         *  If user choice is YES : Ask a series of questions used to create the character.
         *  If user choice is NO : Then load a pre-made character based on the role.
         *  @role : This String value is based on the user option, if it was 1 it would be Peasant, 2 Warrior...
         */

        System.out.print("Do you want to custom your character ? Y/N : ");
        Scanner choice = new Scanner(System.in);
        String isCustom = choice.nextLine();

        System.out.print("Enter the name of the new " + role + " : ");
        Scanner new_character = new Scanner(System.in);
        String name = new_character.nextLine();

        int ID = character.list.size();
        int verifiedID = character.doesCharacterExist(ID);
        while(verifiedID != -1) {
            ID = ID + 1;
            verifiedID = character.doesCharacterExist(ID);
        }

        if (isCustom.matches("(?i).*" + "yes|y" + ".*") ) {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the attack damages of the character " + name + " : ");
            int AD = IntegerInput(input);
            System.out.print("Enter the health points of the character " + name + " : ");
            int HP = IntegerInput(input);
            System.out.print("Enter the initiative of the character " + name + " : ");
            int speed = IntegerInput(input);

            switch (role) {
                case "Peasant" -> character.add_character(new Peasant(ID, name, AD, HP, speed));
                case "Warrior" -> {
                    System.out.print("Enter the shield strength of the character " + name + " : ");
                    int shield = IntegerInput(input);
                    character.add_character(new Warrior(ID, name, AD, shield, HP, speed));
                }
                case "Mage" -> {
                    System.out.print("Enter the magic damages of the character " + name + " : ");
                    int magic = IntegerInput(input);
                    character.add_character(new Mage(ID, name, AD, magic, HP, speed));
                }
                case "Thief" -> {
                    System.out.print("Enter the critical chance (0 to 1) of the character " + name + " : ");
                    String criticalChanceStr = input.next();
                    float criticalChance = FloatInput(criticalChanceStr);
                    System.out.print("Enter the agility (0 to 1) of the character " + name + " : ");
                    String agilityStr = input.next();
                    float agility = FloatInput(agilityStr);
                    character.add_character(new Thief(ID, name, AD, criticalChance, HP, agility, speed));
                }
                case "WarriorMage" -> {
                    System.out.print("Enter the magic damages of the character " + name + " : ");
                    int magic = IntegerInput(input);
                    System.out.print("Enter the shield strength of the character " + name + " : ");
                    int shield = IntegerInput(input);
                    character.add_character(new WarriorMage(ID, name, AD, magic, shield, HP, speed));
                }
            }
        }
        else if (isCustom.matches("(?i).*" + "no|n" + ".*")) {
            switch (role) {
                case "Peasant" ->     character.add_character(new Peasant(ID, name, random_int(2,3), random_int(10,20), random_int(3,5)));
                case "Warrior" ->     character.add_character(new Warrior(ID, name, random_int(4,6), random_int(2,5), random_int(15,30), random_int(1,2)));
                case "Mage" ->        character.add_character(new Mage(ID, name, random_int(2,3), random_int(5,10), random_int(10,15), random_int(2,3)));
                case "Thief" ->       character.add_character(new Thief(ID, name, random_int(3,5), random_float(0,1), random_int(10,15), random_float(0,1), random_int(4,6)));
                case "WarriorMage" -> character.add_character(new WarriorMage(ID, name, random_int(4,6), random_int(5,10), random_int(2,5), random_int(10,25), random_int(3,5)));
            }
        }
    }

    public int menuRemove() throws InterruptedException {
        /* This method will await the user choice, exit will quit this option.
         *  If the user choice is a character ID, a verification of the ID will be undergoing.
         *  If this ID is not found, the question for an ID will be asked again.
         *  Once the confirmation of the ID is valid, a warning message asking if you are sure to proceed will pop.
         *  If user choice is NO : Exit the option.
         *  If user choice is YES : Removing the character from the list.
         */

        Scanner input = new Scanner(System.in);
        System.out.print("""

                If you wish to cancel the operation, please type : exit
                Otherwise please select the ID of the character you want to remove :\s""");

        String option = input.nextLine();
        if(option.matches("(?i).*" + "exit" + ".*")) {
            System.out.println("Exiting the remove option..");
            return awaitChoice();
        }

        int characterID;
        try {
            characterID = Integer.parseInt(option);
        } catch (NumberFormatException nfe) {
            System.out.println("You provided a chain of characters but the program was waiting for a number.\nPlease try again.");
            return menuRemove();
        }

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
            System.out.println(character.list.get(verifiedID) + "Removing from the list..");
            character.removeCharacter(verifiedID);
        }
        return awaitChoice();
    }

    public int display_submenu() throws InterruptedException {
        /* This method will ask the user if he wants the details of a specific character.
         *  If user choice is NO : Exit the option.
         *  If user choice is YES : Calling the getCharacterInformation method.
         */
        System.out.print("\nWould you like to get detailed information about a specific character ? Y/N : ");
        Scanner confirmChoice = new Scanner(System.in);
        String choice = confirmChoice.nextLine();

        if (choice.matches("(?i).*" + "yes|y" + ".*") ) getCharacterInformation();
        else if (choice.matches("(?i).*" + "no|n" + ".*")) System.out.println("Exiting the list menu..");
        return awaitChoice();
    }

    public int getCharacterInformation() throws InterruptedException {
        /* This method await a character ID and a verification of the ID will be undergoing.
         *  If this ID is not found, the question for an ID will be asked again.
         *  Once the confirmation of the ID is valid, the details of the character will be displayed.
         */
        System.out.print("Please select the ID of the character you seek details : ");
        Scanner optionID = new Scanner(System.in);

        int characterID = IntegerInput(optionID);
        int verifiedID = character.doesCharacterExist(characterID);
        if(verifiedID == -1) {
            System.out.println("The character ID provided doesn't seem to exist.\n");
            return getCharacterInformation();
        }

        System.out.println(character.list.get(verifiedID));
        return display_submenu();
    }

    public void fightForGlory() throws InterruptedException {
        // This method will ask for 2 characters, these 2 characters will then be identified to proceed further.

        int selectedFighters = 0;
        int[] verifiedID    = new int[2];
        int[] healthPoints  = new int[2];
        int[] initiative    = new int[2];
        String[] name       = new String[2];
        String[] className  = new String[2];
        Character character2 = new Character();
        Character[] fighter = new Character[2];

        while(selectedFighters != 2) {
            // This loop will await for 2 valid characters ID.
            // If a character ID verified doesn't exit, the loop will ask the question again.
            // Once the character is valid, the character object will be then recreated in another Character class
            // Instance to avoid any modification from the main list.
            // Also, the value of the cloned characters will be used for the fight and the fight alone.

            if(selectedFighters == 0) System.out.print("Chose your first fighter : ");
            if(selectedFighters == 1) System.out.print("Chose your second fighter : ");

            Scanner thisFighter = new Scanner(System.in);
            int characterID = IntegerInput(thisFighter);
            verifiedID[selectedFighters] = character.doesCharacterExist(characterID);
            if(verifiedID[selectedFighters] == -1) {
                System.out.println("The character ID provided doesn't seem to exist.\n");
                continue;
            }

            Character clone = character.getFighter(characterID);
            String role = clone.className;
            switch (role) {
                case "Peasant"     : character2.list.add(new Peasant(clone.indexID, clone.name, clone.attackDamages, clone.healthPoints, clone.initiative));
                case "Warrior"     : character2.list.add(new Warrior(clone.indexID, clone.name, clone.attackDamages, clone.getShield(), clone.healthPoints, clone.initiative));
                case "Mage"        : character2.list.add(new Mage(clone.indexID, clone.name, clone.attackDamages, clone.getMagicDamages(), clone.healthPoints, clone.initiative));
                case "Thief"       : character2.list.add(new Thief(clone.indexID, clone.name, clone.attackDamages, clone.getCriticalChance(), clone.healthPoints,clone.getAgility(), clone.initiative));
                case "WarriorMage" : character2.list.add(new WarriorMage(clone.indexID, clone.name, clone.attackDamages,clone.getMagicDamages(), clone.getShield(), clone.healthPoints, clone.initiative));
            }

            fighter[selectedFighters]      = character2.getFighter(characterID);
            healthPoints[selectedFighters] = fighter[selectedFighters].getHealthPoints();
            initiative[selectedFighters]   = fighter[selectedFighters].getInitiative();
            name[selectedFighters]         = fighter[selectedFighters].getCharacterName();
            className[selectedFighters]    = fighter[selectedFighters].getClassName();
            selectedFighters++;
        }

        System.out.print("\n" + name[0] + " VS " + name[1] + "\n"+ "Are-You-Ready ? FIGHT !\n" +
                "-------------------------------------------------");

        // Used to display the turn, and stop the fight once the turn reach 100.
        int nbTurn = 0;
        int nbFighter = fighter.length;
        int checkTurn = nbFighter;
        // Which character will start first.
        boolean turn = initiative[0] > initiative[1];

        while(healthPoints[0] > 0 || healthPoints[1] > 0) {
            // Binary attribution to select which fighter will be the attacker and the defender.
            // A lot of condition will be checked to attribute what action will ensure during the fight.

            int attacker = turn ? 0 : 1;
            int defender = turn ? 1 : 0;
            int damage = 0;

            if(checkTurn == nbFighter) {
                System.out.println("\n----------- Turn " + nbTurn + " begins ! -----------");
                nbTurn++;
                checkTurn = 0;
            }

            if(Objects.equals(className[attacker], "Peasant"))          damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Warrior"))     damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Mage"))        damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "WarriorMage")) damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "Thief")) {
                // Get the CC and if the Thief can crit this turn or not.
                // If the CC happen with the random method, then the CD will be on cooldown for 1 turn.

                float criticalChance = fighter[attacker].getCriticalChance();
                boolean chance = fighter[attacker].isCritical();
                damage = fighter[attacker].getAttackDamages();

                if((new Random().nextFloat() <= criticalChance) && chance) {
                    fighter[attacker].disableCritical();
                    damage = damage*2;

                } else fighter[attacker].enableCritical();
            }

            System.out.println("\n" + className[attacker] + " " + name[attacker] + " attack " + className[defender] + " " + name[defender]);
            switch (className[defender]) {
                case "Warrior", "WarriorMage" -> {
                    int shield = fighter[defender].getShield();
                    damage = damage - shield;
                    if (damage <= 0)
                        System.out.println(className[defender] + " " + name[defender] + " nullified the damages with his shield.");
                    else {
                        fighter[defender].takeDamages(damage);
                        System.out.println(className[defender] + " " + name[defender] + " took " + damage + " damages");
                    }
                }
                case "Thief" -> {
                    float agility = fighter[defender].getAgility();
                    boolean miss = new Random().nextFloat() <= agility;
                    if (miss) System.out.println(className[attacker] + " " + name[attacker] + " missed");
                    else {
                        fighter[defender].takeDamages(damage);
                        System.out.println(className[defender] + " " + name[defender] + " took " + damage + " damages");
                    }
                }
                case null, default -> {
                    fighter[defender].takeDamages(damage);
                    System.out.println(className[defender] + " " + name[defender] + " took " + damage + " damages");
                }
            }

            int currentHealthPoints = fighter[defender].getHealthPoints();
            if (currentHealthPoints < 0) currentHealthPoints = 0;
            System.out.println(className[defender] + " " + name[defender] + " remaining hp is " + currentHealthPoints);

            if(currentHealthPoints <= 0) {
                System.out.println("-------------------------------------------------");
                System.out.println("Result from the battle :\n");
                System.out.print("The winner of the confrontation is " + className[attacker] + " " + name[attacker] + ", congratulations ! For now..\n");
                character.removeCharacter(verifiedID[defender]);
                break;
            }

            if(nbTurn == 101) {
                System.out.println("-------------------------------------------------");
                System.out.println("Result from the battle :\n");
                System.out.println("Luckily you both survived, no one won...");
                break;
            }

            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) {

            }

            turn = !turn;
            checkTurn++;
        }
    }

    public int IntegerInput(Scanner input) throws InterruptedException {
        // Prevent the program from crashing if the user input is a String.
        int inputVerified;
        try {
            inputVerified = Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException nfe) {
            System.out.println("You provided a chain of characters but the program was waiting for a number.\nPlease try again.");
            return awaitChoice();
        }
        return inputVerified;
    }

    public float FloatInput(String input) {
        // Prevent crash cause by the os language if the user either enter 0.3 or 0,3 when
        // the program is waiting for a float value.

        float my_float = 0;

        if(input.matches("^*" + "[.]" + "*$")) {
            String[] values = input.split("\\.");
            my_float = Float.parseFloat(values[0] + "." + values[1]);

        }

        if(input.matches(".*,.*")) {
            String[] values = input.split(",");
            my_float = Float.parseFloat(values[0] + "." + values[1]);
        }

        return my_float;
    }

    public static int random_int(int Min, int Max) {
        return (int) (Math.random()*(Max-Min))+Min;
    }

    public static float random_float(int Min, int Max) {
        return (float) ((Math.random()*(Max-Min))+Min);
    }

    public void isCharactersInList(int limit) throws InterruptedException {
        if(character.list.size() <= limit) {
            System.out.println("Not enough characters in the characters list.");
            awaitChoice();
        }
    }
}
