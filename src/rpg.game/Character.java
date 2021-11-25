package rpg.game;

import java.util.ArrayList;
import java.util.List;


interface inProgress {

}

public class Character {
    String className;
    String name;
    int indexID;
    int healthPoints;
    int attackDamages;
    int magicDamages;
    int shield;
    int initiative;
    float agility;
    float criticalChance;
    boolean isCritical = true;

    List<Character> list = new ArrayList<>();

    public Character() {

    }

    public Character(String name, int attackDamages, int healthPoints, int initiative) {
        this.className     = "Peasant";
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
        attributeRandomIndex();
    }

    public Character(String name, int attackDamages, int specialAbilities, int healthPoints, int initiative){
        if(attackDamages >= 5) {
            this.className    = "Warrior";
            this.shield       = specialAbilities;
        } else {
            this.className    = "Mage";
            this.magicDamages = specialAbilities;
        }
        this.name          = name;
        this.attackDamages = attackDamages;
        this.healthPoints  = healthPoints;
        this.initiative    = initiative;
        attributeRandomIndex();
    }

    public Character(String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        this.className      = "Thief";
        this.name           = name;
        this.attackDamages  = attackDamages;
        this.criticalChance = criticalChance;
        this.healthPoints   = healthPoints;
        this.agility        = agility;
        this.initiative     = initiative;
        attributeRandomIndex();
    }

    private void attributeRandomIndex() {
        int random = (int) Math.floor(Math.random() * 100);
        for(Character character : list) {
            while(character.indexID == random) {
                random = (int) Math.floor(Math.random() * 100) + 100;
            }
        }

        this.indexID = random;
    }

    public void add_character(Character new_character){
        list.add(new_character);
    }

    public String getClassName() {
        return className;
    }
    public String getCharacterName() { return name; }
    public int getHealthPoints() { return healthPoints; }
    public int getAttackDamages() {  return attackDamages; }
    public int getInitiative() { return initiative; }

    public int getEnhancedDamages() {
        int enhancedDamages = attackDamages + magicDamages;
        magicDamages = magicDamages/2;
        if(magicDamages == 0) magicDamages = 1;
        return enhancedDamages;
    }

    public int getShield() {
        return shield;
    }
    public float getAgility() {
        return agility;
    }
    public float getCriticalChance() {
        return criticalChance;
    }
    public boolean isCritical() {
        return isCritical;
    }
    public void disableCritical() {
        isCritical = false;
    }
    public void enableCritical() {
        isCritical = true;
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

    public void takeDamages(int damages){
        this.healthPoints -= damages;
    }

    int doesCharacterExist(int characterID) {
        for(Character character : list) {
            int IndexID = character.indexID;
            if(IndexID == characterID) {
                return list.indexOf(character);
            }
        }
        return -1;
    }

    void removeCharacter(int characterID) {
        list.remove(characterID);
    }

    void display_list(){
        System.out.println("\nDisplaying the characters list..");
        for (Character character : list){
            System.out.println(character.IndexType());
        }
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
}

class Peasant extends Character {

    public Peasant(String name, int attackDamages, int healthPoints, int initiative) {
        super(name, attackDamages, healthPoints, initiative);
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
    public Warrior(String name, int attackDamages, int shield, int healthPoints, int initiative) {
        super(name, attackDamages, shield, healthPoints, initiative);
    }

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
    public Mage(String name, int attackDamages, int magicDamages, int healthPoints, int initiative) {
        super(name, attackDamages, magicDamages, healthPoints, initiative);
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
                "|  MagicDamages  = " + magicDamages + "         4 \n" +
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
    public Thief(String name, int attackDamages, float criticalChance, int healthPoints, float agility, int initiative) {
        super(name, attackDamages, criticalChance, healthPoints, agility, initiative);
    }

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
                ",critchance:" + criticalChance + ",agility:" + agility;
    }
}