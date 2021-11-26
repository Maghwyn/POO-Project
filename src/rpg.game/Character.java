package rpg.game;

import java.util.ArrayList;
import java.util.List;

public class Character implements Stats, Fighting, Save, Management {
    /**
     */
    String className, name;
    int indexID, healthPoints, attackDamages, initiative;
    public List<Character> list = new ArrayList<>();

    public Character() { }
    public Character(String name, int attackDamages, int healthPoints, int initiative) {
        /** Initialise the main variable for a character.
         * @name : The name of the character, user input.
         * @attackDamages : The attack damages of the character, user input.
         * @healthPoints : The health points of the character, user input.
         * @initiative : The initiative of the character, user input.
         */
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
    }

    public void add_character(Character new_character) { list.add(new_character);  }
    public void removeCharacter(int characterID)       { list.remove(characterID); }

    public int doesCharacterExist(int characterID) {
        /** This method will check if a character exist in the list or not.
         * @characterID : The character waiting to be validated, user input.
         * @return : Return either -1 so the program will ask the same question again in the menu,
         * or return the Object index in the list.
         */
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == characterID) {
                return list.indexOf(character);
            }
        }
        return -1;
    }

    public void display_list(){
        // This method will simply run throughout all elements inside the list, and print them.
        System.out.println("\nDisplaying the characters list..");
        for (Character character : list){
            System.out.println(character.IndexType());
        }
    }

    public String getClassName()     { return className;     }
    public String getCharacterName() { return name;          }
    public int getHealthPoints()     { return healthPoints;  }
    public int getAttackDamages()    { return attackDamages; }
    public int getInitiative()       { return initiative;    }


    public void takeDamages(int damages){
        // Used in the method to make two characters fight each other.
        this.healthPoints -= damages;
    }

    public Character getFighter(int index) {
        /** This method will check if a character exist in the list or not.
         * @characterID : The character waiting to be validated, user input.
         * @return : Either -1 or the Object index in the list.
         */
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == index) {
                return character;
            }
        }
        return null;
    }

    // This will display the character in a box.
    public String toString()      { return ""; }
    // This will be used for the displayList method.
    public String IndexType()     { return ""; }
    // This will be used for the save.
    public String convertToJSON() { return ""; }

    // These methods will be used specifically in a specific child class.
    public int getMagicDamages()     { return 0;     }
    public int getEnhancedDamages()  { return 0;     }
    public int getShield()           { return 0;     }
    public float getAgility()        { return 0;     }
    public float getCriticalChance() { return 0;     }
    public boolean isCritical()      { return false; }
    public void disableCritical()    {               }
    public void enableCritical()     {               }
}

class Peasant extends Character {
    public Peasant(int indexID, String name, int attackDamages, int healthPoints, int initiative) {
        /** Initialise the className and IndexID for a character Peasant.
         * @indexID : IndexID of the character generated from the size of the list.
         */
        super(name, attackDamages, healthPoints, initiative);
        this.className = "Peasant";
        this.indexID   = indexID;
    }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : Peasant";
    }

    public String toString() {
        return  "+-----------------------+-----------------------+\n" +
                "|  Archetype = " + className + "  "                  +
                "|  IndexID = " + indexID +               "       \n" +
                "|  Name = " + name + "                           \n" +
                "|                                                \n" +
                "|  AttackDamages = " + attackDamages + "         \n" +
                "|  HealthPoint   = " + healthPoints + "          \n" +
                "|  Initiative    = " + initiative + "            \n" +
                "+-----------------------------------------------+\n";
    }

    public String convertToJSON() {
        return indexID + ":" + className +
                "=>name:" + name + ",attackDamages:" + attackDamages +
                ",healthPoints:" + healthPoints + ",initiative:" + initiative;
    }
}

class Warrior extends Character {
    int shield;

    public Warrior(int indexID, String name, int attackDamages, int shield, int healthPoints, int initiative) {
        /** Initialise the className and IndexID for a character Peasant.
         * @indexID : IndexID of the character generated from the size of the list.
         * @shield  : Special value attributed to the warrior, it will be used to reduce the damage.
         */
        super(name, attackDamages, healthPoints, initiative);
        this.className = "Warrior";
        this.indexID   = indexID;
        this.shield    = shield;
    }

    public int getShield() { return shield; }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : Warrior";
    }

    public String toString() {
        return  "+-----------------------+-----------------------+\n" +
                "|  Archetype = " + className + "  "                  +
                "|  IndexID = " + indexID +               "       \n" +
                "|  Name = " + name + "                           \n" +
                "|                                                \n" +
                "|  AttackDamages = " + attackDamages + "         \n" +
                "|  HealthPoint   = " + healthPoints + "          \n" +
                "|  Initiative    = " + initiative + "            \n" +
                "|  Shield        = " + shield + "                \n" +
                "+-----------------------------------------------+\n";
    }

    public String convertToJSON() {
        return indexID + ":" + className +
                "=>name:" + name + ",attackDamages:" + attackDamages +
                ",healthPoints:" + healthPoints + ",initiative:" + initiative + ",shield:" + shield;
    }
}

class Mage extends Character {
    int magicDamages;

