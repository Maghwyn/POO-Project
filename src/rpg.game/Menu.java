package rpg.game;

import java.util.*;


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
                |  2. Create Warrior           |                              |
                |  3. Create Mage              |                              |
                |  4. Create Thief             |  9.  Display the menu        |
                |  5. Create WarriorMage       |  10. Save The Game           |
                |  6. Remove Character         |  11. Load Game Save          |
                |  7. Display Characters List  |  12. Exit The Game           |
                +------------------------------+-----------------------------+\s
                """
        );

        // !input.hasNext("^[A-Za-z]+$")
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
            case 5 -> createCharacter("WarriorMage");
            case 6 -> menuRemove();
            case 7 -> { character.display_list(); display_submenu(); }
            case 8 -> fightForGlory();
            case 9 -> displayMenu();
            case 10 -> saveGame();
            case 11 -> loadGame();
            case 12 -> System.exit(0);
            case default -> System.out.println("Error.");
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
            int AD = input.nextInt();
            System.out.print("Enter the health points of the character " + name + " : ");
            int HP = input.nextInt();
            System.out.print("Enter the initiative of the character " + name + " : ");
            int speed = input.nextInt();

            if(role.equals("Peasant")) character.add_character(new Peasant(ID, name, AD, HP, speed));
            if(role.equals("Warrior")) {
                System.out.print("Enter the shield strength of the character " + name + " : ");
                int shield = input.nextInt();
                character.add_character(new Warrior(ID, name, AD, shield, HP, speed));
            }
            if(role.equals("Mage")) {
                System.out.print("Enter the magic damages of the character " + name + " : ");
                int magic = input.nextInt();
                character.add_character(new Mage(ID, name, AD, magic, HP, speed));
            }
            if(role.equals("Thief")) {
                System.out.print("Enter the critical chance (0 to 1) of the character " + name + " : ");
                float criticalChance = Float.parseFloat(String.valueOf(input.nextFloat()));
                System.out.print("Enter the agility (0 to 1) of the character " + name + " : ");
                float agility = Float.parseFloat(String.valueOf(input.nextFloat()));
                character.add_character(new Thief  (ID, name, AD, criticalChance, HP, agility, speed));
            }
            if(role.equals("WarriorMage")) {
                System.out.print("Enter the magic damages of the character " + name + " : ");
                int magic = input.nextInt();
                System.out.print("Enter the shield strength of the character " + name + " : ");
                int shield = input.nextInt();
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
        //We need to add a clone so the stats aren't definitive.
        int selectedFighters = 0;
        int[] verifiedID    = new int[2];
        int[] healthPoints  = new int[2];
        int[] initiative    = new int[2];
        String[] name       = new String[2];
        String[] className  = new String[2];
        Character[] fighter = new Character[2];

        character.display_list();

        Character character2 = new Character();

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

            Character clone = character.getFighter(characterID);
            String role = clone.className;
            switch (role) {
                case "Peasant"     : character2.list.add(new Peasant(clone.indexID, clone.name, clone.attackDamages, clone.healthPoints, clone.initiative));
                case "Warrior"     : character2.list.add(new Warrior(clone.indexID, clone.name, clone.attackDamages, clone.getShield(), clone.healthPoints, clone.initiative));
                case "Mage"        : character2.list.add(new Mage(clone.indexID, clone.name, clone.attackDamages, clone.getEnhancedDamages(), clone.healthPoints, clone.initiative));
                case "Thief"       : character2.list.add(new Thief(clone.indexID, clone.name, clone.attackDamages, clone.getCriticalChance(), clone.healthPoints,clone.getAgility(), clone.initiative));
                case "WarriorMage" : character2.list.add(new WarriorMage(clone.indexID, clone.name, clone.attackDamages,clone.getEnhancedDamages(), clone.getShield(), clone.healthPoints, clone.initiative));

            }
            fighter[selectedFighters]      = character2.getFighter(characterID);
            healthPoints[selectedFighters] = fighter[selectedFighters].getHealthPoints();
            initiative[selectedFighters]   = fighter[selectedFighters].getInitiative();
            name[selectedFighters]         = fighter[selectedFighters].getCharacterName();
            className[selectedFighters]    = fighter[selectedFighters].getClassName();
            selectedFighters++;

//            listClone = (ArrayList<Character>) character.list;
  //          character2.list.add(new Peasant(10,"zefz",2,2,2));
            System.out.println("--------- Origin ");
            character.display_list();
            System.out.println("--------- Clone ");
            character2.display_list();

//            for (Character character : character2.list){
//                System.out.println(character.IndexType());
//            }

        }

        System.out.println(className[0] + " " + className[1]);
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
                System.out.println("\n**** Turn " + nbTurn + " begins ! ****");
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
            if (currentHealthPoints < 0) {
                currentHealthPoints = 0;
            }
            System.out.println(className[defender] + " " + name[defender] + " remaining hp is " + currentHealthPoints);

            if(currentHealthPoints <= 0) {
                System.out.println("-------------------------------------------------");
                System.out.print("The winner of the confrontation is " + className[attacker] + " " + name[attacker] + ", congratulation ! For now..\n");
                character.removeCharacter(verifiedID[defender]);
                break;
            }

            turn = !turn;
            checkTurn++;
        }
    }
}
