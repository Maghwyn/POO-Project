package rpg.game;

import java.util.ArrayList;
import java.util.List;

public class Character implements Stats, Fighting, Save, Management {
    String className, name;
    int indexID, healthPoints, attackDamages, initiative;
    public List<Character> list = new ArrayList<>();

    public Character() { }
    public Character(String name, int attackDamages, int healthPoints, int initiative) {
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
        attributeRandomIndex();
    }

    private void attributeRandomIndex() {
        int random = (int) Math.floor(Math.random() * 100);
        for(Character character : list) {
            while(character.indexID == random) {
                random = (int) Math.floor(Math.random() * 100);
                random = random + 100;
            }
        }

        this.indexID = random;
    }

    public void add_character(Character new_character) { list.add(new_character);  }
    public void removeCharacter(int characterID)       { list.remove(characterID); }

    public int doesCharacterExist(int characterID) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == characterID) {
                return list.indexOf(character);
            }
        }
        return -1;
    }

    public void display_list(){
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
        this.healthPoints -= damages;
    }

    public Character getFighter(int index) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == index) {
                return character;
            }
        }
        return null;
    }

    public String toString() {
        return """
                +------------------------+---------------------+
                |  IndexID = None        |                     |
                |  Archetype = None      |  Name = None        |
                |                        |                     |
                |  AttackDamages = None  |  Initiative = None  |
                |  HealthPoint = None    |                     |
                +------------------------+---------------------+
                """;
    }

    public String IndexType() {
        return "CharacterID : " + indexID + " => Archetype : None";
    }

    public String convertToJSON() {
        return "{" + indexID + ": " + className +
                "{name:" + name + ", attackDamages:" + attackDamages +
                ", healthPoints:" + healthPoints + ", initiative:" + initiative + "}}";
    }

    public int getEnhancedDamages()  { return 0;     }
    public int getShield()           { return 0;     }
    public float getAgility()        { return 0;     }
    public float getCriticalChance() { return 0;     }
    public boolean isCritical()      { return false; }
    public void disableCritical()    {               }
    public void enableCritical()     {               }
}

class Peasant extends Character {
    public Peasant(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
        this.className     = "Peasant";
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

    public Warrior(String name, int attackDamages, int shield, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
        this.className    = "Warrior";
        this.shield       = shield;
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

    public Mage(String name, int attackDamages, int magicDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
        this.className    = "Mage";
        this.magicDamages = magicDamages;
    }

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

    public Thief(String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
        this.className      = "Thief";
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

    public WarriorMage (String name, int attackDamages, int magicDamages, int shield, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
        this.className = "WarriorMage";
        this.magicDamages = magicDamages;
        this.shield = shield;
    }

    public int getShield() { return shield; }
    public int getEnhancedDamages() {
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