    public Mage(int indexID, String name, int attackDamages, int magicDamages, int healthPoints, int initiative) {
        /** Initialise the className and IndexID for a character Peasant.
         * @indexID : IndexID of the character generated from the size of the list.
         * @magicDamages  : Special value attributed to the mage, it will be used to buff the overall damage.
         */
        super(name, attackDamages, healthPoints, initiative);
        this.className    = "Mage";
        this.indexID      = indexID;
        this.magicDamages = magicDamages;
    }

    public int getMagicDamages() { return magicDamages; }
    public int getEnhancedDamages() {
        int enhancedDamages = attackDamages + magicDamages;
        magicDamages = magicDamages/2;
        if(magicDamages == 0) magicDamages = 1;
        return enhancedDamages;
    }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : Mage";
    }

    public String toString() {
        return  "+-----------------------+-----------------------+\n" +
                "|  Archetype = " + className + "  "                  +
                "|  IndexID = " + indexID +               "       \n" +
                "|  Name = " + name + "                           \n" +
                "|                                                \n" +
                "|  AttackDamages = " + attackDamages + "         \n" +
                "|  MagicDamages  = " + magicDamages + "          \n" +
                "|  HealthPoint   = " + healthPoints + "          \n" +
                "|  Initiative    = " + initiative + "            \n" +
                "+-----------------------------------------------+\n";
    }

    public String convertToJSON() {
        return indexID + ":" + className +
                "=>name:" + name + ",attackDamages:" + attackDamages + ",magicDamages:" + magicDamages +
                ",healthPoints:" + healthPoints + ",initiative:" + initiative;
    }
}

class Thief extends Character {
    float agility;
    float criticalChance;
    boolean isCritical = true;

    public Thief(int indexID, String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        /** Initialise the className and IndexID for a character Peasant.
         * @indexID : IndexID of the character generated from the size of the list.
         * @criticalChance  : Special value attributed to the thief, it will be used to multiply the damage.
         * @agility : Special value attributed to the thief, it will be used to dodge an attack.
         */
        super(name, attackDamages, healthPoints, initiative);
        this.className      = "Thief";
        this.indexID        = indexID;
        this.criticalChance = criticalChance;
        this.agility        = agility;
    }

    public float getAgility()        { return agility;        }
    public float getCriticalChance() { return criticalChance; }
    public boolean isCritical()      { return isCritical;     }
    public void disableCritical()    { isCritical = false;    }
    public void enableCritical()     { isCritical = true;     }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : Thief";
    }

    public String toString() {
        return  "+-----------------------+-----------------------+\n" +
                "|  Archetype = " + className + "  "                  +
                "|  IndexID = " + indexID +               "       \n" +
                "|  Name = " + name + "                           \n" +
                "|                                                \n" +
                "|  AttackDamages = " + attackDamages + "         \n" +
                "|  HealthPoint   = " + healthPoints + "          \n" +
                "|  Initiative    = " + initiative + "            \n" +
                "|  CritChance    = " + criticalChance + "        \n" +
                "|  Agility       = " + agility + "               \n" +
                "+-----------------------------------------------+\n";
    }

    public String convertToJSON() {
        return indexID + ":" + className +
                "=>name:" + name + ",attackDamages:" + attackDamages +
                ",healthPoints:" + healthPoints + ",initiative:" + initiative +
                ",critChance:" + criticalChance + ",agility:" + agility;
    }
}

class WarriorMage extends Character {
    int magicDamages;
    int shield;

    public WarriorMage (int indexID, String name, int attackDamages, int magicDamages, int shield, int healthPoints, int initiative) {
        /** Initialise the className and IndexID for a character Peasant.
         * @indexID : IndexID of the character generated from the size of the list.
         * @magicDamages  : Special value attributed to the WarriorMage, it will be used to buff the overall damage.
         * @shield : Special value attributed to the WarriorMage, it will be used to reduce or block an attack.
         */
        super(name, attackDamages, healthPoints, initiative);
        this.className    = "WarriorMage";
        this.indexID      = indexID;
        this.magicDamages = magicDamages;
        this.shield       = shield;
    }

    public int getShield() { return shield; }
    public int getMagicDamages() { return magicDamages; }
    public int getEnhancedDamages() {
        // This method will calculate the damage enhanced with magic.
        int enhancedDamages = attackDamages + magicDamages;
        magicDamages = magicDamages/2;
        if(magicDamages == 0) magicDamages = 1;
        return enhancedDamages;
    }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : Warrior-Mage";
    }

    public String toString() {
        return  "+-----------------------+-----------------------+\n" +
                "|  Archetype = " + className + "  "                  +
                "|  IndexID = " + indexID +               "       \n" +
                "|  Name = " + name + "                           \n" +
                "|                                                \n" +
                "|  AttackDamages = " + attackDamages + "         \n" +
                "|  MagicDamages  = " + magicDamages + "          \n" +
                "|  HealthPoint   = " + healthPoints + "          \n" +
                "|  Initiative    = " + initiative + "            \n" +
                "|  Shield        = " + shield + "                \n" +
                "+-----------------------------------------------+\n";
    }

    public String convertToJSON() {
        return indexID + ":" + className +
                "=>name:" + name + ",attackDamages:" + attackDamages + ",magicDamages:" + magicDamages +
                ",healthPoints:" + healthPoints + ",initiative:" + initiative + ",shield:" + shield;
    }
}