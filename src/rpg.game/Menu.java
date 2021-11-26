package rpg.game;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

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
                                        
                +------------------------------+-----------------------------+\s
                |  1. Create Default           |  8.  Fight For Glory         |
                |  2. Create Warrior           |  9.  Team Fight              |
                |  3. Create Mage              |                              |
                |  4. Create Thief             |  10.  Display the menu       |
                |  5. Create WarriorMage       |  11. Save The Game           |
                |  6. Remove Character         |  12. Load Game Save          |
                |  7. Display Characters List  |  13. Exit The Game           |
                +------------------------------+-----------------------------+\s
                """
        );

        awaitChoice();
    }

    public int awaitChoice() {
        // Await a menu choice.
        System.out.print("\nYour choice : ");
        Scanner input = new Scanner(System.in);
        int inputChoice = IntegerInput(input);
        return choseOption(inputChoice);
    }

    public int choseOption(int choice) {
        // Based on the input, do something related to the option.
        switch (choice){
            case 0 -> awaitChoice();
            case 1 -> createCharacter("Peasant");
            case 2 -> createCharacter("Warrior");
            case 3 -> createCharacter("Mage");
            case 4 -> createCharacter("Thief");
            case 5 -> createCharacter("WarriorMage");
            case 6 -> menuRemove();
            case 7 -> { character.display_list(); display_submenu(); }
            case 8 -> fightForGlory();
            case 9 -> fightPvP();
            case 10 -> displayMenu();
            case 11 -> saveGame();
            case 12 -> loadGame();
            case 13 -> System.exit(0);

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

    public void loadGame() {
        /** This method will call the SaveManager class and read the file JSON.txt.
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

    private void createCharacter(String role) {
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

            if(role.equals("Peasant")) character.add_character(new Peasant(ID, name, AD, HP, speed));
            if(role.equals("Warrior")) {
                System.out.print("Enter the shield strength of the character " + name + " : ");
                int shield = IntegerInput(input);
                character.add_character(new Warrior(ID, name, AD, shield, HP, speed));
            }
            if(role.equals("Mage")) {
                System.out.print("Enter the magic damages of the character " + name + " : ");
                int magic = IntegerInput(input);
                character.add_character(new Mage(ID, name, AD, magic, HP, speed));
            }
            if(role.equals("Thief")) {
                System.out.print("Enter the critical chance (0 to 1) of the character " + name + " : ");
                float criticalChance = 0f;
                String criticalChanceStr = input.next();
                if(criticalChanceStr.matches("^*" + "." + "*$")) {
                    String[] values = criticalChanceStr.split("\\.");
                    criticalChance = Float.parseFloat(values[0] + "." + values[1]);
                }else if(criticalChanceStr.matches(",")) {
                    String[] values = criticalChanceStr.split("\\.");
                    criticalChance = Float.parseFloat(values[0] + "." + values[1]);
                }
                System.out.print("Enter the agility (0 to 1) of the character " + name + " : ");
                float agility = 0f;
                String agilityStr = input.next();
                if(agilityStr.matches("^*" + "." + "*$")) {
                    String[] values = agilityStr.split("\\.");
                    agility = Float.parseFloat(values[0] + "." + values[1]);
                }else if(agilityStr.matches(",")) {
                    String[] values = agilityStr.split("\\.");
                    agility = Float.parseFloat(values[0] + "." + values[1]);
                }
                character.add_character(new Thief  (ID, name, AD, criticalChance, HP, agility, speed));
            }
            if(role.equals("WarriorMage")) {
                System.out.print("Enter the magic damages of the character " + name + " : ");
                int magic = IntegerInput(input);
                System.out.print("Enter the shield strength of the character " + name + " : ");
                int shield = IntegerInput(input);
                character.add_character(new WarriorMage(ID, name, AD, magic, shield, HP, speed));
            }

        }
        else if (isCustom.matches("(?i).*" + "no|n" + ".*")) {
            if(role.equals("Peasant"))     character.add_character(new Peasant    (ID, name, 3, 10, 3));
            if(role.equals("Warrior"))     character.add_character(new Warrior    (ID, name, 5, 3, 100, 1));
            if(role.equals("Mage"))        character.add_character(new Mage       (ID, name, 2, 8, 13, 2));
            if(role.equals("Thief"))       character.add_character(new Thief      (ID, name, 4, 1f,15, 0.9f, 4));
            if(role.equals("WarriorMage")) character.add_character(new WarriorMage(ID, name, 5, 8,15, 100, 3));
        }
    }

    public int menuRemove() {
        /* This method will await the user choice, exit will quit this option.
         *  If the user choice is a character ID, a verification of the ID will be undergoing.
         *  If this ID is not found, the question for an ID will be asked again.
         *  Once the confirmation of the ID is valid, a warning message asking if you are sure to proceed will pop.
         *  If user choice is NO : Exit the option.
         *  If user choice is YES : Removing the character from the list.
         */
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

    public int getCharacterInformation() {
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

    public void fightForGlory() {
        if(character.list.size() <= 1) {
            System.out.println("Not enough fighters in the characters list.");
            awaitChoice();
            return;
        } else character.display_list();

        int selectedFighters = 0;
        int[] verifiedID    = new int[2];
        int[] healthPoints  = new int[2];
        int[] initiative    = new int[2];
        String[] name       = new String[2];
        String[] className  = new String[2];
        Character character2 = new Character();
        Character[] fighter = new Character[2];

        while(selectedFighters != 2) {
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

        int nbTurn = 0;
        int nbFighter = fighter.length;
        int checkTurn = nbFighter;
        boolean turn = initiative[0] > initiative[1];

        while(healthPoints[0] > 0 || healthPoints[1] > 0) {
            int attacker = turn ? 0 : 1;
            int defender = turn ? 1 : 0;
            int damage = 0;

            if(checkTurn == nbFighter) {
                System.out.println("\n Turn " + nbTurn + " begins ! ");
                nbTurn++;
                checkTurn = 0;
            }

            if     (Objects.equals(className[attacker], "Peasant"))     damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Warrior"))     damage = fighter[attacker].getAttackDamages();
            else if(Objects.equals(className[attacker], "Mage"))        damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "WarriorMage")) damage = fighter[attacker].getEnhancedDamages();
            else if(Objects.equals(className[attacker], "Thief")) {
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
                System.out.print("The winner of the confrontation is " + className[attacker] + " " + name[attacker] + ", congratulation ! For now..\n");
                character.removeCharacter(verifiedID[defender]);
                break;
            }

            if(nbTurn == 101) {
                System.out.println("-------------------------------------------------");
                System.out.println("You both survived, no one won.");
                break;
            }

            turn = !turn;
            checkTurn++;
        }
    }

    public int IntegerInput(Scanner input) {
        // Prevent the program from crashing if the user input is a String.
        int inputVerified;
        try {
            inputVerified = Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            return awaitChoice();
        }
        return inputVerified;
    }

    public float FloatInput(Scanner input) {
        // Prevent the program from crashing if the user input is a String.
        float inputVerified;
        try {
            inputVerified = Float.parseFloat(input.nextLine());
        }
        catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            return awaitChoice();
        }
        return inputVerified;
    }

    public void fightPvP() {
//        if(character.list.size() <= 1) {
//            System.out.println("not enough team.");
//            awaitChoice();
//            return;
//        } else character.display_list();

        int selectedFighters = 0;
        int[] verifiedID    = new int[5];
        int[] healthPoints  = new int[5];
        int[] initiative    = new int[5];
        String[] name       = new String[5];
        String[] className  = new String[5];
        Character[] fighter = new Character[5];
        Character character2 = new Character();

        while(selectedFighters != 4) {
            if(selectedFighters == 0) out.print("Chose your first fighter Team 1 : ");
            if(selectedFighters == 1) out.print("Chose your second fighter Team 1 : ");
            if(selectedFighters == 2) out.print("Chose your first fighter Team 2 : ");
            if(selectedFighters == 3) out.print("Chose your second fighter Team 2 : ");

            Scanner thisFighter = new Scanner(in);
            int characterID = Integer.parseInt(thisFighter.nextLine());
            verifiedID[selectedFighters] = character.doesCharacterExist(characterID);
            if(verifiedID[selectedFighters] == -1) {
                out.println("The character ID provided doesn't seem to exist.\n");
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

            fighter[selectedFighters] = character.getFighter(characterID);
            healthPoints[selectedFighters] = fighter[selectedFighters].getHealthPoints();
            initiative[selectedFighters] = fighter[selectedFighters].getInitiative();
            name[selectedFighters] = fighter[selectedFighters].getCharacterName();
            className[selectedFighters] = fighter[selectedFighters].getClassName();
            selectedFighters++;
        }
        out.println("Team 1 figthers " +className[0] + " and  " + className[1] + " VS Team 2 figthers " + className[2] + " and " + className[3]);
        out.print("\n Team 1 figthers " + name[0] + " and " + name[1] + " VS Team 2 figthers " + name[2] + " and " + name[3] + "\n"+ "Are-You-Ready ? FIGHT !\n" +
                "-------------------------------------------------");

        int nbTurn = 0;
        int nbFighter = fighter.length;
        int checkTurn = nbFighter;
        boolean turn = initiative[0] > initiative[2];
        boolean turn1 = initiative[1] > initiative[3];

        while(healthPoints[0] > 0 || healthPoints[1] > 0 || healthPoints[2] > 0 || healthPoints[3] > 0) {
            int attacker = turn ? 0 : 2;
            int defender = turn ? 2 : 0;
            int attacker1 = turn1 ? 1 : 3;
            int defender1 = turn1 ? 3 : 1;
            int damage = 0;
            int damage1 = 0;

            if(checkTurn == nbFighter) {
                System.out.println("\n Turn " + nbTurn + " begins ! ");
                nbTurn++;
                checkTurn = 0;
            }

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
            if     (Objects.equals(className[attacker1], "Peasant")) damage1 = fighter[attacker1].getAttackDamages();
            else if(Objects.equals(className[attacker1], "Warrior")) damage1 = fighter[attacker1].getAttackDamages();
            else if(Objects.equals(className[attacker1], "Mage"))    damage1 = fighter[attacker1].getEnhancedDamages();
            else if(Objects.equals(className[attacker1], "Thief")) {
                float criticalChance = fighter[attacker1].getCriticalChance();
                boolean chance = fighter[attacker1].isCritical();
                damage1 = fighter[attacker1].getAttackDamages();
                if((new Random().nextFloat() <= criticalChance) && chance) {
                    fighter[attacker1].disableCritical();
                    damage1 = damage1*2;
                } else fighter[attacker1].enableCritical();
            }

            out.println("\n- " +name[0] + " - FIGHT 1 - " + name[2] + "- \n");
            switch (className[defender]) {
                case "Warrior", "WarriorMage" -> {
                    int shield = fighter[defender].getShield();
                    damage = damage - shield;
                    if (damage <= 0)
                        out.println("Warrior " + name[defender] + " nullified the damages with his shield to " + name[attacker]);
                    else {
                        fighter[defender].takeDamages(damage);
                        out.println("\n" + name[defender] + " took " + damage + " damages to " + name[attacker]);
                    }
                }
                case "Thief" -> {
                    float agility = fighter[defender].getAgility();
                    boolean miss = new Random().nextFloat() <= agility;

                    if (miss) out.println(className[attacker] + " " + name[attacker] + " missed to " + name[defender]);
                    else {
                        fighter[defender].takeDamages(damage);
                        out.println(name[defender] + " took " + damage + " damages to " + name[attacker]);
                    }
                }
                case null, default -> {
                    fighter[defender].takeDamages(damage);
                    out.println("\n" + name[defender] + " took " + damage + " damages to " + name[attacker]);
                }
            }

            out.println("\n- " +name[1] + " - FIGHT 2 - " + name[3] + "- \n");
            switch (className[defender1]) {
                case "Warrior", "WarriorMage" -> {
                    int shield = fighter[defender1].getShield();
                    damage1 = damage1 - shield;
                    if (damage1 <= 0)
                        out.println("Warrior " + name[defender1] + " nullified the damages with his shield to " + name[attacker1]);
                    else {
                        fighter[defender1].takeDamages(damage1);
                        out.println("\n" + name[defender1] + " took " + damage1 + " damages to " + name[attacker1]);
                    }
                }
                case "Thief" -> {
                    float agility = fighter[defender1].getAgility();
                    boolean miss = new Random().nextFloat() <= agility;

                    if (miss) out.println(className[attacker1] + " " + name[attacker1] + " missed to " + name[defender1]);
                    else {
                        fighter[defender1].takeDamages(damage1);
                        out.println(name[defender1] + " took " + damage1 + " damages to " + name[attacker1]);
                    }
                }
                case null, default -> {
                    fighter[defender].takeDamages(damage1);
                    out.println("\n" + name[defender1] + " took " + damage1 + " damages to " + name[attacker1]);
                }
            }
            int currentHealthPoints = fighter[defender].getHealthPoints();
            if (currentHealthPoints < 0) currentHealthPoints = 0;
            out.println(name[defender] + " remaining hp is " + currentHealthPoints);

            int currentHealthPoints1 = fighter[defender1].getHealthPoints();
            if (currentHealthPoints1 < 0) currentHealthPoints1 = 0;
            out.println(name[defender1] + " remaining hp is " + currentHealthPoints1);

            if(currentHealthPoints <= 0 && currentHealthPoints1 <= 0) {
                out.println("-------------------------------------------------");
                out.print("The winner of this confrontation is " + name[attacker] + "and " + name[attacker1] + ", congratulation ! For now...\n");
                character.removeCharacter(verifiedID[defender]);
                character.removeCharacter(verifiedID[defender1]);
            }
//            if(currentHealthPoints1 <= 0) {
//                out.println("-------------------------------------------------");
//                out.print("The winner of first confrontation is " + name[attacker1] + ", congratulation ! For now...\n");
//                character.removeCharacter(verifiedID[defender1]);
//            }
            if(nbTurn == 101) {
                System.out.println("-------------------------------------------------");
                System.out.println("You both survived, no one won.");
                break;
            }
            turn = !turn1;
//            turn1 = !turn1;
            checkTurn++;
        }

    }

}
